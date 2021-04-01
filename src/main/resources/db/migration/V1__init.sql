CREATE TABLE IF NOT EXISTS users
(
    id     VARCHAR(255) PRIMARY KEY,
    active boolean,
    name   VARCHAR(255),
    email  VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS categories
(
    id         SERIAL PRIMARY KEY,
    is_default boolean,
    name       VARCHAR(255),
    name_hash  integer
);

CREATE TABLE IF NOT EXISTS user_category
(
    category_id INTEGER REFERENCES categories (id),
    user_id     VARCHAR(255) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS accounts
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(255),
    currency VARCHAR(255),
    amount   integer,
    user_id  VARCHAR(255) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS costs
(
    id          SERIAL PRIMARY KEY,
    date        DATE,
    amount      NUMERIC(10, 2),
    category_id INTEGER REFERENCES categories (id),
    account_id  INTEGER REFERENCES accounts (id),
    user_id     VARCHAR(255) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS user_role
(
    roles   VARCHAR(255),
    user_id VARCHAR(255) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS month_costs
(
    id          SERIAL PRIMARY KEY,
    amount      NUMERIC(10, 2),
    start_date  DATE,
    category_id INTEGER REFERENCES categories (id),
    account_id  INTEGER REFERENCES accounts (id),
    user_id     VARCHAR(255) REFERENCES users (id)
);