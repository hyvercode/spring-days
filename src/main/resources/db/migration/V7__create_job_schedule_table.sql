CREATE TABLE job_schedule (
    job_schedule_id BIGINT NOT NULL PRIMARY KEY,
    job_name VARCHAR(255) NOT NULL,
    scheduled_time TIMESTAMP NOT NULL,
    executed BOOLEAN DEFAULT FALSE,
    retry_count INTEGER DEFAULT 0,
    is_active BOOLEAN NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP NOT NULL,
    updated_by VARCHAR(50) NULL,
    updated_time TIMESTAMP NULL
);