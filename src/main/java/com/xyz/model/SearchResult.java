package com.xyz.model;

import java.sql.Time;
import java.util.Comparator;
import java.util.List;

public class SearchResult {
	private Time duration;
	private Integer noOfStops;
	private Double price;
	private List<Commute> commutes;

	public Time getDuration() {
		return duration;
	}
	public void setDuration(Time duration) {
		this.duration = duration;
	}
	public Integer getNoOfStops() {
		return noOfStops;
	}
	public void setNoOfStops(Integer noOfStops) {
		this.noOfStops = noOfStops;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public List<Commute> getCommutes() {
		return commutes;
	}
	public void setCommutes(List<Commute> commutes) {
		this.commutes = commutes;
	}

	public static final Comparator<SearchResult> DEFAULT_SORT = Comparator.comparing(
			SearchResult::getNoOfStops).thenComparing(SearchResult::getPrice).thenComparing(SearchResult::getDuration);
}
