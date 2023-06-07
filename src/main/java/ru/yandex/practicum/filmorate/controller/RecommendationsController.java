package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.entity.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.RecommendationsService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
public class RecommendationsController {

    private final RecommendationsService recommendationsService;
    private final UserService userService;

    public RecommendationsController(RecommendationsService recommendationsService, UserService userService) {
        this.recommendationsService = recommendationsService;
        this.userService = userService;
    }

    @GetMapping("/users/{id}/recommendations")
    public List<Film> getRecommended(@PathVariable Long id) {
        User user = userService.getUserById(id);

        if (user == null) {
            throw new EntityNotFoundException("Пользователь не найден.");
        }

        return recommendationsService.getRecommended(id);
    }
}
