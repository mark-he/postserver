package com.eagletsoft.post.core.data;

import java.util.ArrayList;
import java.util.List;

public class StatusReport {
	private String id;
	private List<ChannelSendState> receivers = new ArrayList<>();
	
	public StatusReport(String id)
	{
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ChannelSendState> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<ChannelSendState> receivers) {
		this.receivers = receivers;
	}

	public void addState(String receiver, String channel, String state)
	{
		receivers.add(new ChannelSendState(receiver, channel, state));
	}
	
	public class ChannelSendState {
		private String receiver;
		private String channel;
		private String state;
		
		public ChannelSendState(String receiver, String channel, String state) {
			super();
			this.receiver = receiver;
			this.channel = channel;
			this.state = state;
		}
		public String getChannel() {
			return channel;
		}
		public void setChannel(String channel) {
			this.channel = channel;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((channel == null) ? 0 : channel.hashCode());
			result = prime * result + ((receiver == null) ? 0 : receiver.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ChannelSendState other = (ChannelSendState) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (channel == null) {
				if (other.channel != null)
					return false;
			} else if (!channel.equals(other.channel))
				return false;
			if (receiver == null) {
				if (other.receiver != null)
					return false;
			} else if (!receiver.equals(other.receiver))
				return false;
			return true;
		}
		private StatusReport getOuterType() {
			return StatusReport.this;
		}
	}
}
