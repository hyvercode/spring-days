CREATE TABLE IF NOT EXISTS product (
    product_id   VARCHAR(36) NOT NULL PRIMARY KEY,
    product_name VARCHAR(60) NOT NULL,
    deleted_time TIMESTAMP NULL
)