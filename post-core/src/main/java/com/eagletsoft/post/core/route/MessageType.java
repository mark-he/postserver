package com.eagletsoft.post.core.route;

import java.util.ArrayList;
import java.util.List;

public class MessageType {
	private String name;
	private String arrival;
	private List<Route> routes = new ArrayList<>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArrival() {
		return arrival;
	}
	public void setArrival(String arrival) {
		this.arrival = arrival;
	}
	public List<Route> getRoutes() {
		return routes;
	}
	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}
}
