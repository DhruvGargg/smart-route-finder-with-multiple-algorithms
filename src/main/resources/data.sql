INSERT INTO cities (name, latitude, longitude)
VALUES
    ('Delhi', 28.6139, 77.2090),
    ('Noida', 28.5355, 77.3910),
    ('Ghaziabad', 28.6692, 77.4538),
    ('Meerut', 28.9845, 77.7064);

INSERT INTO roads (source, destination, distance, travel_time)
VALUES
    ('Delhi', 'Noida', 15, NULL),
    ('Delhi', 'Ghaziabad', 30, NULL),
    ('Ghaziabad', 'Noida', 12, NULL),
    ('Ghaziabad', 'Meerut', 45, NULL);