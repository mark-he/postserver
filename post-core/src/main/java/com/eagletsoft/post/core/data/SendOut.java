package com.eagletsoft.post.core.data;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class SendOut {
	public static class SETTING {
		public static int RETRY_MAX = 3;
		public static int TIMEOUT_SECS = 30;
		public static int LAST_TRY_SECS = 60;
	}
	
	public static class STATE {
		public static String PENDING = "PENDING";
		public static String PROCESSING = "PROCESSING";
		public static String ERROR = "ERROR";
		public static String EXTERNAL_ERROR = "EXTERNAL_ERROR";
		
		public static String SENT = "SENT";
		public static String FAILED = "FAILED";
		public static String READ = "READ";
	}
	
	public static class History {
		private String state;
		private String description;
		private Date createdTime;
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public Date getCreatedTime() {
			return createdTime;
		}
		public void setCreatedTime(Date createdTime) {
			this.createdTime = createdTime;
		}
	}
	
	@Id
	private String id;
	@Indexed
	private String taskId;
	private String template;
	private String channel;
	@Indexed
	private String sender;
	@Indexed
	private String receiver;
	@Indexed
	private Date scheduledTime;
	@Indexed
	private Date processedTime;
	@Indexed
	private String state;
	private int retry;
	private String[] histories;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
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
	public int getRetry() {
		return retry;
	}
	public void setRetry(int retry) {
		this.retry = retry;
	}
	public String[] getHistories() {
		return histories;
	}
	public void setHistories(String[] histories) {
		this.histories = histories;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	
}
