package com.xyz.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.xyz.dao.RefdataDAO;
import com.xyz.dao.TravelDAO;
import com.xyz.model.Flight;
import com.xyz.model.ReferenceField;
import com.xyz.model.ReferenceFieldValue;
import com.xyz.model.Route;

public class ApplicationCache {
	private RefdataDAO refdataDAO;
	private TravelDAO travelDAO;

	private Map<String, Map<String, ReferenceFieldValue>> refdataMap;
	private Map<String, ReferenceField> refFieldMap;
	private Map<String, Flight> flightMap;
	private Map<String, Route> routeMap;

	public RefdataDAO getRefdataDAO() {
		return refdataDAO;
	}

	public void setRefdataDAO(RefdataDAO refdataDAO) {
		this.refdataDAO = refdataDAO;
	}

	public TravelDAO getTravelDAO() {
		return travelDAO;
	}

	public void setTravelDAO(TravelDAO travelDAO) {
		this.travelDAO = travelDAO;
	}

	public void init() {
		refreshRefdataMap();
		refreshFlightMap();
		refreshRouteMap();
	}

	public void refreshRefdataMap() {
		List<ReferenceField> refFields = refdataDAO.getReferenceFields();
		Map<String, Map<String, ReferenceFieldValue>> tempRefDataMap = new HashMap<>();
		Map<String, ReferenceField> tempRefFieldMap = new HashMap<>();

		refFields.forEach(ref -> {
			List<ReferenceFieldValue> refFieldValues = refdataDAO.getRFVByFieldId(ref.getFieldId());
			Map<String, ReferenceFieldValue> tempMap = new HashMap<>();
			
			refFieldValues.forEach(refValue -> {
				tempMap.put(refValue.getFieldValueName().toUpperCase(), refValue);
			});

			tempRefDataMap.put(ref.getFieldName(), tempMap);
			tempRefFieldMap.put(ref.getFieldName(), ref);
		});

		this.refFieldMap = tempRefFieldMap;
		this.refdataMap = tempRefDataMap;
	}

	public void refreshFlightMap() {
		List<Flight> flights = travelDAO.getFlights();
		Map<String, Flight> tempFlightMap = new HashMap<>();
		
		flights.forEach(fl -> {
			tempFlightMap.put(fl.getFlightId().toUpperCase(), fl);
		});

		this.flightMap = tempFlightMap;
	}

	public void refreshRouteMap() {
		List<Route> routes = travelDAO.getRoutes();
		Map<String, Route> tempRouteMap = new HashMap<>();
		
		routes.forEach(rt -> {
			tempRouteMap.put(rt.getFromCity().getFieldValueName().toUpperCase() 
					+ "->" + rt.getToCity().getFieldValueName().toUpperCase(), rt);
		});

		this.routeMap = tempRouteMap;
	}

	public List<String> getRFVListFromFieldName(String fieldName) {
		return refdataMap.get(fieldName).values().stream().map(
				rfv -> rfv.getFieldValueName()).sorted().collect(Collectors.toList());
	}

	public ReferenceFieldValue getRFV(String fieldName, String fieldValueName) {
		return refdataMap.get(fieldName).get(fieldValueName.toUpperCase());
	}

	public Integer getRFVId(String fieldName, String fieldValueName) {
		ReferenceFieldValue rfv = getRFV(fieldName, fieldValueName);

		if(rfv != null) {
			return rfv.getFieldValueId();
		}
	
		return null;
	}

	public ReferenceField getRefFieldFromFieldName(String fieldName) {
		return refFieldMap.get(fieldName);
	}

	public List<Flight> getFlights() {
		return new ArrayList<>(flightMap.values());
	}

	public Flight getFlight(String flightId) {
		return flightMap.get(flightId.toUpperCase());
	}

	public List<Route> getRoutes() {
		return new ArrayList<>(routeMap.values());
	}

	public Route getRoute(String fromCity, String toCity) {
		return routeMap.get(fromCity.toUpperCase() 
				+ "->" + toCity.toUpperCase());
	}
}
