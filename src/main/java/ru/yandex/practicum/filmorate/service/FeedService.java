package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Feed;

import java.util.List;

public interface FeedService {
    void createFeed(Long userId, Long entityId, String eventType, String operation);

    List<Feed> getFeedId(Long userId);
}
