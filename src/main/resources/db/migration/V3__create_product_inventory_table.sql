CREATE TABLE IF NOT EXISTS product_inventory (
    product_inventory_id    VARCHAR(36) NOT NULL PRIMARY KEY,
    quantity                INT,
    created_by              VARCHAR(50) NULL,
    created_time            TIMESTAMP DEFAULT NOW(),
    updated_by              VARCHAR(50) NULL,
    updated_time            TIMESTAMP DEFAULT NOW(),
    deleted_time            TIMESTAMP NULL
)