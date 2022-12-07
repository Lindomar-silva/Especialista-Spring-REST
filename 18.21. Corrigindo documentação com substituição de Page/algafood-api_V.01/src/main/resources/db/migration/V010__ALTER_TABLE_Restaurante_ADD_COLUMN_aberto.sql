ALTER TABLE restaurante ADD COLUMN aberto BOOLEAN NOT NULL;
UPDATE restaurante SET aberto = TRUE;