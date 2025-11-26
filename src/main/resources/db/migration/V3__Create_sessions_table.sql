CREATE TABLE sessions
(
    id         uuid PRIMARY KEY,
    user_id    int,
    expires_at date
)
