package com.xyz.service;

import java.util.List;
import java.util.Map;

import com.xyz.model.Flight;
import com.xyz.model.Route;
import com.xyz.model.SearchResult;

public interface TravelService {
	List<Flight> getFlights();
	List<Route> getRoutes();

	Map<String, List<SearchResult>> searchFlights(String fromCity, String toCity,
			String departureDate, String returnDate, String oneWayPrice, String airline) throws Exception;
	List<Map<String, SearchResult>> searchRoundTripFlights(String fromCity, String toCity,
			String departureDate, String returnDate) throws Exception;

	void addFlight(String flightId, String airline) throws Exception;
	void addRoute(String fromCity, String toCity) throws Exception;

	void deleteFlight(String flightId) throws Exception;
	void deleteRoute(String fromCity, String toCity) throws Exception;
}
