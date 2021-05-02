CREATE OR REPLACE FUNCTION delete_costs_func() RETURNS text AS $$
BEGIN
    DELETE FROM costs WHERE costs.date < current_date - interval '2 month';
    RETURN 'text';
END
$$ LANGUAGE plpgsql