# flightSearch

Spring MVC based RESTful API application which allows you to search flights based on criteria like From City, To City, Departure Date, Return Date, Price and Airline. It will provide you information like Duration, Number of Stops, Total Price & Commute for the search criteria.

###Technology Stack

Spring MVC
MyBatis
MySql


## Development

### Build war archive

mvn clean package

Open <http://localhost:8080/XYZTravels/>


##API

###Get cache data

http://localhost:8080/XYZTravels/cities

["Bangalore","Hyderabad","Mumbai","New Delhi"]

http://localhost:8080/XYZTravels/airlines

["Air India","Indigo","SpiceJet","Vistara"]

http://localhost:8080/XYZTravels/flights

[{"flightId":"AIR001","airline":{"value":"Air India"}},{"flightId":"IND003","airline":{"value":"Indigo"}},{"flightId":"VIS001","airline":{"value":"Vistara"}},{"flightId":"AIR002","airline":{"value":"Air India"}},{"flightId":"IND001","airline":{"value":"Indigo"}},{"flightId":"SPI001","airline":{"value":"SpiceJet"}},{"flightId":"IND002","airline":{"value":"Indigo"}}]

http://localhost:8080/XYZTravels/routes

[{"fromCity":{"value":"New Delhi"},"toCity":{"value":"Hyderabad"}},{"fromCity":{"value":"Bangalore"},"toCity":{"value":"Hyderabad"}},{"fromCity":{"value":"Bangalore"},"toCity":{"value":"Mumbai"}},{"fromCity":{"value":"New Delhi"},"toCity":{"value":"Mumbai"}},{"fromCity":{"value":"Mumbai"},"toCity":{"value":"New Delhi"}},{"fromCity":{"value":"New Delhi"},"toCity":{"value":"Bangalore"}},{"fromCity":{"value":"Mumbai"},"toCity":{"value":"Bangalore"}},{"fromCity":{"value":"Hyderabad"},"toCity":{"value":"Mumbai"}},{"fromCity":{"value":"Bangalore"},"toCity":{"value":"New Delhi"}}]

###POST cache data

curl -d "city=Chennai" http://localhost:8080/XYZTravels/city/add
curl -d "airline=Jet Airways" http://localhost:8080/XYZTravels/airline/add
curl -d "flightId=JET001&airline=Jet Airways" http://localhost:8080/XYZTravels/flight/add
curl -d "fromCity=Hyderabad&toCity=Chennai" http://localhost:8080/XYZTravels/route/add

###DELETE cache data

curl -i -X DELETE http://localhost:8080/XYZTravels/route/delete?fromCity=Hyderabad&toCity=Chennai
curl -i -X DELETE http://localhost:8080/XYZTravels/flight/delete?flightId=JET001
curl -i -X DELETE http://localhost:8080/XYZTravels/airline/delete?airline=Jet%20Airways
curl -i -X DELETE http://localhost:8080/XYZTravels/city/delete?city=Chennai

###GET /searchFlights?fromCity={fromCity}&toCity={toCity}&departureDate={departureDate}&returnDate={returndate}&oneWayPrice={price}&airline={airline}
-   fromCity - departure city
-   toCity - arrival city
-   departureDate - departure date in yyyyMMdd format
-   returnDate - return date in yyyyMMdd format
-   oneWayPrice - price for each side
-   airline - specific airline 

Returns all flights combination of forward and return flights and also shows journey time, number of stops, price and commutes.

###One way flight search 
http://localhost:8080/XYZTravels/searchFlights?fromCity=Hyderabad&toCity=New%20Delhi&departureDate=20180725&oneWayPrice=7200

{
  "Departure": [
    {
      "duration": "04:45:00",
      "noOfStops": 1,
      "price": 7000,
      "commutes": [
        {
          "route": {
            "fromCity": {
              "value": "Hyderabad"
            },
            "toCity": {
              "value": "Mumbai"
            }
          },
          "flight": {
            "flightId": "AIR001",
            "airline": {
              "value": "Air India"
            }
          },
          "departureTime": "2018-07-25 04:00:00",
          "arrivalTime": "2018-07-25 05:30:00",
          "price": 2500
        },
        {
          "route": {
            "fromCity": {
              "value": "Mumbai"
            },
            "toCity": {
              "value": "New Delhi"
            }
          },
          "flight": {
            "flightId": "AIR001",
            "airline": {
              "value": "Air India"
            }
          },
          "departureTime": "2018-07-25 07:00:00",
          "arrivalTime": "2018-07-25 08:45:00",
          "price": 4500
        }
      ]
    }
  ]
}

###Both way flight search 
http://localhost:8080/XYZTravels/searchFlights?fromCity=Hyderabad&toCity=New%20Delhi&departureDate=20180725&returnDate=20180728&oneWayPrice=7200

{
  "Departure": [
    {
      "duration": "04:45:00",
      "noOfStops": 1,
      "price": 7000,
      "commutes": [
        {
          "route": {
            "fromCity": {
              "value": "Hyderabad"
            },
            "toCity": {
              "value": "Mumbai"
            }
          },
          "flight": {
            "flightId": "AIR001",
            "airline": {
              "value": "Air India"
            }
          },
          "departureTime": "2018-07-25 04:00:00",
          "arrivalTime": "2018-07-25 05:30:00",
          "price": 2500
        },
        {
          "route": {
            "fromCity": {
              "value": "Mumbai"
            },
            "toCity": {
              "value": "New Delhi"
            }
          },
          "flight": {
            "flightId": "AIR001",
            "airline": {
              "value": "Air India"
            }
          },
          "departureTime": "2018-07-25 07:00:00",
          "arrivalTime": "2018-07-25 08:45:00",
          "price": 4500
        }
      ]
    }
  ],
  "Return": [
    {
      "duration": "02:00:00",
      "noOfStops": 0,
      "price": 4500,
      "commutes": [
        {
          "route": {
            "fromCity": {
              "value": "New Delhi"
            },
            "toCity": {
              "value": "Hyderabad"
            }
          },
          "flight": {
            "flightId": "IND002",
            "airline": {
              "value": "Indigo"
            }
          },
          "departureTime": "2018-07-28 06:00:00",
          "arrivalTime": "2018-07-28 08:00:00",
          "price": 4500
        }
      ]
    },
    {
      "duration": "02:00:00",
      "noOfStops": 0,
      "price": 5000,
      "commutes": [
        {
          "route": {
            "fromCity": {
              "value": "New Delhi"
            },
            "toCity": {
              "value": "Hyderabad"
            }
          },
          "flight": {
            "flightId": "IND002",
            "airline": {
              "value": "Indigo"
            }
          },
          "departureTime": "2018-07-28 16:00:00",
          "arrivalTime": "2018-07-28 18:00:00",
          "price": 5000
        }
      ]
    }
  ]
}


###GET /searchRoundTripFlights?fromCity={fromCity}&toCity={toCity}&departureDate={departureDate}&returnDate={returndate}
-   fromCity - departure city
-   toCity - arrival city
-   departureDate - departure date in yyyyMMdd format
-   returnDate - return date in yyyyMMdd format

Returns best combination of forward and return flights and also shows journey time, number of stops, price and commutes. 

The list consist of:
-   all direct flights if available (for example: DUB - WRO)
-   all interconnected flights with a maximum of one stop if available (for example: DUB - STN -
WRO)
-   For interconnected flights the difference between the arrival and the next departure should be 2h
or greater

###Round trip best flight combination search 
http://localhost:8080/XYZTravels/searchRoundTripFlights?fromCity=Hyderabad&toCity=Mumbai&departureDate=20180725&returnDate=20180728

[
  {
    "Departure": {
      "duration": "01:30:00",
      "noOfStops": 0,
      "price": 2500,
      "commutes": [
        {
          "route": {
            "fromCity": {
              "value": "Hyderabad"
            },
            "toCity": {
              "value": "Mumbai"
            }
          },
          "flight": {
            "flightId": "AIR001",
            "airline": {
              "value": "Air India"
            }
          },
          "departureTime": "2018-07-25 04:00:00",
          "arrivalTime": "2018-07-25 05:30:00",
          "price": 2500
        }
      ]
    },
    "Return": {
      "duration": "11:00:00",
      "noOfStops": 1,
      "price": 9500,
      "commutes": [
        {
          "route": {
            "fromCity": {
              "value": "Mumbai"
            },
            "toCity": {
              "value": "New Delhi"
            }
          },
          "flight": {
            "flightId": "AIR001",
            "airline": {
              "value": "Air India"
            }
          },
          "departureTime": "2018-07-28 07:00:00",
          "arrivalTime": "2018-07-28 08:45:00",
          "price": 4500
        },
        {
          "route": {
            "fromCity": {
              "value": "New Delhi"
            },
            "toCity": {
              "value": "Hyderabad"
            }
          },
          "flight": {
            "flightId": "IND002",
            "airline": {
              "value": "Indigo"
            }
          },
          "departureTime": "2018-07-28 16:00:00",
          "arrivalTime": "2018-07-28 18:00:00",
          "price": 5000
        }
      ]
    }
  },
  {
    "Departure": {
      "duration": "01:00:00",
      "noOfStops": 0,
      "price": 3000,
      "commutes": [
        {
          "route": {
            "fromCity": {
              "value": "Hyderabad"
            },
            "toCity": {
              "value": "Mumbai"
            }
          },
          "flight": {
            "flightId": "IND001",
            "airline": {
              "value": "Indigo"
            }
          },
          "departureTime": "2018-07-25 03:00:00",
          "arrivalTime": "2018-07-25 04:00:00",
          "price": 3000
        }
      ]
    },
    "Return": {
      "duration": "11:00:00",
      "noOfStops": 1,
      "price": 9500,
      "commutes": [
        {
          "route": {
            "fromCity": {
              "value": "Mumbai"
            },
            "toCity": {
              "value": "New Delhi"
            }
          },
          "flight": {
            "flightId": "AIR001",
            "airline": {
              "value": "Air India"
            }
          },
          "departureTime": "2018-07-28 07:00:00",
          "arrivalTime": "2018-07-28 08:45:00",
          "price": 4500
        },
        {
          "route": {
            "fromCity": {
              "value": "New Delhi"
            },
            "toCity": {
              "value": "Hyderabad"
            }
          },
          "flight": {
            "flightId": "IND002",
            "airline": {
              "value": "Indigo"
            }
          },
          "departureTime": "2018-07-28 16:00:00",
          "arrivalTime": "2018-07-28 18:00:00",
          "price": 5000
        }
      ]
    }
  }
]

