package com.eagletsoft.post.core.route.impl.raw;

import java.util.List;
import java.util.Map;

public class RawConfig {
	private List<Map<String, Object>> channels;
	private List<RawMessageType> messageTypes;
	private List<RawConvertor> convertors;

	public List<Map<String, Object>> getChannels() {
		return channels;
	}

	public void setChannels(List<Map<String, Object>> channels) {
		this.channels = channels;
	}

	public List<RawMessageType> getMessageTypes() {
		return messageTypes;
	}

	public void setMessageTypes(List<RawMessageType> messageTypes) {
		this.messageTypes = messageTypes;
	}

	public List<RawConvertor> getConvertors() {
		return convertors;
	}

	public void setConvertors(List<RawConvertor> convertors) {
		this.convertors = convertors;
	}

	public static class RawMessageType {
		private String name;
		private String arrival;
		private List<Map<String, Object>> routes;
		
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
		public List<Map<String, Object>> getRoutes() {
			return routes;
		}
		public void setRoutes(List<Map<String, Object>> routes) {
			this.routes = routes;
		}
	}

	public static class RawConvertor {

		private String name;
//		private List<Map<String, Object>> props;
		private String driver;
		private String url;
		private String userName;
		private String password;
		private String maxPoolSize;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDriver() {
			return driver;
		}

		public void setDriver(String driver) {
			this.driver = driver;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getMaxPoolSize() {
			return maxPoolSize;
		}

		public void setMaxPoolSize(String maxPoolSize) {
			this.maxPoolSize = maxPoolSize;
		}
	}
}
