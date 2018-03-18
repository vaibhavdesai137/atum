
DROP SCHEMA IF EXISTS `atumdb`;
CREATE SCHEMA `atumdb` DEFAULT CHARACTER SET utf8;
USE `atumdb`;

-- -----------------------------------------------------
-- Tables
-- -----------------------------------------------------

CREATE TABLE `members` (
  `id` 						INT 			NOT NULL AUTO_INCREMENT,
  `email` 					VARCHAR(50) 	NOT NULL,
  `created_time` 			TIMESTAMP 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` 			TIMESTAMP 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`email`)
) 
ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

CREATE TABLE `books` (
  `id` 						INT 			NOT NULL AUTO_INCREMENT,
  `title` 					VARCHAR(200) 	NOT NULL,
  `author` 					VARCHAR(200) 	NOT NULL,
  `publisher` 				VARCHAR(200) 	NOT NULL,
  `status` 					VARCHAR(20)		NOT NULL DEFAULT 'AVAILABLE',
  `created_time` 			TIMESTAMP 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` 			TIMESTAMP 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) 
ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

CREATE TABLE `checkouts` (
  `id` 						INT 			NOT NULL AUTO_INCREMENT,
  `book_id` 				INT 			NOT NULL,
  `member_id` 				INT 			NOT NULL,
  `checkout_date` 			TIMESTAMP 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `expected_return_date`	TIMESTAMP 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actual_return_date`		TIMESTAMP 		NULL,
  `created_time` 			TIMESTAMP 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` 			TIMESTAMP 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`book_id`) 	REFERENCES books(`id`),
  FOREIGN KEY (`member_id`) REFERENCES members(`id`)
) 
ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Indexes
-- -----------------------------------------------------
ALTER TABLE `checkouts` ADD INDEX `idx_checkouts_book_id` (`book_id`);
ALTER TABLE `checkouts` ADD INDEX `idx_checkouts_member_id` (`member_id`);


-- -----------------------------------------------------
-- Data
-- -----------------------------------------------------

INSERT INTO members (`email`) VALUES ('sherlock.holmes@bbc.com');
INSERT INTO members (`email`) VALUES ('bar@foo.com');
INSERT INTO members (`email`) VALUES ('abc@xyz.com');
INSERT INTO members (`email`) VALUES ('xyz@abc.com');

INSERT INTO books (`title`, `author`, `publisher`) VALUES ('Lord Of The Rings', 'J . R. R. Tolkien', 'ORielly');
INSERT INTO books (`title`, `author`, `publisher`) VALUES ('Harry Potter', 'J. K. Rowling', 'Some Publisher');
INSERT INTO books (`title`, `author`, `publisher`) VALUES ('Harry Potter', 'J. K. Rowling', 'Some Publisher');
INSERT INTO books (`title`, `author`, `publisher`) VALUES ('Take Of Two Cities', 'Charles Dickens', 'Awesome Publisher');
INSERT INTO books (`title`, `author`, `publisher`) VALUES ('The Hbbit', 'J. R. R. Tolkien', 'Good Publisher');
INSERT INTO books (`title`, `author`, `publisher`) VALUES ('The Hbbit', 'J. R. R. Tolkien', 'Good Publisher');
INSERT INTO books (`title`, `author`, `publisher`) VALUES ('The Hbbit', 'J. R. R. Tolkien', 'Good Publisher');

USE `atumdb`;
