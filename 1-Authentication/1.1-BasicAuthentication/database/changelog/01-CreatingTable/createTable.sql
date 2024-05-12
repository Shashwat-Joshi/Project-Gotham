CREATE TABLE auth.user_credentials (
    email VARCHAR(254) PRIMARY KEY,
    password CHAR(60),
    salt CHAR(29)
);

-- Dropping salt as bcrypt saves salt in hash password itself
alter table auth.user_credentials
drop column salt;