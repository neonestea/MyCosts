CREATE FUNCTION regular_cost_func() RETURNS text AS $$
DECLARE
    regular_cost RECORD;
    exchange_rate NUMERIC(10, 4);
BEGIN
    FOR regular_cost in
        SELECT * FROM regular_costs WHERE next_date = current_date
    LOOP
        IF regular_cost.every_month THEN
            UPDATE regular_costs SET last_date = current_date, next_date = current_date +
                                                                           interval '1 month' * regular_cost.period
            WHERE regular_cost.id = id;
        ELSE
            IF not regular_cost.every_month THEN
                UPDATE regular_costs SET last_date = current_date, next_date = current_date +
                                                                               interval '1 day' * regular_cost.period
                WHERE regular_cost.id = id;
            END IF;
        END IF;
        SELECT rate INTO exchange_rate FROM currency_exchange_rates
            WHERE date = current_date and currency = regular_cost.currency;
        INSERT INTO costs (date, amount, category_id, account_id, user_id, amount_usd) VALUES
            (current_date, regular_cost.amount, regular_cost.category_id, regular_cost.account_id, regular_cost.user_id,
             regular_cost.amount * exchange_rate);
    END LOOP;
    RETURN 'text';
END
$$ LANGUAGE plpgsql