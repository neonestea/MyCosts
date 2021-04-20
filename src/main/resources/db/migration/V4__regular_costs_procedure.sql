CREATE PROCEDURE regular_cost_func() AS $$
DECLARE
    regular_cost RECORD;
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
        INSERT INTO costs (date, amount, category_id, account_id, user_id) VALUES
            (current_date, regular_cost.amount, regular_cost.category_id, regular_cost.account_id, regular_cost.user_id);
    END LOOP;
END
$$ LANGUAGE plpgsql