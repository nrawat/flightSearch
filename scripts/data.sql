
INSERT INTO ReferenceField (FieldName)
SELECT * FROM (SELECT 'Airline') AS tmp
WHERE NOT EXISTS (SELECT FieldName FROM ReferenceField WHERE FieldName = 'Airline');

INSERT INTO ReferenceField (FieldName)
SELECT * FROM (SELECT 'City') AS tmp
WHERE NOT EXISTS (SELECT FieldName FROM ReferenceField WHERE FieldName = 'City');

INSERT INTO ReferenceFieldValue (FieldId, FieldValueName)
SELECT * FROM (SELECT 1, 'Indigo') AS tmp
WHERE NOT EXISTS (SELECT FieldValueName FROM ReferenceFieldValue WHERE FieldValueName = 'Indigo' AND FieldId = 1);

INSERT INTO ReferenceFieldValue (FieldId, FieldValueName)
SELECT * FROM (SELECT 1, 'Air India') AS tmp
WHERE NOT EXISTS (SELECT FieldValueName FROM ReferenceFieldValue WHERE FieldValueName = 'Air India' AND FieldId = 1);

INSERT INTO ReferenceFieldValue (FieldId, FieldValueName)
SELECT * FROM (SELECT 1, 'SpiceJet') AS tmp
WHERE NOT EXISTS (SELECT FieldValueName FROM ReferenceFieldValue WHERE FieldValueName = 'SpiceJet' AND FieldId = 1);

INSERT INTO ReferenceFieldValue (FieldId, FieldValueName)
SELECT * FROM (SELECT 1, 'Vistara') AS tmp
WHERE NOT EXISTS (SELECT FieldValueName FROM ReferenceFieldValue WHERE FieldValueName = 'Vistara' AND FieldId = 1);

INSERT INTO ReferenceFieldValue (FieldId, FieldValueName)
SELECT * FROM (SELECT 2, 'Bangalore') AS tmp
WHERE NOT EXISTS (SELECT FieldValueName FROM ReferenceFieldValue WHERE FieldValueName = 'Bangalore' AND FieldId = 2);

INSERT INTO ReferenceFieldValue (FieldId, FieldValueName)
SELECT * FROM (SELECT 2, 'Hyderabad') AS tmp
WHERE NOT EXISTS (SELECT FieldValueName FROM ReferenceFieldValue WHERE FieldValueName = 'Hyderabad' AND FieldId = 2);

INSERT INTO ReferenceFieldValue (FieldId, FieldValueName)
SELECT * FROM (SELECT 2, 'Mumbai') AS tmp
WHERE NOT EXISTS (SELECT FieldValueName FROM ReferenceFieldValue WHERE FieldValueName = 'Mumbai' AND FieldId = 2);

INSERT INTO ReferenceFieldValue (FieldId, FieldValueName)
SELECT * FROM (SELECT 2, 'New Delhi') AS tmp
WHERE NOT EXISTS (SELECT FieldValueName FROM ReferenceFieldValue WHERE FieldValueName = 'New Delhi' AND FieldId = 2);

INSERT INTO Flight(FlightId, Airline)
SELECT * FROM (SELECT 'IND001', 1) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Flight WHERE FlightId = 'IND001');

INSERT INTO Flight(FlightId, Airline)
SELECT * FROM (SELECT 'IND002', 1) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Flight WHERE FlightId = 'IND002');

INSERT INTO Flight(FlightId, Airline)
SELECT * FROM (SELECT 'AIR001', 2) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Flight WHERE FlightId = 'AIR001');

INSERT INTO Flight(FlightId, Airline)
SELECT * FROM (SELECT 'AIR002', 2) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Flight WHERE FlightId = 'AIR002');

INSERT INTO Flight(FlightId, Airline)
SELECT * FROM (SELECT 'SPI001', 3) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Flight WHERE FlightId = 'SPI001');

INSERT INTO Flight(FlightId, Airline)
SELECT * FROM (SELECT 'VIS001', 4) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Flight WHERE FlightId = 'VIS001');

INSERT INTO Route(FromCity, ToCity)
SELECT * FROM (SELECT 5, 6) AS tmp
WHERE NOT EXISTS (SELECT RouteId FROM Route WHERE FromCity = 5 AND ToCity = 6);

INSERT INTO Route(FromCity, ToCity)
SELECT * FROM (SELECT 5, 7) AS tmp
WHERE NOT EXISTS (SELECT RouteId FROM Route WHERE FromCity = 5 AND ToCity = 7);

INSERT INTO Route(FromCity, ToCity)
SELECT * FROM (SELECT 6, 7) AS tmp
WHERE NOT EXISTS (SELECT RouteId FROM Route WHERE FromCity = 6 AND ToCity = 7);

INSERT INTO Route(FromCity, ToCity)
SELECT * FROM (SELECT 7, 5) AS tmp
WHERE NOT EXISTS (SELECT RouteId FROM Route WHERE FromCity = 7 AND ToCity = 5);

INSERT INTO Route(FromCity, ToCity)
SELECT * FROM (SELECT 7, 8) AS tmp
WHERE NOT EXISTS (SELECT RouteId FROM Route WHERE FromCity = 7 AND ToCity = 8);

INSERT INTO Route(FromCity, ToCity)
SELECT * FROM (SELECT 8, 5) AS tmp
WHERE NOT EXISTS (SELECT RouteId FROM Route WHERE FromCity = 8 AND ToCity = 5);

INSERT INTO Route(FromCity, ToCity)
SELECT * FROM (SELECT 8, 6) AS tmp
WHERE NOT EXISTS (SELECT RouteId FROM Route WHERE FromCity = 8 AND ToCity = 6);

INSERT INTO Route(FromCity, ToCity)
SELECT * FROM (SELECT 8, 7) AS tmp
WHERE NOT EXISTS (SELECT RouteId FROM Route WHERE FromCity = 8 AND ToCity = 7);

INSERT INTO Commute(FlightId, RouteId, DepartureTime, ArrivalTime, Price)
SELECT * FROM (SELECT 'IND001', 1, DATE_FORMAT("2018-07-25 00:00:00", "%Y-%m-%d %H:%i:%s"), DATE_FORMAT("2018-07-25 01:30:00", "%Y-%m-%d %H:%i:%s"), 1500.00) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Commute WHERE FlightId = 'IND001' AND DepartureTime = '2018-07-25 00:00:00');

INSERT INTO Commute(FlightId, RouteId, DepartureTime, ArrivalTime, Price)
SELECT * FROM (SELECT 'IND001', 3, DATE_FORMAT("2018-07-25 03:00:00", "%Y-%m-%d %H:%i:%s"), DATE_FORMAT("2018-07-25 04:00:00", "%Y-%m-%d %H:%i:%s"), 3000.00) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Commute WHERE FlightId = 'IND001' AND DepartureTime = '2018-07-25 03:00:00');

INSERT INTO Commute(FlightId, RouteId, DepartureTime, ArrivalTime, Price)
SELECT * FROM (SELECT 'IND002', 7, DATE_FORMAT("2018-07-25 06:00:00", "%Y-%m-%d %H:%i:%s"), DATE_FORMAT("2018-07-25 08:00:00", "%Y-%m-%d %H:%i:%s"), 4500.00) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Commute WHERE FlightId = 'IND002' AND DepartureTime = '2018-07-25 06:00:00');

INSERT INTO Commute(FlightId, RouteId, DepartureTime, ArrivalTime, Price)
SELECT * FROM (SELECT 'IND002', 7, DATE_FORMAT("2018-07-25 16:00:00", "%Y-%m-%d %H:%i:%s"), DATE_FORMAT("2018-07-25 18:00:00", "%Y-%m-%d %H:%i:%s"), 5000.00) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Commute WHERE FlightId = 'IND002' AND DepartureTime = '2018-07-25 16:00:00');

INSERT INTO Commute(FlightId, RouteId, DepartureTime, ArrivalTime, Price)
SELECT * FROM (SELECT 'AIR001', 3, DATE_FORMAT("2018-07-25 04:00:00", "%Y-%m-%d %H:%i:%s"), DATE_FORMAT("2018-07-25 05:30:00", "%Y-%m-%d %H:%i:%s"), 2500.00) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Commute WHERE FlightId = 'AIR001' AND DepartureTime = '2018-07-25 04:00:00');

INSERT INTO Commute(FlightId, RouteId, DepartureTime, ArrivalTime, Price)
SELECT * FROM (SELECT 'AIR001', 5, DATE_FORMAT("2018-07-25 07:00:00", "%Y-%m-%d %H:%i:%s"), DATE_FORMAT("2018-07-25 08:45:00", "%Y-%m-%d %H:%i:%s"), 4500.00) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Commute WHERE FlightId = 'AIR001' AND DepartureTime = '2018-07-25 07:00:00');

INSERT INTO Commute(FlightId, RouteId, DepartureTime, ArrivalTime, Price)
SELECT * FROM (SELECT 'AIR002', 2, DATE_FORMAT("2018-07-25 10:00:00", "%Y-%m-%d %H:%i:%s"), DATE_FORMAT("2018-07-25 11:00:00", "%Y-%m-%d %H:%i:%s"), 1200.00) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Commute WHERE FlightId = 'AIR002' AND DepartureTime = '2018-07-25 10:00:00');

INSERT INTO Commute(FlightId, RouteId, DepartureTime, ArrivalTime, Price)
SELECT * FROM (SELECT 'AIR002', 4, DATE_FORMAT("2018-07-25 21:00:00", "%Y-%m-%d %H:%i:%s"), DATE_FORMAT("2018-07-25 22:15:00", "%Y-%m-%d %H:%i:%s"), 1100.00) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Commute WHERE FlightId = 'AIR002' AND DepartureTime = '2018-07-25 21:00:00');

INSERT INTO Commute(FlightId, RouteId, DepartureTime, ArrivalTime, Price)
SELECT * FROM (SELECT 'SPI001', 6, DATE_FORMAT("2018-07-25 18:00:00", "%Y-%m-%d %H:%i:%s"), DATE_FORMAT("2018-07-25 20:00:00", "%Y-%m-%d %H:%i:%s"), 2500.00) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Commute WHERE FlightId = 'SPI001' AND DepartureTime = '2018-07-25 18:00:00');

INSERT INTO Commute(FlightId, RouteId, DepartureTime, ArrivalTime, Price)
SELECT * FROM (SELECT 'VIS001', 8, DATE_FORMAT("2018-07-25 21:45:00", "%Y-%m-%d %H:%i:%s"), DATE_FORMAT("2018-07-26 00:00:00", "%Y-%m-%d %H:%i:%s"), 4100.00) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Commute WHERE FlightId = 'VIS001' AND DepartureTime = '2018-07-25 21:45:00');


INSERT INTO Commute(FlightId, RouteId, DepartureTime, ArrivalTime, Price)
SELECT * FROM (SELECT 'IND001', 1, DATE_FORMAT("2018-07-28 00:00:00", "%Y-%m-%d %H:%i:%s"), DATE_FORMAT("2018-07-28 01:30:00", "%Y-%m-%d %H:%i:%s"), 1500.00) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Commute WHERE FlightId = 'IND001' AND DepartureTime = '2018-07-28 00:00:00');

INSERT INTO Commute(FlightId, RouteId, DepartureTime, ArrivalTime, Price)
SELECT * FROM (SELECT 'IND001', 3, DATE_FORMAT("2018-07-28 03:00:00", "%Y-%m-%d %H:%i:%s"), DATE_FORMAT("2018-07-28 04:00:00", "%Y-%m-%d %H:%i:%s"), 3000.00) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Commute WHERE FlightId = 'IND001' AND DepartureTime = '2018-07-28 03:00:00');

INSERT INTO Commute(FlightId, RouteId, DepartureTime, ArrivalTime, Price)
SELECT * FROM (SELECT 'IND002', 7, DATE_FORMAT("2018-07-28 06:00:00", "%Y-%m-%d %H:%i:%s"), DATE_FORMAT("2018-07-28 08:00:00", "%Y-%m-%d %H:%i:%s"), 4500.00) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Commute WHERE FlightId = 'IND002' AND DepartureTime = '2018-07-28 06:00:00');

INSERT INTO Commute(FlightId, RouteId, DepartureTime, ArrivalTime, Price)
SELECT * FROM (SELECT 'IND002', 7, DATE_FORMAT("2018-07-28 16:00:00", "%Y-%m-%d %H:%i:%s"), DATE_FORMAT("2018-07-28 18:00:00", "%Y-%m-%d %H:%i:%s"), 5000.00) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Commute WHERE FlightId = 'IND002' AND DepartureTime = '2018-07-28 16:00:00');

INSERT INTO Commute(FlightId, RouteId, DepartureTime, ArrivalTime, Price)
SELECT * FROM (SELECT 'AIR001', 3, DATE_FORMAT("2018-07-28 04:00:00", "%Y-%m-%d %H:%i:%s"), DATE_FORMAT("2018-07-28 05:30:00", "%Y-%m-%d %H:%i:%s"), 2500.00) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Commute WHERE FlightId = 'AIR001' AND DepartureTime = '2018-07-28 04:00:00');

INSERT INTO Commute(FlightId, RouteId, DepartureTime, ArrivalTime, Price)
SELECT * FROM (SELECT 'AIR001', 5, DATE_FORMAT("2018-07-28 07:00:00", "%Y-%m-%d %H:%i:%s"), DATE_FORMAT("2018-07-28 08:45:00", "%Y-%m-%d %H:%i:%s"), 4500.00) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Commute WHERE FlightId = 'AIR001' AND DepartureTime = '2018-07-28 07:00:00');

INSERT INTO Commute(FlightId, RouteId, DepartureTime, ArrivalTime, Price)
SELECT * FROM (SELECT 'AIR002', 2, DATE_FORMAT("2018-07-28 10:00:00", "%Y-%m-%d %H:%i:%s"), DATE_FORMAT("2018-07-28 11:00:00", "%Y-%m-%d %H:%i:%s"), 1200.00) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Commute WHERE FlightId = 'AIR002' AND DepartureTime = '2018-07-28 10:00:00');

INSERT INTO Commute(FlightId, RouteId, DepartureTime, ArrivalTime, Price)
SELECT * FROM (SELECT 'AIR002', 4, DATE_FORMAT("2018-07-28 21:00:00", "%Y-%m-%d %H:%i:%s"), DATE_FORMAT("2018-07-28 22:15:00", "%Y-%m-%d %H:%i:%s"), 1100.00) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Commute WHERE FlightId = 'AIR002' AND DepartureTime = '2018-07-28 21:00:00');

INSERT INTO Commute(FlightId, RouteId, DepartureTime, ArrivalTime, Price)
SELECT * FROM (SELECT 'SPI001', 6, DATE_FORMAT("2018-07-28 18:00:00", "%Y-%m-%d %H:%i:%s"), DATE_FORMAT("2018-07-28 20:00:00", "%Y-%m-%d %H:%i:%s"), 2500.00) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Commute WHERE FlightId = 'SPI001' AND DepartureTime = '2018-07-28 18:00:00');

INSERT INTO Commute(FlightId, RouteId, DepartureTime, ArrivalTime, Price)
SELECT * FROM (SELECT 'VIS001', 8, DATE_FORMAT("2018-07-28 21:45:00", "%Y-%m-%d %H:%i:%s"), DATE_FORMAT("2018-07-29 00:00:00", "%Y-%m-%d %H:%i:%s"), 4100.00) AS tmp
WHERE NOT EXISTS (SELECT FlightId FROM Commute WHERE FlightId = 'VIS001' AND DepartureTime = '2018-07-28 21:45:00');
