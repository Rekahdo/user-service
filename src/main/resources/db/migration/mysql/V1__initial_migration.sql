-- Create users table
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    verified BOOLEAN DEFAULT FALSE,
    profile_id BIGINT,
    created_at DATE NOT NULL,
    updated_at DATE,
    INDEX idx_username (username),
    INDEX idx_email (email)
);

-- Create authorities table
CREATE TABLE authorities (
    id TINYINT PRIMARY KEY AUTO_INCREMENT,
    role INT,
    assigned_at DATE,
    user_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id)
);

-- Create phone_numbers table
CREATE TABLE phone_numbers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    country_code VARCHAR(10) NOT NULL,
    number VARCHAR(20) NOT NULL,
    verified BOOLEAN DEFAULT FALSE,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    UNIQUE KEY uk_phone (number, user_id)
);

-- Create otps table
CREATE TABLE otps (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    otp INT,
    purpose VARCHAR(10),
    sent_to VARCHAR(50),
    verified BOOLEAN DEFAULT FALSE,
    expire_at DATETIME,
    user_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_expire_at (expire_at)
);
