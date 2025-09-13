CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    salutation VARCHAR(50),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL
);

CREATE TABLE practitioners (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    bio TEXT,
    specialties TEXT
);

CREATE TABLE clients (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    contact_info TEXT,
    insurance_number VARCHAR(100),
    insurance_company VARCHAR(255)
);

CREATE TABLE locations (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    address TEXT
);

CREATE TABLE availability (
    id BIGSERIAL PRIMARY KEY,
    practitioner_id BIGINT NOT NULL REFERENCES practitioners(id),
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    location_id BIGINT REFERENCES locations(id),
    is_public BOOLEAN DEFAULT TRUE
);

CREATE TABLE appointments (
    id BIGSERIAL PRIMARY KEY,
    practitioner_id BIGINT NOT NULL REFERENCES practitioners(id),
    client_id BIGINT NOT NULL REFERENCES clients(id),
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    notes TEXT
);