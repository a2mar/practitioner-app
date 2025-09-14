-- V2__update_locations_table.sql
ALTER TABLE locations
    DROP COLUMN address,
    ADD COLUMN street_and_no VARCHAR(255) NOT NULL,
    ADD COLUMN zip_code VARCHAR(50) NOT NULL,
    ADD COLUMN city VARCHAR(255) NOT NULL,
    ADD COLUMN country VARCHAR(255) NOT NULL;
