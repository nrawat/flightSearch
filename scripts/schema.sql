
CREATE DATABASE IF NOT EXISTS Travel;

USE Travel;

CREATE TABLE IF NOT EXISTS
ReferenceField (FieldId int AUTO_INCREMENT,
  FieldName varchar(50) UNIQUE NOT NULL,
  PRIMARY KEY (FieldId));

CREATE TABLE IF NOT EXISTS
ReferenceFieldValue (FieldValueId int AUTO_INCREMENT,
  FieldId int NOT NULL,
  FieldValueName varchar(50) NOT NULL,
  PRIMARY KEY (FieldValueId),
  UNIQUE KEY (FieldId, FieldValueName),
  FOREIGN KEY (FieldId) REFERENCES ReferenceField(FieldId));

CREATE TABLE IF NOT EXISTS
Flight (FlightId varchar(6),
  AirLine int NOT NULL,
  PRIMARY KEY (FlightId),
  FOREIGN KEY (AirLine) REFERENCES ReferenceFieldValue(FieldValueId));

CREATE TABLE IF NOT EXISTS
Route (RouteId int AUTO_INCREMENT,
  FromCity int NOT NULL,
  ToCity int NOT NULL,
  PRIMARY KEY (RouteId),
  FOREIGN KEY (FromCity) REFERENCES ReferenceFieldValue(FieldValueId),
  FOREIGN KEY (ToCity) REFERENCES ReferenceFieldValue(FieldValueId));
 
CREATE TABLE IF NOT EXISTS
Commute (CommuteId int AUTO_INCREMENT,
  RouteId int NOT NULL,
  FlightId varchar(6) NOT NULL,
  DepartureTime datetime NOT NULL,
  ArrivalTime datetime NOT NULL,
  Price decimal(8,2) NOT NULL,
  PRIMARY KEY (CommuteId),
  FOREIGN KEY (RouteId) REFERENCES Route(RouteId),
  FOREIGN KEY (FlightId) REFERENCES Flight(FlightId));
