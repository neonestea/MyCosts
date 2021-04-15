CREATE TABLE IF NOT EXISTS regular_costs
(
    id          SERIAL PRIMARY KEY,
    last_date   DATE,
    every_month BOOLEAN,
    period      INTEGER,
    amount      NUMERIC(10, 2),
    category_id INTEGER REFERENCES categories (id),
    account_id  INTEGER REFERENCES accounts (id),
    user_id     VARCHAR(255) REFERENCES users (id)
);

INSERT INTO regular_costs (id, last_date, every_month, period, amount, category_id, account_id, user_id)
VALUES (1, '2021-04-14', false, 1, 1, 1, 1, 107743470699812252368)