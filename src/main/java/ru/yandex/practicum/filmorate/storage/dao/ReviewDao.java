package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReviewDao implements ReviewStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Review> addReview(Review review) {
        return Optional.empty();
    }

    @Override
    public Optional<Review> updateReview(Review review) {
        return Optional.empty();
    }

    @Override
    public Optional<Review> findReviewById(long reviewId) {
        return Optional.empty();
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
