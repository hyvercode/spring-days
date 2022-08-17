CREATE TABLE IF NOT EXISTS product (
    product_id   VARCHAR(36) NOT NULL PRIMARY KEY,
    sku  VARCHAR(36) NOT NULL UNIQUE,
    product_name VARCHAR(60) NOT NULL,
    price        DECIMAL(21,2) DEFAULT 0,
    is_active    BOOLEAN DEFAULT TRUE,
    created_by  VARCHAR(50) NULL,
    created_time TIMESTAMP DEFAULT NOW(),
    updated_by  VARCHAR(50) NULL,
    updated_time TIMESTAMP DEFAULT NOW(),
    deleted_time TIMESTAMP NULL
)