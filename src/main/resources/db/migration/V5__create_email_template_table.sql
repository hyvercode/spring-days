-- DDL
CREATE TABLE email_template(
    email_template_id VARCHAR(36) NOT NULL,
    event_code VARCHAR(50) UNIQUE NOT NULL,
    en_title   VARCHAR(200) NOT NULL,
    en_content TEXT NOT NULL,
    id_title VARCHAR(200) NOT NULL,
    id_content TEXT NOT NULL,
    image_content VARCHAR(255) NULL,
    is_active BOOLEAN NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP NOT NULL,
    updated_by VARCHAR(50) NULL,
    updated_time TIMESTAMP NULL,
    CONSTRAINT "email_template_pk" PRIMARY KEY (email_template_id)
);