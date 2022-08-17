CREATE TABLE IF NOT EXISTS product (
    product_id              VARCHAR(36) NOT NULL PRIMARY KEY,
    sku                     VARCHAR(36) NOT NULL UNIQUE,
    product_name            VARCHAR(60) NOT NULL,
    price                   DECIMAL(21,2) DEFAULT 0,
    product_category_id     VARCHAR(36) NOT NULL,
    product_inventory_id    VARCHAR(36) NOT NULL,
    is_active               BOOLEAN DEFAULT TRUE,
    created_by              VARCHAR(50) NULL,
    created_time            TIMESTAMP DEFAULT NOW(),
    updated_by              VARCHAR(50) NULL,
    updated_time            TIMESTAMP DEFAULT NOW(),
    deleted_time            TIMESTAMP NULL,
    CONSTRAINT product_category_fk1 FOREIGN KEY (product_category_id) REFERENCES product_category (product_category_id),
    CONSTRAINT product_inventory_fk2 FOREIGN KEY (product_inventory_id) REFERENCES product_inventory (product_inventory_id)
)