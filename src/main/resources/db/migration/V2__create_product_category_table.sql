CREATE TABLE IF NOT EXISTS product_category (
    product_category_id     VARCHAR(36) NOT NULL PRIMARY KEY,
    product_category_name   VARCHAR(60) NOT NULL,
    description             VARCHAR(100) NULL,
    is_active               BOOLEAN DEFAULT TRUE,
    created_by              VARCHAR(50) NULL,
    created_time            TIMESTAMP DEFAULT NOW(),
    updated_by              VARCHAR(50) NULL,
    updated_time            TIMESTAMP DEFAULT NOW(),
    deleted_time            TIMESTAMP NULL
)