DROP TABLE IF EXISTS driver;
DROP TABLE IF EXISTS truck;
DROP TABLE IF EXISTS semi_trailer;

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
    semi_trailer_id     BIGINT REFERENCES semi_trailer (id) ON DELETE CASCADE ,
    manufacturer        VARCHAR(30)           NOT NULL,
    production_date     TIMESTAMP             NOT NULL,
    registration_number VARCHAR(20) UNIQUE,
    created             timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated             timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE driver
(
    id             BIGSERIAL PRIMARY KEY NOT NULL,
    truck_id       BIGINT REFERENCES truck (id) ON DELETE CASCADE ,
    name           VARCHAR(50)           NOT NULL,
    surname        VARCHAR(100)          NOT NULL,
    driver_license VARCHAR(35) UNIQUE    NOT NULL,
    created        timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated        timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO semi_trailer(manufacturer, production_date, registration_number)
VALUES ('Mercedes', '1999-03-01', 'VLN 111'),
       ('Volvo', '2000-03-01', 'KNS 001'),
       ('Renault', '2002-03-01', 'KLP 009'),
       ('Trailer', '2002-03-01', 'KLP 010');

INSERT INTO truck(semi_trailer_id, manufacturer, production_date, registration_number)
VALUES (1, 'Volvo', '2002-01-01', 'CCC 888'),
       (2, 'Mercedes', '2006-10-01', 'BBB 888'),
       (3, 'Mercedes', '2006-10-01', 'BBB 999'),
       (4, 'Truck', '2000-03-01', 'TruckWithDriver5');

INSERT INTO driver(truck_id, name, surname, driver_license)
VALUES (1, 'Driver', 'One', 'LCN0001'),
       (2, 'Driver', 'Two', 'LCN0002'),
       (3, 'Driver', 'Three', 'LCN0003'),
       (1, 'Driver', 'Four', 'LCN0004'),
       (4, 'Driver', 'OnTruck4', 'LCN0005');
