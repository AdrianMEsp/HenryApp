-- CREATE TABLE category AND inject values

CREATE TABLE IF NOT EXISTS category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

INSERT INTO category (name, description)
SELECT 'Beauty', 'Expenses related to personal care and beauty products'
WHERE NOT EXISTS (
    SELECT 1
    FROM category
    WHERE name = 'Beauty'
);

INSERT INTO category (name, description)
SELECT 'Cellphone', 'Expenses related to phone bills and devices'
WHERE NOT EXISTS (
    SELECT 1
    FROM category
    WHERE name = 'Cellphone'
);

INSERT INTO category (name, description)
SELECT 'Education', 'Expenses for courses, books, and learning materials'
WHERE NOT EXISTS (
    SELECT 1
    FROM category
    WHERE name = 'Education'
);

INSERT INTO category (name, description)
SELECT 'Entertainment', 'Expenses for leisure and entertainment activities'
WHERE NOT EXISTS (
    SELECT 1
    FROM category
    WHERE name = 'Entertainment'
);

INSERT INTO category (name, description)
SELECT 'Food', 'Daily food and grocery expenses'
WHERE NOT EXISTS (
    SELECT 1
    FROM category
    WHERE name = 'Food'
);

INSERT INTO category (name, description)
SELECT 'Fuel', 'Fuel expenses for transportation'
WHERE NOT EXISTS (
    SELECT 1
    FROM category
    WHERE name = 'Fuel'
);

INSERT INTO category (name, description)
SELECT 'Health', 'Medical and healthcare expenses'
WHERE NOT EXISTS (
    SELECT 1
    FROM category
    WHERE name = 'Health'
);

INSERT INTO category (name, description)
SELECT 'Rent', 'Monthly rent for accommodation'
WHERE NOT EXISTS (
    SELECT 1
    FROM category
    WHERE name = 'Rent'}
);

INSERT INTO category (name, description)
SELECT 'Services', 'Expenses for various services (e.g., cleaning, repairs)'
WHERE NOT EXISTS (
    SELECT 1
    FROM category
    WHERE name = 'Services'
);

INSERT INTO category (name, description)
SELECT 'Transport', 'Transportation expenses like buses, taxis, or trains'
WHERE NOT EXISTS (
    SELECT 1
    FROM category
    WHERE name = 'Transport'
);

-- CREATE TABLE users AND inject values

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

INSERT INTO users (name, email)
SELECT 'Alvarez Carla', 'alvarezcarla@gmail.com'
WHERE NOT EXISTS (
    SELECT 1
    FROM users
    WHERE email = 'alvarezcarla@gmail.com'
);

INSERT INTO users (name, email)
SELECT 'Gomez Juan', 'gomezjuan@gmail.com'
WHERE NOT EXISTS (
    SELECT 1
    FROM users
    WHERE email = 'gomezjuan@gmail.com'
);

-- CREATE TABLE expenses AND inject values

CREATE TABLE IF NOT EXISTS expenses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DOUBLE NOT NULL,
    date DATE NOT NULL,
    description VARCHAR(255),
    user_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);

-- INSERT expenses only if they not exist
INSERT INTO expenses (amount, date, description, user_id, category_id)
SELECT 200.0, '2025-03-25', 'Beauty products purchase', 1, 1
WHERE NOT EXISTS (
    SELECT 1
    FROM expenses
    WHERE amount = 200.0 AND date = '2025-03-25' AND description = 'Beauty products purchase');

INSERT INTO expenses (amount, date, description, user_id, category_id)
SELECT 50.0, '2025-03-25', 'Phone bill', 2, 7
WHERE NOT EXISTS (
    SELECT 1
    FROM expenses
    WHERE amount = 50.0AND date = '2025-03-25' AND description = 'Phone bill'
);

INSERT INTO expenses (amount, date, description, user_id, category_id)
SELECT 150.0, '2025-03-26', 'Grocery shopping', 1, 5
WHERE NOT EXISTS (
    SELECT 1
    FROM expenses
    WHERE amount = 150.0 AND date = '2025-03-26' AND description = 'Grocery shopping'
);

INSERT INTO expenses (amount, date, description, user_id, category_id)
SELECT 60.0, '2025-03-26', 'Taxi fare', 2, 10
WHERE NOT EXISTS (
    SELECT 1
    FROM expenses
    WHERE amount = 60.0 AND date = '2025-03-26' AND description = 'Taxi fare'
);
