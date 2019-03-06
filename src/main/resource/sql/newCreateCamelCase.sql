CREATE DATABASE Gym CHARACTER SET utf8;
USE Gym;

CREATE TABLE UserRole
(
  UserRoleId INT         NOT NULL AUTO_INCREMENT,
  Role       VARCHAR(10) NOT NULL,
  PRIMARY KEY (UserRoleId)
);

CREATE TABLE Membership
(
  MembershipId INT         NOT NULL AUTO_INCREMENT,
  Name         VARCHAR(30) NOT NULL,
  VisitCount   INT         NOT NULL,
  Price INT NOT NULL,
  PRIMARY KEY (MembershipId)
);

CREATE TABLE User
(
  UserId     INT         NOT NULL AUTO_INCREMENT,
  UserRoleId INT         NOT NULL,
  Login      VARCHAR(30) NOT NULL,
  Hash       VARCHAR(60) NOT NULL,
  Salt       VARCHAR(60) NOT NULL,
  Name       VARCHAR(30) NOT NULL,
  Surname    VARCHAR(30) NOT NULL,
  Discount TINYINT,
  Balance  INT,
  PRIMARY KEY (UserId),
  FOREIGN KEY (UserRoleId) REFERENCES UserRole (UserRoleId),
  UNIQUE (Login)

);

CREATE TABLE Booking
(
  BookingId      INT NOT NULL AUTO_INCREMENT,
  UserId         INT NOT NULL,
  MembershipId INT NOT NULL,
  VisitCountLeft  INT,
  NeedAppointment  TINYINT NOT NULL,
  Feedback       VARCHAR(500),
  PRIMARY KEY (BookingId),
  FOREIGN KEY (UserId) REFERENCES User (UserId) ON DELETE CASCADE,
  FOREIGN KEY (MembershipId) REFERENCES Membership (MembershipId)
);

CREATE TABLE Exercise
(
  ExerciseId INT         NOT NULL AUTO_INCREMENT,
  Name       VARCHAR(30) NOT NULL,
  PRIMARY KEY (ExerciseId)
);

CREATE TABLE Product
(
  ProductId INT         NOT NULL AUTO_INCREMENT,
  Name      VARCHAR(30) NOT NULL,
  PRIMARY KEY (ProductId)

);

CREATE TABLE ProductAppointment
(
  BookingId  INT NOT NULL,
  ProductId INT NOT NULL,
  GramInDay INT NOT NULL,
  FOREIGN KEY (BookingId) REFERENCES Booking (BookingId) ON DELETE CASCADE,
  FOREIGN KEY (ProductId) REFERENCES Product (ProductId)
);

CREATE TABLE ExerciseAppointment
(
  BookingId  INT NOT NULL,
  ExerciseId INT NOT NULL,
  SetCount   INT NOT NULL,
  RepCount   INT NOT NULL,
  Weight     INT,
  FOREIGN KEY (BookingId) REFERENCES Booking (BookingId) ON DELETE CASCADE,
  FOREIGN KEY (ExerciseId) REFERENCES Exercise (ExerciseId)
);

DROP TABLE ProductAppointment;
DROP TABLE ExerciseAppointment;
DROP TABLE Exercise;
DROP TABLE Product;
DROP TABLE Booking;
DROP TABLE User;
DROP TABLE UserRole;
DROP TABLE Membership;

DROP DATABASE gym;

SELECT default_character_set_name FROM information_schema.SCHEMATA WHERE schema_name = 'gym';

ALTER DATABASE gym DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
ALTER TABLE gym.user CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;
ALTER TABLE gym.user CONVERT TO CHARACTER SET utf8;

SHOW TABLE STATUS;

