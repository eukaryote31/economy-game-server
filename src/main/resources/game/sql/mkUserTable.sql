-- Makes the main users table
CREATE TABLE `USERS` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`username`	VARCHAR(100) NOT NULL UNIQUE,
	`passhash`	VARCHAR(100) NOT NULL,
	`timeregistered`	BIGINT NOT NULL,
	`balance`	BIGINT NOT NULL
);

CREATE UNIQUE INDEX `UK_USERS_USERNAME` ON `USERS` (
	`username`
);
