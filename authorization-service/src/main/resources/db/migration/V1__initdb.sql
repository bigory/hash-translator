CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(256) UNIQUE NOT NULL,
    password varchar(256)        NOT NULL,
    role     VARCHAR(8)
);

-- ### Default user:
-- username: admin
-- password: pass123+
INSERT INTO users (username, password, role)
VALUES ('admin', '$2a$10$l.FkrZIhbrcgTh9zssW.j.INQbuZhMyqHOqtfDNKqk0VYRTcyWXpy', 'ADMIN');