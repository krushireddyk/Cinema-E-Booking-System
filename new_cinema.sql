-- MySQL Script generated by MySQL Workbench
-- Mon Oct 24 18:59:06 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema cinema
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cinema
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cinema` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `cinema` ;

-- -----------------------------------------------------
-- Table `cinema`.`paymentcard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`paymentcard` (
  `PaymentID` INT NOT NULL AUTO_INCREMENT COMMENT 'if a same card is used by different customers then we have different paymentID for each customer',
  `UserID` INT NULL DEFAULT NULL,
  `Card_Number` VARCHAR(20) NULL DEFAULT NULL,
  `ExpiryDate` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`PaymentID`),
  INDEX `FOREIGN KEY` (`UserID` ASC) VISIBLE,
  CONSTRAINT `paymentcard_ibfk_1`
    FOREIGN KEY (`UserID`)
    REFERENCES `cinema`.`User` (`UserID`))
ENGINE = InnoDB
AUTO_INCREMENT = 103
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema`.`billingaddress`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`billingaddress` (
  `street` VARCHAR(50) NOT NULL COMMENT 'street name',
  `city` VARCHAR(20) NOT NULL COMMENT 'city name',
  `state` VARCHAR(20) NOT NULL COMMENT 'state name',
  `zipcode` VARCHAR(20) NOT NULL COMMENT 'zipcode number',
  `paymentID` INT NOT NULL,
  `id` INT NOT NULL COMMENT 'Billing address for user',
  PRIMARY KEY (`id`),
  INDEX `fk_paymentID_billingAddress` (`paymentID` ASC) VISIBLE,
  CONSTRAINT `fk_paymentID_billingAddress`
    FOREIGN KEY (`paymentID`)
    REFERENCES `cinema`.`paymentcard` (`PaymentID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema`.`promotion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`promotion` (
  `PromotionID` INT NOT NULL AUTO_INCREMENT,
  `PromotionCode` VARCHAR(225) NULL DEFAULT NULL COMMENT 'a valid promocode will give a discount to the customer.',
  `StartDate` DATE NULL DEFAULT NULL,
  `EndDate` DATE NULL DEFAULT NULL,
  `Promotional_Value` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`PromotionID`))
ENGINE = InnoDB
AUTO_INCREMENT = 100
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema`.`movie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`movie` (
  `MovieID` INT NOT NULL AUTO_INCREMENT,
  `Title` VARCHAR(225) NULL DEFAULT NULL,
  `Category` VARCHAR(225) NULL DEFAULT NULL,
  `MovieCast` VARCHAR(225) NULL DEFAULT NULL,
  `Director` CHAR(50) NULL DEFAULT NULL,
  `Producer` CHAR(50) NULL DEFAULT NULL,
  `Synopsis` VARCHAR(1000) NULL DEFAULT NULL,
  `Review` VARCHAR(225) NULL DEFAULT NULL COMMENT 'this column contains string of reviews',
  `TrailerLink` VARCHAR(300) NULL DEFAULT NULL COMMENT 'this contains a youtube video link ',
  `Picture` LONGBLOB NULL DEFAULT NULL COMMENT 'this column contains images',
  `Rating` INT NULL DEFAULT NULL,
  PRIMARY KEY (`MovieID`))
ENGINE = InnoDB
AUTO_INCREMENT = 100
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema`.`screen`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`screen` (
  `ScreenID` VARCHAR(225) NOT NULL,
  `NumberOfSeats` INT NULL DEFAULT NULL COMMENT 'total number of seats in the screen',
  PRIMARY KEY (`ScreenID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema`.`showdetails`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`showdetails` (
  `MovieID` INT NULL DEFAULT NULL,
  `ScreenID` VARCHAR(225) NULL DEFAULT NULL,
  `ShowID` INT NOT NULL AUTO_INCREMENT,
  `ShowDate` DATE NULL DEFAULT NULL COMMENT 'avalaible dates of the movie',
  `ShowTime` TIME NULL DEFAULT NULL,
  `showDuration` INT NOT NULL COMMENT 'Movie Duration in Minutes',
  PRIMARY KEY (`ShowID`),
  INDEX `fk_MovieID` (`MovieID` ASC) VISIBLE,
  INDEX `fk_ScreenID` (`ScreenID` ASC) VISIBLE,
  CONSTRAINT `fk_MovieID`
    FOREIGN KEY (`MovieID`)
    REFERENCES `cinema`.`movie` (`MovieID`),
  CONSTRAINT `fk_ScreenID`
    FOREIGN KEY (`ScreenID`)
    REFERENCES `cinema`.`screen` (`ScreenID`))
ENGINE = InnoDB
AUTO_INCREMENT = 100
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema`.`usertype`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`usertype` (
  `RoleID` INT NOT NULL AUTO_INCREMENT,
  `UserRole` VARCHAR(225) NULL DEFAULT NULL COMMENT 'role can be Admin or Customer',
  PRIMARY KEY (`RoleID`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema`.`status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`status` (
  `StatusID` INT NOT NULL,
  `Status` VARCHAR(255) NULL DEFAULT NULL COMMENT 'values for this column are INACTIVE,ACTIVE,SUSPENDED',
  PRIMARY KEY (`StatusID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`user` (
  `UserID` INT NOT NULL AUTO_INCREMENT,
  `LastName` VARCHAR(255) NULL DEFAULT NULL,
  `FirstName` VARCHAR(255) NULL DEFAULT NULL,
  `UserName` VARCHAR(255) NOT NULL COMMENT 'user can keep username of his choice based on availability(if a user name is already taken by a user it throws error)',
  `PhoneNumber` VARCHAR(255) NULL DEFAULT NULL,
  `EmailID` VARCHAR(50) NULL DEFAULT NULL,
  `Password` CHAR(50) NULL DEFAULT NULL,
  `StatusID` INT NULL DEFAULT NULL,
  `RoleID` INT NULL DEFAULT NULL,
  PRIMARY KEY (`UserID`),
  UNIQUE INDEX `UserName` (`UserName` ASC) VISIBLE,
  INDEX `fk_RoleID` (`RoleID` ASC) VISIBLE,
  INDEX `fk_statusid` (`StatusID` ASC) VISIBLE,
  CONSTRAINT `fk_RoleID`
    FOREIGN KEY (`RoleID`)
    REFERENCES `cinema`.`usertype` (`RoleID`),
  CONSTRAINT `fk_statusid`
    FOREIGN KEY (`StatusID`)
    REFERENCES `cinema`.`status` (`StatusID`))
ENGINE = InnoDB
AUTO_INCREMENT = 101
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema`.`booking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`booking` (
  `BookingID` INT NOT NULL AUTO_INCREMENT COMMENT 'a unique id generated for every booking',
  `UserName` VARCHAR(255) NULL DEFAULT NULL COMMENT 'username from user table',
  `ShowID` INT NULL DEFAULT NULL,
  `TotalPrice` FLOAT NULL DEFAULT NULL COMMENT 'Total price is the sum of ticket price and sales tax ,online fees',
  `PaymentID` INT NULL DEFAULT NULL,
  `PromoID` INT NULL DEFAULT NULL,
  `NumberOfTickets` INT NOT NULL COMMENT 'No.of tickets in each booking',
  `bookingStatus` VARCHAR(50) NOT NULL DEFAULT 'Booked' COMMENT 'values are Booked or Returned',
  PRIMARY KEY (`BookingID`),
  INDEX `fk_UserName` (`UserName` ASC) VISIBLE,
  INDEX `fk_ShowID` (`ShowID` ASC) VISIBLE,
  INDEX `fk_PromoID` (`PromoID` ASC) VISIBLE,
  INDEX `fk_PaymentID` (`PaymentID` ASC) VISIBLE,
  CONSTRAINT `fk_PaymentID`
    FOREIGN KEY (`PaymentID`)
    REFERENCES `cinema`.`paymentcard` (`PaymentID`),
  CONSTRAINT `fk_PromoID`
    FOREIGN KEY (`PromoID`)
    REFERENCES `cinema`.`promotion` (`PromotionID`),
  CONSTRAINT `fk_ShowID`
    FOREIGN KEY (`ShowID`)
    REFERENCES `cinema`.`showdetails` (`ShowID`),
  CONSTRAINT `fk_UserName`
    FOREIGN KEY (`UserName`)
    REFERENCES `cinema`.`user` (`UserName`))
ENGINE = InnoDB
AUTO_INCREMENT = 100
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema`.`homeaddress`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`homeaddress` (
  `street` VARCHAR(50) NOT NULL COMMENT 'street name',
  `city` VARCHAR(20) NOT NULL COMMENT 'city name',
  `state` VARCHAR(20) NOT NULL COMMENT 'state name',
  `zipcode` VARCHAR(10) NOT NULL COMMENT 'zipcode value',
  `userid` INT NOT NULL,
  `id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_homeaddress_userid` (`userid` ASC) VISIBLE,
  CONSTRAINT `fk_homeaddress_userid`
    FOREIGN KEY (`userid`)
    REFERENCES `cinema`.`user` (`UserID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema`.`seat`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`seat` (
  `SeatID` INT NOT NULL,
  `RowName` VARCHAR(20) NULL DEFAULT NULL COMMENT 'it takes a value from [A-Z]',
  `RowNumber` INT NULL DEFAULT NULL,
  `ScreenID` VARCHAR(225) NULL DEFAULT NULL,
  PRIMARY KEY (`SeatID`),
  INDEX `fk_Seat_ScreenID` (`ScreenID` ASC) VISIBLE,
  CONSTRAINT `fk_Seat_ScreenID`
    FOREIGN KEY (`ScreenID`)
    REFERENCES `cinema`.`screen` (`ScreenID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema`.`tickettype`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`tickettype` (
  `TypeID` INT NOT NULL,
  `CategoryType` VARCHAR(225) NULL DEFAULT NULL COMMENT 'values for this column can be Adult,Senior or Child',
  `Price` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`TypeID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema`.`ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema`.`ticket` (
  `TicketNumber` INT NOT NULL AUTO_INCREMENT,
  `TypeID` INT NULL DEFAULT NULL,
  `BookingID` INT NULL DEFAULT NULL,
  `SeatID` INT NULL DEFAULT NULL,
  PRIMARY KEY (`TicketNumber`),
  INDEX `fk_TypeID` (`TypeID` ASC) VISIBLE,
  INDEX `fk_BookingID` (`BookingID` ASC) VISIBLE,
  INDEX `fk_SeatID` (`SeatID` ASC) VISIBLE,
  CONSTRAINT `fk_BookingID`
    FOREIGN KEY (`BookingID`)
    REFERENCES `cinema`.`booking` (`BookingID`),
  CONSTRAINT `fk_SeatID`
    FOREIGN KEY (`SeatID`)
    REFERENCES `cinema`.`seat` (`SeatID`),
  CONSTRAINT `fk_TypeID`
    FOREIGN KEY (`TypeID`)
    REFERENCES `cinema`.`tickettype` (`TypeID`))
ENGINE = InnoDB
AUTO_INCREMENT = 100
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;






ALTER TABLE `cinema`.`homeaddress` 
CHANGE COLUMN `id` `id` INT NOT NULL AUTO_INCREMENT ;

ALTER TABLE `cinema`.`billingaddress` 
DROP FOREIGN KEY `fk_paymentID_billingAddress`;
ALTER TABLE `cinema`.`billingaddress` 
CHANGE COLUMN `paymentID` `PaymentID` INT NOT NULL ,
CHANGE COLUMN `id` `id` INT NOT NULL AUTO_INCREMENT COMMENT 'Billing address for user' ;
ALTER TABLE `cinema`.`billingaddress` 
ADD CONSTRAINT `fk_paymentID_billingAddress`
  FOREIGN KEY (`PaymentID`)
  REFERENCES `cinema`.`paymentcard` (`PaymentID`);

ALTER TABLE `cinema`.`user` 
ADD COLUMN `PromotionEnabled` INT NULL AFTER `RoleID`;

ALTER TABLE `cinema`.`user` 
CHANGE COLUMN `PromotionEnabled` `PromotionEnabled` INT NULL DEFAULT 0 ;

ALTER TABLE `cinema`.`user` 
CHANGE COLUMN `Password` `Password` VARCHAR(255) NULL DEFAULT NULL ;

ALTER TABLE `cinema`.`user` 
ADD COLUMN `verificationcode` VARCHAR(22) NULL AFTER `PromotionEnabled`;

ALTER TABLE `cinema`.`user` 
ADD UNIQUE INDEX `EmailID_UNIQUE` (`EmailID` ASC) VISIBLE;

ALTER TABLE `cinema`.`user` 
DROP FOREIGN KEY `fk_RoleID`;
ALTER TABLE `cinema`.`user` 
CHANGE COLUMN `RoleID` `RoleID` INT NULL DEFAULT 2 ;
ALTER TABLE `cinema`.`user` 
ADD CONSTRAINT `fk_RoleID`
  FOREIGN KEY (`RoleID`)
  REFERENCES `cinema`.`usertype` (`RoleID`);


ALTER TABLE `cinema`.`user` 
DROP FOREIGN KEY `fk_statusid`;
ALTER TABLE `cinema`.`user` 
CHANGE COLUMN `StatusID` `StatusID` INT NULL DEFAULT 2 ;
ALTER TABLE `cinema`.`user` 
ADD CONSTRAINT `fk_statusid`
  FOREIGN KEY (`StatusID`)
  REFERENCES `cinema`.`status` (`StatusID`);

ALTER TABLE `cinema`.`paymentcard` 
CHANGE COLUMN `Card_Number` `Card_Number` VARCHAR(255) NULL DEFAULT NULL ;



INSERT INTO `cinema`.`status`
(`StatusID`,
`Status`)
VALUES
(1, 'Active');

INSERT INTO `cinema`.`status`
(`StatusID`,
`Status`)
VALUES
(2, 'Inactive');

INSERT INTO `cinema`.`status`
(`StatusID`,
`Status`)
VALUES
(3, 'Suspended');

INSERT INTO `cinema`.`usertype`
(`RoleID`,
`UserRole`)
VALUES
(1,
'Admin');


INSERT INTO `cinema`.`usertype`
(`RoleID`,
`UserRole`)
VALUES
(2,
'Customer');

ALTER TABLE `cinema`.`showdetails` 
DROP FOREIGN KEY `fk_ScreenID`;
ALTER TABLE `cinema`.`showdetails` 
DROP COLUMN `ScreenID`,
DROP INDEX `fk_ScreenID` ;
;


ALTER TABLE `cinema`.`screen` 
ADD COLUMN `ShowID` INT NULL AFTER `NumberOfSeats`;

ALTER TABLE `cinema`.`screen` 
ADD INDEX `fk_screen_showid_idx` (`ShowID` ASC) VISIBLE;
;
ALTER TABLE `cinema`.`screen` 
ADD CONSTRAINT `fk_screen_showid`
  FOREIGN KEY (`ShowID`)
  REFERENCES `cinema`.`showdetails` (`ShowID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `cinema`.`movie` 
ADD UNIQUE INDEX `Title_UNIQUE` (`Title` ASC) VISIBLE;

ALTER TABLE `cinema`.`showdetails` 
ADD COLUMN `screenID` VARCHAR(225) NULL AFTER `showDuration`;

ALTER TABLE `cinema`.`showdetails` 
ADD INDEX `fk_screenID_idx` (`screenID` ASC) VISIBLE;
;
ALTER TABLE `cinema`.`showdetails` 
ADD CONSTRAINT `fk_screenID`
  FOREIGN KEY (`screenID`)
  REFERENCES `cinema`.`screen` (`ScreenID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `cinema`.`screen` 
DROP FOREIGN KEY `fk_screen_showid`;
ALTER TABLE `cinema`.`screen` 
DROP COLUMN `ShowID`,
DROP INDEX `fk_screen_showid_idx` ;
;
