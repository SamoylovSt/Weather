CREATE TABLE   locations
(
    id        int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name      VARCHAR(30),
    user_id   int,
    latitude  decimal,
    longitude decimal
,
    FOREIGN KEY (user_id) REFERENCES users (id)
);