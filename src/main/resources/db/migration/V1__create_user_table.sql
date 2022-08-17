CREATE TABLE users (
    user_id         VARCHAR(36) NOT NULL PRIMARY KEY,
    name            VARCHAR(60) NOT NULL,
    email           VARCHAR(30) NOT NULL,
    deleted_time    TIMESTAMP NULL
);