package com.xyz.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.model.Flight;
import com.xyz.model.Route;
import com.xyz.model.SearchResult;
import com.xyz.service.TravelService;

@RestController
public class TravelController {
	@Autowired
	private TravelService travelService;

	@RequestMapping(value = "/flights", method = RequestMethod.GET)
	public List<Flight> getFlights() {
		return travelService.getFlights();
	}

	@RequestMapping(value = "/routes", method = RequestMethod.GET)
	public List<Route> getRoutes() {
		return travelService.getRoutes();
	}

	@RequestMapping(value = "/searchFlights", method = RequestMethod.GET)
	public Map<String, List<SearchResult>> searchFlights(
			@RequestParam(value = "fromCity", required = true) String fromCity,
			@RequestParam(value = "toCity", required = true) String toCity,
			@RequestParam(value = "departureDate", required = true) String departureDate,
			@RequestParam(value = "returnDate", required = false) String returnDate,
			@RequestParam(value = "oneWayPrice", required = false) String oneWayPrice,
			@RequestParam(value = "airline", required = false) String airline) throws Exception {
		return travelService.searchFlights(fromCity, toCity,
				departureDate, returnDate, oneWayPrice, airline);
	}

	@RequestMapping(value = "/searchRoundTripFlights", method = RequestMethod.GET)
	public List<Map<String, SearchResult>> searchRoundTripFlights(
			@RequestParam(value = "fromCity", required = true) String fromCity,
			@RequestParam(value = "toCity", required = true) String toCity,
			@RequestParam(value = "departureDate", required = true) String departureDate,
			@RequestParam(value = "returnDate", required = true) String returnDate) throws Exception {
		return travelService.searchRoundTripFlights(fromCity, toCity, departureDate, returnDate);
	}

	@RequestMapping(value = "/flight/add", method = RequestMethod.POST)
	public void addFlight(String flightId, String airline) throws Exception {
		travelService.addFlight(flightId, airline);
	}

	@RequestMapping(value = "/flight/delete", method = RequestMethod.DELETE)
	public void deleteFlight(
			@RequestParam(value = "flightId", required = true) String flightId) throws Exception {
		travelService.deleteFlight(flightId);
	}

	@RequestMapping(value = "/route/add", method = RequestMethod.POST)
	public void addRoute(String fromCity, String toCity) throws Exception {
		travelService.addRoute(fromCity, toCity);
	}

	@RequestMapping(value = "/route/delete", method = RequestMethod.POST)
	public void deleteRoute(String fromCity, String toCity) throws Exception {
		travelService.deleteRoute(fromCity, toCity);
	}
}
