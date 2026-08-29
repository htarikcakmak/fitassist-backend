-- Eğer users tablosu veritabanında yoksa, otomatik olarak sıfırdan oluşturur.
-- Eğer tablo zaten varsa, hiçbir işlem yapmaz ve mevcut verilerini korur.
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    age INTEGER,
    height FLOAT,
    weight FLOAT,
    goal VARCHAR(255),
    language VARCHAR(255),
    theme_bg VARCHAR(255),
    theme_primary VARCHAR(255),
    image_url TEXT
);

-- Mevcut veritabanında image_url sütunu VARCHAR(255) ise TEXT'e çevir (Base64 resimler sığmıyordu)
ALTER TABLE users ALTER COLUMN image_url TYPE TEXT;