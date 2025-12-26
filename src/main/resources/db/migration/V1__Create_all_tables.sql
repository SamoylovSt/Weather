CREATE TABLE IF NOT EXISTS users
(
    id       bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    login    VARCHAR(30)  not null unique,
    password VARCHAR(100) not null
);

CREATE TABLE IF NOT EXISTS locations
(
    id        bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name      VARCHAR(30)   not null,
    user_id   bigint        not null,
    latitude  decimal(9, 6) not null,
    longitude decimal(9, 6) not null,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS sessions
(
    id         uuid PRIMARY KEY,
    user_id    bigint    not null,
    expires_at timestamp not null,
    FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE
);
alter TABLE locations
ADD CONSTRAINT un_location_name_user_id UNIQUE (user_id, name);

