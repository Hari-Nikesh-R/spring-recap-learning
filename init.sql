CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    active BOOLEAN,
    salary NUMERIC
);

-- Insert bulk data
INSERT INTO users (name, active, salary)
SELECT
    'User_' || i,
    CASE WHEN i % 2 = 0 THEN true ELSE false END,
    (random() * 100000)::int
FROM generate_series(1, 50000) i;