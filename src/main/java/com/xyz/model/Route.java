package com.xyz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Route {
	private int routeId;
	private ReferenceFieldValue fromCity;
	private ReferenceFieldValue toCity;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + routeId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Route other = (Route) obj;
		if (routeId != other.routeId)
			return false;
		return true;
	}

	@JsonIgnore
	public int getRouteId() {
		return routeId;
	}
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}
	public ReferenceFieldValue getFromCity() {
		return fromCity;
	}
	public void setFromCity(ReferenceFieldValue fromCity) {
		this.fromCity = fromCity;
	}
	public ReferenceFieldValue getToCity() {
		return toCity;
	}
	public void setToCity(ReferenceFieldValue toCity) {
		this.toCity = toCity;
	}
}
