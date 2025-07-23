-- Create database and table for testing
CREATE DATABASE IF NOT EXISTS POS;
USE POS;

CREATE TABLE IF NOT EXISTS category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(100),
    description TEXT,
    image VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert a test category
INSERT INTO category (name, code, description) VALUES 
('Test Category', 'TEST001', 'This is a test category');

-- Show the table structure
DESCRIBE category;

-- Show existing data
SELECT * FROM category;