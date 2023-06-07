package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.ReviewService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService service;

    @PostMapping //POST /reviews
    @ResponseStatus(HttpStatus.CREATED)
    public Review addReview(@Valid @RequestBody Review review,
                            HttpServletRequest request) {
        log.debug("On URL [{}] used method [{}]", request.getRequestURL(), request.getMethod());
        return service.addReview(review);
    }

    @PostMapping //PUT /reviews
    @ResponseStatus(HttpStatus.OK)
    public Review updateReview(@Valid @RequestBody Review review,
                               HttpServletRequest request) {
        log.debug("On URL [{}] used method [{}]", request.getRequestURL(), request.getMethod());
        return service.updateReview(review);
    }

    @GetMapping("/{reviewId}") //GET /reviews/{id}
    @ResponseStatus(HttpStatus.OK)
    public Review getReview(@PathVariable("reviewId") long reviewId,
                            HttpServletRequest request) {
        log.debug("On URL [{}] used method [{}]", request.getRequestURL(), request.getMethod());
        return service.findReviewById(reviewId);
    }

    @DeleteMapping("/{reviewId}") //DELETE /reviews/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeReview(@PathVariable("reviewId") long reviewId,
                             HttpServletRequest request) {
        log.debug("On URL [{}] used method [{}]", request.getRequestURL(), request.getMethod());
        service.removeReview(reviewId);
    }

    @PutMapping("/{reviewId}/like/{userId}") //PUT /reviews/{id}/like/{userId}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addLikeReview(@PathVariable("reviewId") long reviewId,
                              @PathVariable("userId") long userId,
                              HttpServletRequest request) {
        log.debug("On URL [{}] used method [{}]", request.getRequestURL(), request.getMethod());
        service.addLikeReview(reviewId, userId);
    }

    @DeleteMapping("/{reviewId}/like/{userId}") //DELETE /reviews/{id}/like/{userId}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLikeReview(@PathVariable("reviewId") long reviewId,
                                 @PathVariable("userId") long userId,
                                 HttpServletRequest request) {
        log.debug("On URL [{}] used method [{}]", request.getRequestURL(), request.getMethod());
        service.removeLikeReview(reviewId, userId);
    }

    @PutMapping("/{reviewId}/dislike/{userId}") //PUT /reviews/{id}/dislike/{userId}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addDislikeReview(@PathVariable("reviewId") long reviewId,
                                 @PathVariable("userId") long userId,
                                 HttpServletRequest request) {
        log.debug("On URL [{}] used method [{}]", request.getRequestURL(), request.getMethod());
        service.addDislikeReview(reviewId, userId);
    }

    @DeleteMapping("/{reviewId}/dislike/{userId}") //DELETE /reviews/{id}/dislike/{userId}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeDislikeReview(@PathVariable("reviewId") long reviewId,
                                    @PathVariable("userId") long userId,
                                    HttpServletRequest request) {
        log.debug("On URL [{}] used method [{}]", request.getRequestURL(), request.getMethod());
        service.removeDislikeReview(reviewId, userId);
    }

    @GetMapping //GET /reviews?count={count}
    public List<Review> getReviews(@RequestParam(defaultValue = "10", required = false) int count,
                                   HttpServletRequest request) {
        log.debug("On URL [{}] used method [{}]", request.getRequestURL(), request.getMethod());
        return service.getReviews(count);
    }

    @GetMapping("/{filmId}") //GET /reviews?filmId={filmId}&count={count}
    @ResponseStatus(HttpStatus.OK)
    public List<Review> getReviewsByFilm(@PathVariable("filmId") long filmId,
                                         @RequestParam(defaultValue = "10", required = false) int count,
                                         HttpServletRequest request) {
        log.debug("On URL [{}] used method [{}]", request.getRequestURL(), request.getMethod());
        return service.getReviewsByFilm(filmId, count);
    }
}
