-- DDL
CREATE TABLE email_template(
    email_template_id VARCHAR(36) NOT NULL,
    email_code VARCHAR(50) UNIQUE NOT NULL,
    content TEXT NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP NOT NULL,
    updated_by VARCHAR(50) NULL,
    updated_time TIMESTAMP NULL,
    CONSTRAINT "email_template_pk" PRIMARY KEY (email_template_id)
);