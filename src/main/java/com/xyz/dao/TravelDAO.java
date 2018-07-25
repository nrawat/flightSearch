package com.xyz.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xyz.model.Flight;
import com.xyz.model.Route;
import com.xyz.model.Commute;

public interface TravelDAO {
	List<Flight> getFlights();
	List<Route> getRoutes();
	Map<Integer, List<Commute>> getCityCommuteMap(Date searchDate, Integer airline);

	void addFlight(Flight flight);
	void deleteFlight(String flightId);

	void addRoute(Route route);
	void deleteRoute(int routeId);
}
