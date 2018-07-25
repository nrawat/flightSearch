package com.xyz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.common.Constants;
import com.xyz.service.RefdataService;

@RestController
public class RefdataController {
	@Autowired
	private RefdataService refdataService;

	@RequestMapping(value = "/airlines", method = RequestMethod.GET)
	public List<String> getAirlines() {
		return refdataService.getAirlines();
	}

	@RequestMapping(value = "/cities", method = RequestMethod.GET)
	public List<String> getCities() {
		return refdataService.getCities();
	}

	@RequestMapping(value = "/airline/add", method = RequestMethod.POST)
	public void addAirline(String airline) throws Exception {
		refdataService.addRFV(Constants.RF_AIRLINE, airline);
	}

	@RequestMapping(value = "/airline/delete", method = RequestMethod.DELETE)
	public void deleteAirline(
			@RequestParam(value = "airline", required = true) String airline) throws Exception {
		refdataService.deleteRFV(Constants.RF_AIRLINE, airline);
	}

	@RequestMapping(value = "/city/add", method = RequestMethod.POST)
	public void addCity(String city) throws Exception {
		refdataService.addRFV(Constants.RF_CITY, city);
	}

	@RequestMapping(value = "/city/delete", method = RequestMethod.DELETE)
	public void deleteCity(
			@RequestParam(value = "city", required = true) String city) throws Exception {
		refdataService.deleteRFV(Constants.RF_CITY, city);
	}
}
