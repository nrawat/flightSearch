package com.xyz.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.xyz.common.Common;
import com.xyz.model.Flight;
import com.xyz.model.Route;
import com.xyz.model.Commute;

public class TravelDAOImpl implements TravelDAO {
	private SqlSession sqlSession;

	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<Flight> getFlights() {
		return sqlSession.selectList("Commute.selectFlights", null);
	}

	@Override
	public List<Route> getRoutes() {
		return sqlSession.selectList("Commute.selectRoutes", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, List<Commute>> getCityCommuteMap(Date searchDate, Integer airline) {
		Map<Object, Object> paramMap = new HashMap<>();
		paramMap.put("searchDate", searchDate);
		paramMap.put("airline", airline);

		return Common.convertSelectMap(sqlSession.selectMap("Commute.selectCityCommutes", paramMap, "key"), "value");
	}

	@Override
	public void addFlight(Flight flight) {
		sqlSession.insert("Commute.insertFlight", flight);
	}

	@Override
	public void deleteFlight(String flightId) {
		sqlSession.insert("Commute.deleteFlight", flightId);
	}

	@Override
	public void addRoute(Route route) {
		sqlSession.insert("Commute.insertRoute", route);
	}

	@Override
	public void deleteRoute(int routeId) {
		sqlSession.insert("Commute.deleteRoute", routeId);
	}
}
