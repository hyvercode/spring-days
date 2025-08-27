CREATE TABLE users (
    user_id         VARCHAR(36) NOT NULL PRIMARY KEY,
    username        VARCHAR(30) NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL,
    name            VARCHAR(60) NOT NULL,
    email           VARCHAR(30) NOT NULL UNIQUE,
    phone_number    VARCHAR(15) NOT NULL,
    device_id       VARCHAR(60) NOT NULL,
    is_active       BOOLEAN DEFAULT TRUE,
    created_by      VARCHAR(50) NULL,
    created_time    TIMESTAMP DEFAULT NOW(),
    updated_by      VARCHAR(50) NULL,
    updated_time    TIMESTAMP DEFAULT NOW(),
    deleted_time    TIMESTAMP NULL
);

CREATE TABLE roles (
  role_id VARCHAR(36) NOT NULL PRIMARY KEY,
  name varchar(45) NOT NULL
);

CREATE TABLE users_roles (
  user_id VARCHAR(36) NOT NULL,
  role_id VARCHAR(36) NOT NULL,
  CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES roles (role_id),
  CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users (user_id)
);