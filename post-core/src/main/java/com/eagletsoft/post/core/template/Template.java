package com.eagletsoft.post.core.template;

import java.util.HashMap;
import java.util.Map;

public class Template {
	private String name;
	private Map<String, String> props = new HashMap<>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, String> getProps() {
		return props;
	}
	public void setProps(Map<String, String> props) {
		this.props = props;
	}
}
