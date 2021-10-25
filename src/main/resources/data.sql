DROP TABLE IF EXISTS driver;
DROP TABLE IF EXISTS semi_trailer;
DROP TABLE IF EXISTS truck;

CREATE TABLE driver
(
    id             BIGSERIAL PRIMARY KEY NOT NULL,
    name           VARCHAR(50)           NOT NULL,
    surname        VARCHAR(100)          NOT NULL,
    driver_license VARCHAR(35) UNIQUE    NOT NULL,
    created        timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated        timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE semi_trailer
(
    id                  BIGSERIAL PRIMARY KEY NOT NULL,
    manufacturer        VARCHAR(30)           NOT NULL,
    production_date     TIMESTAMP             NOT NULL,
    registration_number VARCHAR(20) UNIQUE,
    created             timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated             timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE truck
(
    id                  BIGSERIAL PRIMARY KEY NOT NULL,
    driver              BIGINT REFERENCES driver (id) ON DELETE CASCADE,
    semi_trailer        BIGINT REFERENCES semi_trailer (id) ON DELETE CASCADE,
    manufacturer        VARCHAR(30)           NOT NULL,
    production_date     TIMESTAMP             NOT NULL,
    registration_number VARCHAR(20) UNIQUE,
    created             timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated             timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP
)
