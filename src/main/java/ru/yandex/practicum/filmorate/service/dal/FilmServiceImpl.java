package ru.yandex.practicum.filmorate.service.dal;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.entity.EntityAlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.entity.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exception.film.FilmAlreadyLikedException;
import ru.yandex.practicum.filmorate.exception.film.FilmNotLikedException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final GenreStorage genreStorage;

    public FilmServiceImpl(FilmStorage filmStorage, UserStorage userStorage, GenreStorage genreStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.genreStorage = genreStorage;
    }

    @Override
    public Film addLike(Long filmId, Long userId) {
        User user = getUserOrThrowException(userId);
        Film film = getFilmOrThrowException(filmId);
        Set<Long> likes = film.getUsersLikes();

        if (likes.contains(userId)) {
            throw new FilmAlreadyLikedException(String.format("%s уже лайкал \"%s\"", user.getName(), film.getName()));
        }

        likes.add(userId);
        filmStorage.updateFilm(film);

        return film;
    }

    @Override
    public Film removeLike(Long filmId, Long userId) {
        User user = getUserOrThrowException(userId);
        Film film = getFilmOrThrowException(filmId);
        Set<Long> likes = film.getUsersLikes();

        if (!likes.contains(userId)) {
            throw new FilmNotLikedException(String.format("%s еще не лайкал \"%s\"", user.getName(), film.getName()));
        }

        likes.remove(userId);
        filmStorage.updateFilm(film);

        return film;
    }

    @Override
    public List<Film> getPopulated(Integer filmsCount, Integer genreId, Integer year) {
        final int DEFAULT_FILMS_COUNT = 10;
        int maxFilms = filmsCount != null ? filmsCount : DEFAULT_FILMS_COUNT;
        List<Film> films = filmStorage.getAllFilms();

        if (genreId == null && year == null) {
            return films.stream()
                    .sorted((f1, f2) -> f2.getUsersLikes().size() - f1.getUsersLikes().size())
                    .limit(maxFilms)
                    .collect(Collectors.toList());

        } else if (genreId != null && year == null) {
            Genre genre = genreStorage.getGenre(genreId);
            return films.stream()
                    .filter(f -> f.getGenres().contains(genre))
                    .sorted((f1, f2) -> f2.getUsersLikes().size() - f1.getUsersLikes().size())
                    .limit(maxFilms)
                    .collect(Collectors.toList());

        } else if (genreId == null && year != null) {
            return films.stream()
                    .filter(f -> year.equals(f.getReleaseDate().getYear()))
                    .sorted((f1, f2) -> f2.getUsersLikes().size() - f1.getUsersLikes().size())
                    .limit(maxFilms)
                    .collect(Collectors.toList());

        } else {
            Genre genre = genreStorage.getGenre(genreId);
            return films.stream()
                    .filter(f -> f.getGenres().contains(genre))
                    .filter(f -> year.equals(f.getReleaseDate().getYear()))
                    .sorted((f1, f2) -> f2.getUsersLikes().size() - f1.getUsersLikes().size())
                    .limit(maxFilms)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Film getFilmById(Long id) {
        return getFilmOrThrowException(id);
    }

    @Override
    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    @Override
    public Film addFilm(Film film) {
        FilmValidator.validate(film);

        Film filmOnDb = filmStorage.addFilm(film);

        if (filmOnDb == null) {
            throw new EntityAlreadyExistsException(String.format("Фильм с ID = %s уже добавлен", film.getId()));
        }

        return filmOnDb;
    }

    @Override
    public Film updateFilm(Film film) {
        FilmValidator.validate(film);

        Film filmOnDb = filmStorage.updateFilm(film);

        if (filmOnDb == null) {
            throw new EntityNotFoundException(String.format("Фильм с ID = %s не найден", film.getId()));
        }

        return filmOnDb;
    }

    @Override
    public List<Film> getDirectorFilms(Long directorId, String sortBy) {
        List<Film> filmsOnDb = filmStorage.getAllFilmsByDirector(directorId);

        if (filmsOnDb.isEmpty()) {
            throw new EntityNotFoundException(String.format("Режиссер с ID = %s не найден", directorId));
        }

        List<Film> result = filmsOnDb;

        if ("year".equals(sortBy)) {
            result = filmsOnDb.stream()
                    .sorted(Comparator.comparingInt(f -> f.getReleaseDate().getYear()))
                    .collect(Collectors.toList());
        } else if ("likes".equals(sortBy)) {
            result = filmsOnDb.stream()
                    .sorted(Comparator.comparingInt(f -> f.getUsersLikes().size()))
                    .collect(Collectors.toList());
        }

        return result;
    }

    private User getUserOrThrowException(Long id) {
        User userOnDb = userStorage.getUserById(id);

        if (userOnDb == null) {
            throw new EntityNotFoundException(String.format("Пользователь с ID = %s не найден", id));
        }
        return userOnDb;
    }

    private Film getFilmOrThrowException(Long id) {
        Film filmOnDb = filmStorage.getFilmById(id);

        if (filmOnDb == null) {
            throw new EntityNotFoundException(String.format("Фильм с ID = %s не найден", id));
        }

        return filmOnDb;
    }
}
