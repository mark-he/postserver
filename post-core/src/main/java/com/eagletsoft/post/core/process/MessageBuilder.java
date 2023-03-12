package com.eagletsoft.post.core.process;

import com.eagletsoft.post.core.data.Payload;
import com.eagletsoft.post.core.data.SendOut;
import com.eagletsoft.post.core.sendout.SendOutMessage;
import com.eagletsoft.post.core.template.Template;

public class MessageBuilder {
	private SendOutMessage message;
	
	public MessageBuilder()
	{
		this.message = new SendOutMessage();
	}
	
	public MessageBuilder setPayload(Payload payload)
	{
		message.setSender(payload.getSender());
		message.setContent(payload.getContent());
		
		return this;
	}
	
	public MessageBuilder setSending(SendOut rec)
	{
		message.setReceiver(rec.getReceiver());
		message.setTrackId(rec.getId());
		message.setChannel(rec.getChannel());
		return this;
	}

	public MessageBuilder setTemplate(Template template)
	{
		message.setTemplate(template);
		return this;
	}
	
	public SendOutMessage build()
	{
		return message;
	}
}
