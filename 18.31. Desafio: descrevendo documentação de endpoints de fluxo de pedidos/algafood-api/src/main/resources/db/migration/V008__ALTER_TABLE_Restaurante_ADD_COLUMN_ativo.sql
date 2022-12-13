ALTER TABLE restaurante ADD COLUMN ativo BOOLEAN NOT NULL;
UPDATE restaurante SET ativo = TRUE;