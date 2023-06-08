package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;
import ru.yandex.practicum.filmorate.util.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReviewDao implements ReviewStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public Optional<Review> addReview(Review review) {
        long reviewId = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("film_reviews")
                .usingGeneratedKeyColumns("reviewId")
                .executeAndReturnKey(Mapper.reviewToMap(review)).longValue();

        return findReviewById(reviewId);
    }

    @Override
    public Optional<Review> updateReview(Review review) {
        return Optional.empty();
    }

    @Override
    public Optional<Review> findReviewById(long reviewId) {
        String sqlQuery = "SELECT *\n" +
                "FROM film_reviews\n" +
                "WHERE reviewId = ?;";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sqlQuery, Mapper::mapRowToReview, reviewId));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean removeReview(long reviewId) {
        return false;
    }

    @Override
    public boolean addLikeReview(long reviewId, long userId) {
        return false;
    }

    @Override
    public boolean removeLikeReview(long reviewId, long userId) {
        return false;
    }

    @Override
    public boolean addDislikeReview(long reviewId, long userId) {
        return false;
    }

    @Override
    public boolean removeDislikeReview(long reviewId, long userId) {
        return false;
    }

    @Override
    public List<Review> getReviews(int count) {
        return Collections.emptyList();
    }

    @Override
    public List<Review> getReviewsByFilm(long filmId, int count) {
        return Collections.emptyList();
    }
}
