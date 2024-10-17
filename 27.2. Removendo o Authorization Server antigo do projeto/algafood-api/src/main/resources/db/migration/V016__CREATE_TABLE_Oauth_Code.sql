CREATE TABLE oauth_code (
	code VARCHAR (256),
	authentication BLOB
) ENGINE = InnoDB DEFAULT CHARSET = UTF8MB4;