CREATE TABLE users (
    user_id         VARCHAR(36) NOT NULL PRIMARY KEY,
    email           VARCHAR(30) NOT NULL,
    password        VARCHAR(255) NOT NULL,
    name            VARCHAR(60) NOT NULL,
    phone_number    VARCHAR(15) NOT NULL,
    is_active       BOOLEAN DEFAULT TRUE,
    created_by      VARCHAR(50) NULL,
    created_time    TIMESTAMP DEFAULT NOW(),
    updated_by      VARCHAR(50) NULL,
    updated_time    TIMESTAMP DEFAULT NOW(),
    deleted_time    TIMESTAMP NULL
);