package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Builder
@Data
public class Review {
    private final long reviewId;

    @NotNull
    @NotBlank(message = "content can't be blank")
    private final String content;

    @NotNull
    private final boolean isPositive;

    @NotNull
    @Positive
    private final long userId;

    @NotNull
    @Positive
    private final long filmId;

    private final long useful;
}
