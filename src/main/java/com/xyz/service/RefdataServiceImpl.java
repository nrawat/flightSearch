package com.xyz.service;

import java.util.List;

import com.xyz.common.Common;
import com.xyz.common.Constants;
import com.xyz.dao.RefdataDAO;
import com.xyz.model.ReferenceFieldValue;

public class RefdataServiceImpl implements RefdataService {
	private ApplicationCache applicationCache;
	private RefdataDAO refdataDAO;

	public ApplicationCache getApplicationCache() {
		return applicationCache;
	}

	public void setApplicationCache(ApplicationCache applicationCache) {
		this.applicationCache = applicationCache;
	}

	public RefdataDAO getRefdataDAO() {
		return refdataDAO;
	}

	public void setRefdataDAO(RefdataDAO refdataDAO) {
		this.refdataDAO = refdataDAO;
	}

	@Override
	public List<String> getAirlines() {
		return applicationCache.getRFVListFromFieldName(Constants.RF_AIRLINE);
	}

	@Override
	public List<String> getCities() {
		return applicationCache.getRFVListFromFieldName(Constants.RF_CITY);
	}

	@Override
	public void addRFV(String fieldName, String value) throws Exception {
		value = Common.toString(value);
		if(value.isEmpty()) {
			throw new Exception(fieldName + " cannot be blank/null");
		}

		if(applicationCache.getRFVId(fieldName, value) != null) {
			throw new Exception(value + " is already present in " + fieldName);
		}
		
		ReferenceFieldValue rfv = new ReferenceFieldValue();
		rfv.setFieldValueName(value);
		rfv.setReferenceField(applicationCache.getRefFieldFromFieldName(fieldName));

		refdataDAO.addRFV(rfv);
		applicationCache.refreshRefdataMap();
	}

	@Override
	public void deleteRFV(String fieldName, String value) throws Exception {
		value = Common.toString(value);
		if(value.isEmpty()) {
			throw new Exception(fieldName + " cannot be blank/null");
		}
		
		ReferenceFieldValue rfv = applicationCache.getRFV(fieldName, value);
		if(rfv == null) {
			throw new Exception(value + " is not present in " + fieldName);
		}

		refdataDAO.deleteRFV(rfv.getFieldValueId());
		applicationCache.refreshRefdataMap();
	}
}
