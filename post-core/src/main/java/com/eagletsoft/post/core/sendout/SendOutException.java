package com.eagletsoft.post.core.sendout;

public class SendOutException extends RuntimeException {
	private String key;

	public SendOutException(String key, String message) {
		super(message);
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
