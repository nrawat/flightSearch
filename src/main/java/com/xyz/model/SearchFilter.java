package com.xyz.model;

import java.util.Date;

public class SearchFilter {
	private Integer airline;
	private Integer fromCity;
	private Integer toCity;
	private Date departureDate;
	private Date returnDate;
	private Double price;

	public Integer getAirline() {
		return airline;
	}
	public void setAirLine(Integer airline) {
		this.airline = airline;
	}
	public Integer getFromCity() {
		return fromCity;
	}
	public void setFromCity(Integer fromCity) {
		this.fromCity = fromCity;
	}
	public Integer getToCity() {
		return toCity;
	}
	public void setToCity(Integer toCity) {
		this.toCity = toCity;
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
}
