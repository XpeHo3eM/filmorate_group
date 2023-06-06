package ru.yandex.practicum.filmorate.service.dal;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.RecommendationsService;
import ru.yandex.practicum.filmorate.service.UserService;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationsServiceImpl implements RecommendationsService {
    private final UserService userService;
    private final FilmService filmService;
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsServiceImpl(UserService userService, FilmService filmService, JdbcTemplate jdbcTemplate) {
        this.userService = userService;
        this.filmService = filmService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> getRecommended(Long userId) {
        String sql = "SELECT count(*) "
            + "FROM film_users_likes "
            + "WHERE user_id = ?;";
        Integer likesCount = null;
        try {
            likesCount = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        } catch (DataAccessException ignore) {
        }
        if (likesCount == null || likesCount == 0) {
            return new ArrayList<>();
        }
        sql = "SELECT user_id "
            + "FROM film_users_likes "
            + "WHERE film_id IN ( "
            + "    SELECT film_id "
            + "    FROM film_users_likes "
            + "    WHERE user_id = ? "
            + ") "
            + "AND user_id != ? "
            + "GROUP BY user_id "
            + "ORDER BY COUNT(*) DESC "
            + "LIMIT 1;";
        Long foundId = null;
        try {
            foundId = jdbcTemplate.queryForObject(sql, Long.class, userId, userId);
        } catch (DataAccessException ignore) {
        }
        if (foundId == null) {
            return new ArrayList<>();
        }
        sql = "SELECT film_id "
            + "FROM film_users_likes "
            + "WHERE user_id = ? "
            + "AND film_id not in ( "
            + "    SELECT film_id "
            + "    FROM film_users_likes "
            + "    WHERE user_id = ? "
            + "); ";
        List<Long> recommendedIds = null;
        try {
            recommendedIds = jdbcTemplate.queryForList(sql, Long.class, foundId, userId);
        } catch (DataAccessException ignore) {
        }
        if (recommendedIds != null && recommendedIds.size() > 0) {
            return recommendedIds.stream()
                .map(filmService::getFilmById)
                .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
