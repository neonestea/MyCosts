CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(25),
    password_hash VARCHAR(25)
);

CREATE TABLE IF NOT EXISTS user_categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    user_id INTEGER REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS accounts (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    currency VARCHAR(50),
    amount integer,
    user_id INTEGER REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS costs (
    id SERIAL PRIMARY KEY,
    date DATE,
    amount NUMERIC(10, 2),
    category_id INTEGER REFERENCES user_categories(id),
    user_id INTEGER REFERENCES users(id)
);