CREATE TABLE job_listings
(
    id          UUID NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_job_listings PRIMARY KEY (id)
);

CREATE TABLE users
(
    id                 UUID NOT NULL,
    username           VARCHAR(255),
    email              VARCHAR(255),
    first_name         VARCHAR(255),
    last_name          VARCHAR(255),
    creation_timestamp TIMESTAMP WITHOUT TIME ZONE,
    update_timestamp   TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_users PRIMARY KEY (id)
);