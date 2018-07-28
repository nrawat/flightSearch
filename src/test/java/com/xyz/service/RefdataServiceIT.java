package com.xyz.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xyz.common.Constants;

public class RefdataServiceIT extends BaseServiceTest {
	@Autowired
	private RefdataService refdataService;

	@Test
	public void testGetAirlines() {
		Assert.assertTrue(refdataService.getAirlines().contains("Indigo"));
		Assert.assertFalse(refdataService.getAirlines().contains("Test12"));
	}

	@Test
	public void testGetCities() {
		Assert.assertTrue(refdataService.getCities().contains("Hyderabad"));
		Assert.assertFalse(refdataService.getCities().contains("Test34"));
	}

	@Test
	public void testAddRFV() throws Exception {
		Assert.assertFalse(refdataService.getAirlines().contains("Test12"));
		refdataService.addRFV(Constants.RF_AIRLINE, "Test12");
		Assert.assertTrue(refdataService.getAirlines().contains("Test12"));

		Assert.assertFalse(refdataService.getCities().contains("Test34"));
		refdataService.addRFV(Constants.RF_CITY, "Test34");
		Assert.assertTrue(refdataService.getCities().contains("Test34"));
	}

	@Test
	public void testDeleteRFV() throws Exception {
		Assert.assertFalse(refdataService.getAirlines().contains("Test12"));
		refdataService.addRFV(Constants.RF_AIRLINE, "Test12");
		Assert.assertTrue(refdataService.getAirlines().contains("Test12"));
		refdataService.deleteRFV(Constants.RF_AIRLINE, "Test12");
		Assert.assertFalse(refdataService.getAirlines().contains("Test12"));

		Assert.assertFalse(refdataService.getCities().contains("Test34"));
		refdataService.addRFV(Constants.RF_CITY, "Test34");
		Assert.assertTrue(refdataService.getCities().contains("Test34"));
		refdataService.deleteRFV(Constants.RF_CITY, "Test34");
		Assert.assertFalse(refdataService.getCities().contains("Test34"));
	}
}
