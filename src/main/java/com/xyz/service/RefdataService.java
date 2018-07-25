package com.xyz.service;

import java.util.List;

public interface RefdataService {
	List<String> getAirlines();
	List<String> getCities();

	void addRFV(String fieldName, String value) throws Exception;
	void deleteRFV(String fieldName, String value) throws Exception;
}
