package com.eagletsoft.post.core.data;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.eagletsoft.boot.framework.common.utils.JsonUtils;
import com.eagletsoft.boot.framework.common.utils.PasswordUtils;

public class Payload {
	private String identity;
	private String source;

	private String sender;
	private String[] receivers;

	private Map<String, Object> content;
	private Date scheduledTime;
	private String type;

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String[] getReceivers() {
		return receivers;
	}

	public void setReceivers(String[] receivers) {
		this.receivers = receivers;
	}

	public Map<String, Object> getContent() {
		return content;
	}

	public void setContent(Map<String, Object> content) {
		this.content = content;
	}

	public Date getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(Date scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String genernateUniqueKey() {
		if (StringUtils.isEmpty(identity)) {
			return "";
		} else {
			return PasswordUtils.encript(JsonUtils.writeValue(this));
		}
	}
}
