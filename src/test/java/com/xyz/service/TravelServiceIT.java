package com.xyz.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xyz.common.Common;
import com.xyz.common.Constants;
import com.xyz.model.Commute;
import com.xyz.model.Flight;
import com.xyz.model.Route;
import com.xyz.model.SearchResult;

public class TravelServiceIT extends BaseServiceTest {
	@Autowired
	private TravelService travelService;

	@Autowired
	private RefdataService refdataService;

	@Test
	public void testGetFlights() {
		List<Flight> flights = travelService.getFlights().stream().filter(
				fl -> fl.getFlightId().equals("IND001")).collect(Collectors.toList());

		Assert.assertTrue(flights.size() == 1);
		Assert.assertTrue(flights.get(0).getFlightId().contains("IND001"));
		Assert.assertTrue(flights.get(0).getAirline().getFieldValueName().contains("Indigo"));
	}

	@Test
	public void testGetRoutes() {
		List<Route> routes = travelService.getRoutes().stream().filter(
				rt -> rt.getFromCity().getFieldValueName().equals("Bangalore") && 
				rt.getToCity().getFieldValueName().equals("Hyderabad")).collect(Collectors.toList());

		Assert.assertTrue(routes.size() == 1);
		Assert.assertTrue(routes.get(0).getFromCity().getFieldValueName().equals("Bangalore"));
		Assert.assertTrue(routes.get(0).getToCity().getFieldValueName().equals("Hyderabad"));
	}

	@Test
	public void testSearchFlights() throws Exception {
		Map<String, List<SearchResult>> results = travelService.searchFlights("Hyderabad", "New Delhi", "20180725", "20180728", "7200", null);

		if(results.containsKey(Constants.FLIGHT_DEPARTURE_KEY)) {
			results.get(Constants.FLIGHT_DEPARTURE_KEY).forEach(rt -> {
				String fromCity = "Hyderabad";
				Date deptTime = rt.getCommutes().get(0).getDepartureTime();
				double price = 0.0;
	
				for(Commute commute : rt.getCommutes()) {
					Assert.assertTrue(commute.getRoute().getFromCity().getFieldValueName().equals(fromCity));
					Assert.assertTrue(commute.getArrivalTime().after(deptTime));
					deptTime = commute.getDepartureTime();
					fromCity = commute.getRoute().getToCity().getFieldValueName();
					price += commute.getPrice();
				}
				
				Assert.assertTrue(fromCity.equals("New Delhi"));
				Assert.assertTrue(rt.getPrice() == price);
				Assert.assertTrue(rt.getNoOfStops() == rt.getCommutes().size()-1);
				Assert.assertTrue(rt.getDuration().equals(Common.getTimeDifference(
						rt.getCommutes().get(0).getDepartureTime(), rt.getCommutes().get(rt.getCommutes().size()-1).getArrivalTime())));
			});
		}

		if(results.containsKey(Constants.FLIGHT_RETURN_KEY)) {
			results.get(Constants.FLIGHT_RETURN_KEY).forEach(rt -> {
				String fromCity = "New Delhi";
				Date deptTime = rt.getCommutes().get(0).getDepartureTime();
				double price = 0.0;
	
				for(Commute commute : rt.getCommutes()) {
					Assert.assertTrue(commute.getRoute().getFromCity().getFieldValueName().equals(fromCity));
					Assert.assertTrue(commute.getArrivalTime().after(deptTime));
					deptTime = commute.getDepartureTime();
					fromCity = commute.getRoute().getToCity().getFieldValueName();
					price += commute.getPrice();
				}
				
				Assert.assertTrue(fromCity.equals("Hyderabad"));
				Assert.assertTrue(rt.getPrice() == price);
				Assert.assertTrue(rt.getNoOfStops() == rt.getCommutes().size()-1);
				Assert.assertTrue(rt.getDuration().equals(Common.getTimeDifference(
						rt.getCommutes().get(0).getDepartureTime(), rt.getCommutes().get(rt.getCommutes().size()-1).getArrivalTime())));
			});
		}
	}
	
	@Test
	public void testSearchRoundTripFlights() throws Exception {
		List<Map<String, SearchResult>> results = travelService.searchRoundTripFlights("Hyderabad", "New Delhi", "20180725", "20180728");

		results.forEach(rt -> {
			SearchResult depart = rt.get(Constants.FLIGHT_DEPARTURE_KEY);
			
			String fromCity = "Hyderabad";
			Date deptTime = depart.getCommutes().get(0).getDepartureTime();
			double price = 0.0;

			for(Commute commute : depart.getCommutes()) {
				Assert.assertTrue(commute.getRoute().getFromCity().getFieldValueName().equals(fromCity));
				Assert.assertTrue(commute.getArrivalTime().after(deptTime));
				deptTime = commute.getDepartureTime();
				fromCity = commute.getRoute().getToCity().getFieldValueName();
				price += commute.getPrice();
			}
			
			Assert.assertTrue(fromCity.equals("New Delhi"));
			Assert.assertTrue(depart.getPrice() == price);
			Assert.assertTrue(depart.getNoOfStops() == depart.getCommutes().size()-1);
			Assert.assertTrue(depart.getDuration().equals(Common.getTimeDifference(
					depart.getCommutes().get(0).getDepartureTime(), depart.getCommutes().get(depart.getCommutes().size()-1).getArrivalTime())));

			SearchResult ret = rt.get(Constants.FLIGHT_RETURN_KEY);
			
			fromCity = "New Delhi";
			deptTime = depart.getCommutes().get(0).getDepartureTime();
			price = 0.0;

			for(Commute commute : ret.getCommutes()) {
				Assert.assertTrue(commute.getRoute().getFromCity().getFieldValueName().equals(fromCity));
				fromCity = commute.getRoute().getToCity().getFieldValueName();
				Assert.assertTrue(commute.getArrivalTime().after(deptTime));
				deptTime = commute.getDepartureTime();
				price += commute.getPrice();
			}
			
			Assert.assertTrue(fromCity.equals("Hyderabad"));
			Assert.assertTrue(ret.getPrice() == price);
			Assert.assertTrue(ret.getNoOfStops() == ret.getCommutes().size()-1);
			Assert.assertTrue(depart.getDuration().equals(Common.getTimeDifference(
					depart.getCommutes().get(0).getDepartureTime(), depart.getCommutes().get(depart.getCommutes().size()-1).getArrivalTime())));
		});
	}

	@Test
	public void testAddFlight() throws Exception {
		refdataService.addRFV(Constants.RF_AIRLINE, "Test34");
		travelService.addFlight("INDXXX", "Test34");
		
		List<Flight> flights = travelService.getFlights().stream().filter(
				fl -> fl.getFlightId().equals("INDXXX")).collect(Collectors.toList());

		Assert.assertTrue(flights.size() == 1);
		Assert.assertTrue(flights.get(0).getFlightId().contains("INDXXX"));
		Assert.assertTrue(flights.get(0).getAirline().getFieldValueName().contains("Test34"));
	}
	
	@Test
	public void testAddRoute() throws Exception {
		refdataService.addRFV(Constants.RF_CITY, "Test78");
		refdataService.addRFV(Constants.RF_CITY, "Test89");
		travelService.addRoute("Test78", "Test89");

		List<Route> routes = travelService.getRoutes().stream().filter(
				rt -> rt.getFromCity().getFieldValueName().equals("Test78") && 
				rt.getToCity().getFieldValueName().equals("Test89")).collect(Collectors.toList());

		Assert.assertTrue(routes.size() == 1);
		Assert.assertTrue(routes.get(0).getFromCity().getFieldValueName().equals("Test78"));
		Assert.assertTrue(routes.get(0).getToCity().getFieldValueName().equals("Test89"));
	}

	@Test
	public void testDeleteFlight() throws Exception {
		refdataService.addRFV(Constants.RF_AIRLINE, "Test12");
		travelService.addFlight("INDXXX", "Test12");

		travelService.deleteFlight("INDXXX");
		Assert.assertFalse(travelService.getFlights().contains("INDXXX"));
	}
	
	@Test
	public void testDeleteRoute() throws Exception {
		refdataService.addRFV(Constants.RF_CITY, "Test56");
		refdataService.addRFV(Constants.RF_CITY, "Test67");
		travelService.addRoute("Test56", "Test67");

		travelService.deleteRoute("Test56", "Test67");

		List<Route> routes = travelService.getRoutes().stream().filter(
				rt -> rt.getFromCity().getFieldValueName().equals("Test56") && 
				rt.getToCity().getFieldValueName().equals("Test67")).collect(Collectors.toList());

		Assert.assertTrue(routes.isEmpty());
	}
}
