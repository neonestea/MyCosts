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
    amount   decimal(10, 2),
    active   boolean,
    user_id  VARCHAR(255) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS costs
(
    id          SERIAL PRIMARY KEY,
    date        DATE,
    amount      NUMERIC(10, 2),
    amount_usd  NUMERIC(10, 2),
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

CREATE TABLE IF NOT EXISTS regular_costs
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(25),
    last_date   DATE,
    next_date   DATE,
    pay_day     INTEGER,
    every_month BOOLEAN,
    period      INTEGER,
    amount      NUMERIC(10, 2),
    currency    VARCHAR(10),
    category_id INTEGER REFERENCES categories (id),
    account_id  INTEGER REFERENCES accounts (id),
    user_id     VARCHAR(255) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS currency_exchange_rates
(
    id       SERIAL PRIMARY KEY,
    date     DATE,
    currency VARCHAR(10),
    rate     NUMERIC(10, 5)
);