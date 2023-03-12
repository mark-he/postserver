package com.eagletsoft.post.core.route;

import java.util.Map;

public class Route {
	private Map<String, Object> props;
	
	public String getChannel() {
		return (String)props.get("channel");
	}

	public String getTemplate() {
		return (String)props.get("template");
	}

	public Map<String, Object> getProps() {
		return props;
	}

	public void setProps(Map<String, Object> props) {
		this.props = props;
	}
}
