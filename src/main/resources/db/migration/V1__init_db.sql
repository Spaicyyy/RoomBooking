CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE rooms (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(100) NOT NULL UNIQUE,
                       capacity INT NOT NULL,
                       description TEXT,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE bookings (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      user_id BIGINT NOT NULL,
                      room_id BIGINT NOT NULL,
                      start_time DATETIME NOT NULL,
                      end_time DATETIME NOT NULL,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                      CONSTRAINT fk_booking_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
                      CONSTRAINT fk_booking_room FOREIGN KEY (room_id) REFERENCES rooms (id) ON DELETE CASCADE
);

CREATE TABLE api_keys (
                      id BINGINT AUTO_INCREMENT PRIMARY KEY,
                      key_hash VARCHAR(100) NOT NULL UNIQUE ,
                      prefix VARCHAR(100) NOT NULL ,
                      owner_name VARCHAR(100) NOT NULL,
                      is_active BOOLEAN NOT NULL DEFAULT TRUE ,
                      is_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);