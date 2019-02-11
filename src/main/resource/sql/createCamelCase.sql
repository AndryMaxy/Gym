#create database gym;
USE gym;

CREATE TABLE UserRole (
	UserRoleId INT NOT NULL AUTO_INCREMENT,
    Role VARCHAR (10) NOT NULL,
    PRIMARY KEY (UserRoleId)
);

CREATE TABLE Membership (
	MembershipId INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR (30) NOT NULL,
    VisitCount INT NOT NULL,
    Price INT NOT NULL,
    PRIMARY KEY (MembershipId)
);

CREATE TABLE User (
	UserId INT NOT NULL AUTO_INCREMENT,
  UserRoleId INT NOT NULL,
  Login VARCHAR (30) NOT NULL,
  Hash VARCHAR (60) NOT NULL,
  Salt VARCHAR (60) NOT NULL,
	Name VARCHAR (30) NOT NULL,
	Surname VARCHAR(30) NOT NULL,
	PRIMARY KEY (UserId),
	FOREIGN KEY (UserRoleId) REFERENCES UserRole (UserRoleId),
	UNIQUE (Login)
);

CREATE TABLE Visitor (
	UserId INT NOT NULL,
	Discount TINYINT,
  Vip BOOLEAN,
  Regular BOOLEAN,
  Balance INT NOT NULL,
	PRIMARY KEY (UserId),
	FOREIGN KEY (UserId) REFERENCES User (UserId)
);

CREATE TABLE Booking (
	BookingId INT NOT NULL AUTO_INCREMENT,
  UserId INT NOT NULL,
  VisitCountLeft INT NOT NULL,
  Feedback VARCHAR (500),
	PRIMARY KEY (BookingId),
	FOREIGN KEY (UserId) REFERENCES Visitor (UserId)
);

CREATE TABLE Exercise (
	ExerciseId INT NOT NULL AUTO_INCREMENT,
  Name VARCHAR (30) NOT NULL,
	PRIMARY KEY (ExerciseId)
);

CREATE TABLE Product (
	ProductId INT NOT NULL AUTO_INCREMENT,
  Name VARCHAR (30) NOT NULL,
	PRIMARY KEY (ProductId)

);

CREATE TABLE ProductAppointment (
  UserId INT NOT NULL,
  ProductId INT NOT NULL,
  GramInDay INT NOT NULL,
	FOREIGN KEY (UserId) REFERENCES Visitor (UserId),
	FOREIGN KEY (ProductId) REFERENCES Product (ProductId)
);

CREATE TABLE ExerciseAppointment (
  UserId INT NOT NULL,
  ExerciseId INT NOT NULL,
  SetCount INT NOT NULL,
  RepCount INT NOT NULL,
  Weight INT,
	FOREIGN KEY (UserId) REFERENCES Visitor (UserId),
	FOREIGN KEY (ExerciseId) REFERENCES Exercise (ExerciseId)
);

DROP TABLE ProductAppointment;
DROP TABLE product;
DROP TABLE ExerciseAppointment;
DROP TABLE Exercise;
DROP TABLE Booking;
DROP TABLE Visitor;
DROP TABLE User;
DROP TABLE UserRole;
DROP TABLE Membership;

