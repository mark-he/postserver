package com.eagletsoft.post.core.route;

import java.util.Map;

public class Channel {
	private Map<String, Object> props;
	
	public String getName() {
		return (String)props.get("name");
	}
	public String getType() {
		return (String)props.get("type");
	}
	public String getConvertor() {
		return (String)props.get("convertor");
	}
	public Map<String, Object> getProps() {
		return props;
	}
	public void setProps(Map<String, Object> props) {
		this.props = props;
	}
}
