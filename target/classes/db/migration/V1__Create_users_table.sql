CREATE TABLE users
(
    id       int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    login    VARCHAR(30),
    password VARCHAR(100)
);

