-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema sd_tema1
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema sd_tema1
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sd_tema1` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `sd_tema1` ;

-- -----------------------------------------------------
-- Table `sd_tema1`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sd_tema1`.`user` (
  `id` INT NOT NULL,
  `firstName` VARCHAR(255) NOT NULL,
  `lastName` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `username` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sd_tema1`.`destination`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sd_tema1`.`destination` (
  `id` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_kw349sqcyo1k39xa0wn3k3q2r` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sd_tema1`.`package`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sd_tema1`.`package` (
  `id` INT NOT NULL,
  `details` VARCHAR(255) NULL DEFAULT NULL,
  `endDate` DATE NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `price` DOUBLE NOT NULL,
  `startDate` DATE NOT NULL,
  `status` INT NOT NULL,
  `destination_id` INT NOT NULL,
  `currentCapacity` INT NOT NULL,
  `maxCapacity` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_5w2s5w41h46jo7cq46hrb8cx2` (`name` ASC) VISIBLE,
  INDEX `FKtq1gn2h0fe7r8ddc34f6tlit1` (`destination_id` ASC) VISIBLE,
  CONSTRAINT `FKtq1gn2h0fe7r8ddc34f6tlit1`
    FOREIGN KEY (`destination_id`)
    REFERENCES `sd_tema1`.`destination` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sd_tema1`.`bookings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sd_tema1`.`bookings` (
  `user_id` INT NOT NULL,
  `package_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `package_id`),
  INDEX `FKk6at2rbmpwaiqmb7yf98h7un2` (`package_id` ASC) VISIBLE,
  CONSTRAINT `FK65bh1tn1y443fxcah5u36e8fy`
    FOREIGN KEY (`user_id`)
    REFERENCES `sd_tema1`.`user` (`id`),
  CONSTRAINT `FKk6at2rbmpwaiqmb7yf98h7un2`
    FOREIGN KEY (`package_id`)
    REFERENCES `sd_tema1`.`package` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
