package com.xyz.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.xyz.common.Constants;
import com.xyz.common.Common;
import com.xyz.dao.TravelDAO;
import com.xyz.model.Flight;
import com.xyz.model.ReferenceFieldValue;
import com.xyz.model.Route;
import com.xyz.model.SearchFilter;
import com.xyz.model.SearchResult;
import com.xyz.model.Commute;

public class TravelServiceImpl implements TravelService {
	private TravelDAO travelDAO;
	private ApplicationCache applicationCache;

	public TravelDAO getTravelDAO() {
		return travelDAO;
	}

	public void setTravelDAO(TravelDAO travelDAO) {
		this.travelDAO = travelDAO;
	}

	public ApplicationCache getApplicationCache() {
		return applicationCache;
	}

	public void setApplicationCache(ApplicationCache applicationCache) {
		this.applicationCache = applicationCache;
	}

	@Override
	public List<Flight> getFlights() {
		return applicationCache.getFlights();
	}

	@Override
	public List<Route> getRoutes() {
		return applicationCache.getRoutes();
	}

	@Override
	public Map<String, List<SearchResult>> searchFlights(String fromCity, String toCity,
			String departureDate, String returnDate, String oneWayPrice, String airline) throws Exception {
		SearchFilter filter = getFlightFilter(fromCity, toCity, departureDate, returnDate, oneWayPrice, airline);

		Map<String, List<SearchResult>> searchResultMap = new LinkedHashMap<>();
		List<SearchResult> departureFlights = getFlightResults(Constants.FLIGHT_DEPARTURE_KEY, filter);
		Collections.sort(departureFlights, SearchResult.DEFAULT_SORT);
		searchResultMap.put(Constants.FLIGHT_DEPARTURE_KEY, departureFlights);

		if(filter.getReturnDate() != null) {
			List<SearchResult> returnFlights = getFlightResults(Constants.FLIGHT_RETURN_KEY, filter);
			Collections.sort(returnFlights, SearchResult.DEFAULT_SORT);
			searchResultMap.put(Constants.FLIGHT_RETURN_KEY, returnFlights);
		}

		return searchResultMap;
	}

	@Override
	public List<Map<String, SearchResult>> searchRoundTripFlights(String fromCity, String toCity,
			String departureDate, String returnDate) throws Exception {
		SearchFilter filter = getFlightFilter(fromCity, toCity, departureDate, returnDate, null, null);
		
		if(filter.getDepartureDate().equals(filter.getReturnDate())) {
			throw new Exception("Departure and Return date cannot be same");
		}

		List<SearchResult> departureFlights = getFlightResults(Constants.FLIGHT_DEPARTURE_KEY, filter);
		Collections.sort(departureFlights, SearchResult.DEFAULT_SORT);
		List<SearchResult> returnFlights = getFlightResults(Constants.FLIGHT_RETURN_KEY, filter);
		Collections.sort(returnFlights, SearchResult.DEFAULT_SORT);

		List<Map<String, SearchResult>> searchResultList = new ArrayList<>();
		int depSize = departureFlights.size();
		int retSize = returnFlights.size();

		if(depSize > 0 && retSize > 0) {
			if(depSize < Math.sqrt(Constants.ROUND_TRIP_MAX_RESULTS)) {
				retSize = Math.min(retSize, (Constants.ROUND_TRIP_MAX_RESULTS / depSize));
			}
			else if(retSize < Math.sqrt(Constants.ROUND_TRIP_MAX_RESULTS)) {
				depSize = Math.min(depSize, (Constants.ROUND_TRIP_MAX_RESULTS / retSize));
			}
			
			for(int i = 0 ; i < depSize; i++) {
				for(int j = 0 ; j < retSize ; j++) {
					Map<String, SearchResult> resultMap = new LinkedHashMap<>();
					resultMap.put(Constants.FLIGHT_DEPARTURE_KEY, departureFlights.get(i));
					resultMap.put(Constants.FLIGHT_RETURN_KEY, returnFlights.get(j));
					searchResultList.add(resultMap);
				}
			}
		}

		return searchResultList;
	}

	private void filterResults(Map<Integer, List<Commute>> cityCommuteMap, Integer inputCity, Integer targetCity,
			Set<Integer> visitedCities, int totalStops, double totalPrice, List<Commute> localCommuteList, List<SearchResult> resultCommuteList) {
		visitedCities.add(inputCity);

		if(inputCity.equals(targetCity)) {
			if(totalStops >= 0) {
				SearchResult resultList = new SearchResult();
				resultList.setDuration(Common.getTimeDifference(
						localCommuteList.get(0).getDepartureTime(), localCommuteList.get(totalStops).getArrivalTime()));
				resultList.setNoOfStops(totalStops);
				resultList.setPrice(totalPrice);
				resultList.setCommutes(new ArrayList<>(localCommuteList));
				resultCommuteList.add(resultList);
			}
		}
		else {
			if(cityCommuteMap.containsKey(inputCity)) {
				cityCommuteMap.get(inputCity).forEach(commute -> {
					if(!visitedCities.contains(commute.getRoute().getToCity().getFieldValueId())) {
						if(totalStops < 0 || commute.getDepartureTime().after(localCommuteList.get(totalStops).getDepartureTime())) {
							localCommuteList.add(commute);
							filterResults(cityCommuteMap, commute.getRoute().getToCity().getFieldValueId(), targetCity, 
									visitedCities, totalStops + 1, totalPrice + commute.getPrice(), localCommuteList, resultCommuteList);
							localCommuteList.remove(commute);
						}
					}
				});
			}
		}

		visitedCities.remove(inputCity);
	}
	
	private List<SearchResult> getFlightResults(String mapKey, SearchFilter filter) {
		List<SearchResult> resultCommuteList = new ArrayList<>();
		Date searchDate = null;
		Integer fromCity = null;
		Integer toCity = null;

		if(mapKey.equals(Constants.FLIGHT_DEPARTURE_KEY)) {
			searchDate = filter.getDepartureDate();
			fromCity = filter.getFromCity();
			toCity = filter.getToCity();
		}
		else if(mapKey.equals(Constants.FLIGHT_RETURN_KEY)) {
			searchDate = filter.getReturnDate();
			fromCity = filter.getToCity();
			toCity = filter.getFromCity();
		}

		Map<Integer, List<Commute>> cityCommuteMap = travelDAO.getCityCommuteMap(searchDate, filter.getAirline());
		
		if(!cityCommuteMap.isEmpty()) {
			filterResults(cityCommuteMap, fromCity, toCity,
					new HashSet<>(), -1, 0.00, new ArrayList<>(), resultCommuteList);
			
			if(filter.getPrice() != null) {
				resultCommuteList = resultCommuteList.stream().filter(
						rl -> rl.getPrice().compareTo(filter.getPrice()) <= 0).collect(Collectors.toList());
			}
		}

		return resultCommuteList;
	}

	private SearchFilter getFlightFilter(String fromCity, String toCity,
			String departureDate, String returnDate, String oneWayPrice, String airline) throws Exception {
		SearchFilter filter = new SearchFilter();

		fromCity = Common.toString(fromCity);
		Integer fromCityId = applicationCache.getRFVId(Constants.RF_CITY, fromCity);
		if(fromCityId != null) {
			filter.setFromCity(fromCityId);
		}
		else {
			throw new Exception("Invalid from city " + fromCity);
		}

		toCity = Common.toString(toCity);
		Integer toCityId = applicationCache.getRFVId(Constants.RF_CITY, toCity);
		if(toCityId != null) {
			filter.setToCity(toCityId);
		}
		else {
			throw new Exception("Invalid to city " + toCity);
		}

		departureDate = Common.toString(departureDate);
		try {
			filter.setDepartureDate(Common.getDateFromString(departureDate));
		}
		catch(Exception e) {
			throw new Exception("Invalid departure date " + departureDate + " . Please specify date in format yyyyMMdd", e);
		}

		returnDate = Common.toString(returnDate);
		if(!returnDate.isEmpty()) {
			try {
				filter.setReturnDate(Common.getDateFromString(returnDate));
			}
			catch(Exception e) {
				throw new Exception("Invalid return date " + returnDate + " . Please specify date in format yyyyMMdd", e);
			}
		}

		oneWayPrice = Common.toString(oneWayPrice);
		if(!oneWayPrice.isEmpty()) {
			try {
				filter.setPrice(Double.parseDouble(oneWayPrice));
			}
			catch(Exception e) {
				throw new Exception("Invalid Price " + oneWayPrice, e);
			}
		}

		airline = Common.toString(airline);
		if(!airline.isEmpty()) {
			Integer airlineId = applicationCache.getRFVId(Constants.RF_AIRLINE, airline);
			if(airlineId != null) {
				filter.setAirLine(airlineId);
			}
			else {
				throw new Exception("Invalid airLine " + airline);
			}
		}

		return filter;	
	}
	
	private Flight getFlight(String flightId, String airline, String operation) throws Exception {
		Flight flight = null;

		flightId = Common.toString(flightId);
		if(flightId.isEmpty()) {
			throw new Exception("FlightId cannot be blank/null");
		}

		if(operation.equals(Constants.OPERATION_ADD)) {
			airline = Common.toString(airline);
			if(airline.isEmpty()) {
				throw new Exception("Airline cannot be blank/null");
			}

			if(applicationCache.getFlight(flightId) != null) {
				throw new Exception(flightId + " is already present");
			}

			ReferenceFieldValue rfv = applicationCache.getRFV(Constants.RF_AIRLINE, airline);
			if(rfv == null) {
				throw new Exception(airline + " is not a valid airline");
			}

			flight = new Flight();
			flight.setFlightId(flightId);
			flight.setAirline(rfv);
		}
		else if(operation.equals(Constants.OPERATION_DELETE)) {
			flight = applicationCache.getFlight(flightId);
			if(flight == null) {
				throw new Exception(flightId + " is not present");
			}
		}

		return flight;
	}

	@Override
	public void addFlight(String flightId, String airline) throws Exception {
		travelDAO.addFlight(getFlight(flightId, airline, Constants.OPERATION_ADD));
		applicationCache.refreshFlightMap();
	}

	@Override
	public void deleteFlight(String flightId) throws Exception {
		travelDAO.deleteFlight(getFlight(flightId, null, Constants.OPERATION_DELETE).getFlightId());
		applicationCache.refreshFlightMap();
	}

	private Route getRoute(String fromCity, String toCity, String operation) throws Exception {
		Route route = null;

		fromCity = Common.toString(fromCity);
		if(fromCity.isEmpty()) {
			throw new Exception("From City cannot be blank/null");
		}

		toCity = Common.toString(toCity);
		if(toCity.isEmpty()) {
			throw new Exception("To City cannot be blank/null");
		}

		ReferenceFieldValue fromCityRFV = applicationCache.getRFV(Constants.RF_CITY, fromCity);
		if(fromCityRFV == null) {
			throw new Exception(fromCity + " is not a valid city");
		}

		ReferenceFieldValue toCityRFV = applicationCache.getRFV(Constants.RF_CITY, toCity);
		if(toCityRFV == null) {
			throw new Exception(toCity + " is not a valid city");
		}

		if(operation.equals(Constants.OPERATION_ADD)) {
			if(applicationCache.getRoute(fromCity, toCity) != null) {
				throw new Exception("Route from " + fromCity + " to " + toCity +  " is already present");
			}

			route = new Route();
			route.setFromCity(fromCityRFV);
			route.setToCity(toCityRFV);
		}
		else if(operation.equals(Constants.OPERATION_DELETE)) {
			route = applicationCache.getRoute(fromCity, toCity);

			if(route == null) {
				throw new Exception("Route from " + fromCity + " to " + toCity +  " is not present");
			}
		}

		return route;
	}

	@Override
	public void addRoute(String fromCity, String toCity) throws Exception {
		travelDAO.addRoute(getRoute(fromCity, toCity, Constants.OPERATION_ADD));
		applicationCache.refreshRouteMap();
	}

	@Override
	public void deleteRoute(String fromCity, String toCity) throws Exception {
		travelDAO.deleteRoute(getRoute(fromCity, toCity, Constants.OPERATION_DELETE).getRouteId());
		applicationCache.refreshRouteMap();
	}
}
