package com.eagletsoft.post.core.process;

import com.eagletsoft.post.core.convert.IConvertor;
import com.eagletsoft.post.core.route.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eagletsoft.post.core.data.PostTask;
import com.eagletsoft.post.core.data.SendOut;
import com.eagletsoft.post.core.sendout.SendOutException;
import com.eagletsoft.post.core.sendout.SendOutMessage;
import com.eagletsoft.post.core.service.PostDelegate;
import com.eagletsoft.post.core.template.Template;

import java.util.List;

public class SendExecutor implements Runnable {

	private static Logger LOG = LoggerFactory.getLogger(SendExecutor.class);

	public final static class ERROR {
		public final static int EMPTY = 0;
		public final static int UNKNOWN = 1;
		public final static int SENDOUT = 2;
		
	}
	
	private PostDelegate service;
	private IExecutionListener listener;
	
	public SendExecutor(PostDelegate service, IExecutionListener listener)
	{
		this.service = service;
		this.listener = listener;
	}
	
	@Override
	public void run() {
		SendOut rec = service.findPending();
		
		if (null == rec)
		{
			listener.onExecutionError(ERROR.EMPTY, "No more pendding was found.");
		}
		else
		{
			LOG.warn("Start to process send one message");
			try
			{
				String channelName = rec.getChannel();
				PostTask task = service.findTask(rec.getTaskId());
				Template template = null;
				if (!StringUtils.isEmpty(rec.getTemplate()))
				{
					template = service.findTemplate(rec.getTemplate(), channelName);
				}

				//sm 的reveiver需要由userId -> deviceId
				Channel channel = service.findChannel(channelName);
				if (null != channel.getConvertor()) {
					IConvertor convertor = service.findConvertor(channel.getConvertor());
					if (null == convertor) {
						throw new SendOutException("No convertor","Can not find convertor: " + channel.getConvertor() );
					}
					List<String> deviceIds  = convertor.convert(rec.getReceiver());
					for (String deviceId : deviceIds) {
						SendOutMessage sm = new MessageBuilder().setPayload(task.getPayload()).setSending(rec).setTemplate(template).build();
						sm.setReceiver(deviceId);
						service.sendOut(sm);
					}
				} else {
					SendOutMessage sm = new MessageBuilder().setPayload(task.getPayload()).setSending(rec).setTemplate(template).build();
					service.sendOut(sm);
				}

				listener.onExecutionSucceeded(rec.getId());
			}
			catch (SendOutException ex)
			{
				listener.onExecutionFailed(rec.getId(), ERROR.SENDOUT, ex.getMessage());
			}
			catch(Exception ex)
			{
				LOG.error("Error", ex);
				listener.onExecutionFailed(rec.getId(), ERROR.UNKNOWN, ex.getMessage());
			}
		}
	}
	
	public interface IExecutionListener
	{
		void onExecutionFailed(String trackId, int errorCode, String message);
		void onExecutionSucceeded(String trackId);
		void onExecutionError(int errorCode, String message);
	}
}
