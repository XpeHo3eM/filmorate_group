CREATE TABLE IF NOT EXISTS genres (
	id    int     GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
	genre varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS mpas (
	id     int     GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
	rating varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
	id       bigint    GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
	name     varchar   NOT NULL,
	email    varchar   NOT NULL,
	login    varchar   NOT NULL,
	birthday timestamp NOT NULL
);

CREATE TABLE IF NOT EXISTS user_friends (
	user_id              bigint NOT NULL REFERENCES users(id) ON DELETE CASCADE,
	friend_id            bigint NOT NULL REFERENCES users(id) ON DELETE CASCADE,
	PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE IF NOT EXISTS films (
	id           bigint     GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
	name         varchar    NOT NULL,
	description  varchar    NOT NULL,
	release_date timestamp  NOT NULL,
	duration     int        NOT NULL,
	rating_id    int        REFERENCES mpas(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS film_genres (
	film_id  bigint NOT NULL REFERENCES films(id) ON DELETE CASCADE,
	genre_id int    NOT NULL REFERENCES genres(id) ON DELETE CASCADE,
	PRIMARY KEY (film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS film_users_likes (
	film_id bigint NOT NULL REFERENCES films(id) ON DELETE CASCADE,
	user_id bigint NOT NULL REFERENCES users(id) ON DELETE CASCADE,
	PRIMARY KEY (film_id, user_id)
);

CREATE TABLE IF NOT EXISTS film_reviews (
    review_id   bigint     GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    content     varchar    NOT NULL,
    is_positive boolean    NOT NULL,
    user_id     bigint     NOT NULL REFERENCES users(id) ON DELETE CASCADE,
	film_id     bigint     NOT NULL REFERENCES films(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS film_reviews_likes (
    review_id   bigint     NOT NULL REFERENCES film_reviews(review_id) ON DELETE CASCADE,
    user_id     bigint     NOT NULL REFERENCES users(id) ON DELETE CASCADE,
	is_positive boolean    NOT NULL,
	PRIMARY KEY (review_id, user_id)
);

CREATE TABLE IF NOT EXISTS directors (
    id   bigint  GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS film_directors (
    film_id     bigint NOT NULL REFERENCES films(id)     ON DELETE CASCADE,
    director_id bigint NOT NULL REFERENCES directors(id) ON DELETE CASCADE,
    PRIMARY KEY (film_id, director_id)
);


