package com.eagletsoft.post.core.data;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class PostTask {
	public static class STATE {
		public static String NEW = "NEW";
		public static String COMPLETED = "COMPLETED";
		public static String FAILED = "FAILED";
		public static String PAUSED = "PAUSED";
	}
	
	@Id
	private String id;

	private Payload payload;
	
	@Indexed
	private String token;
	private Date tokenExpiryTime;
	private Date createdTime;
	@Indexed
	private Date scheduledTime;
	@Indexed
	private Date processedTime;
	@Indexed
	private String state = STATE.NEW;
	@Indexed
	private String uniqueKey;
	
	private int count;
	private int sent;
	
	private int failure;
	private int success;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Payload getPayload() {
		return payload;
	}
	public void setPayload(Payload payload) {
		this.payload = payload;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getTokenExpiryTime() {
		return tokenExpiryTime;
	}
	public void setTokenExpiryTime(Date tokenExpiryTime) {
		this.tokenExpiryTime = tokenExpiryTime;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getScheduledTime() {
		return scheduledTime;
	}
	public void setScheduledTime(Date scheduledTime) {
		this.scheduledTime = scheduledTime;
	}
	public Date getProcessedTime() {
		return processedTime;
	}
	public void setProcessedTime(Date processedTime) {
		this.processedTime = processedTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUniqueKey() {
		return uniqueKey;
	}
	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getSent() {
		return sent;
	}
	public void setSent(int sent) {
		this.sent = sent;
	}
	public int getFailure() {
		return failure;
	}
	public void setFailure(int failure) {
		this.failure = failure;
	}
	public int getSuccess() {
		return success;
	}
	public void setSuccess(int success) {
		this.success = success;
	}
}
