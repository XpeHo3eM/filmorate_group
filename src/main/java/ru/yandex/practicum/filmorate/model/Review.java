package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class Review {
    private long reviewId;

    @NotBlank(message = "content can't be blank")
    private String content;

    @NonNull
    private Boolean isPositive;

    @NonNull
    private Long userId;

    @NonNull
    private Long filmId;

    private long useful;
}
