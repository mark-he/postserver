package com.eagletsoft.post.core.service;

import com.eagletsoft.post.core.convert.ConvertManager;
import com.eagletsoft.post.core.convert.IConvertor;
import com.eagletsoft.post.core.route.Channel;
import com.eagletsoft.post.core.route.impl.Router;
import com.eagletsoft.post.core.service.bo.AckRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eagletsoft.post.core.data.Payload;
import com.eagletsoft.post.core.data.PostTask;
import com.eagletsoft.post.core.data.SendOut;
import com.eagletsoft.post.core.data.StatusReport;
import com.eagletsoft.post.core.data.repo.PostTaskRepo;
import com.eagletsoft.post.core.sendout.ISendOutManager;
import com.eagletsoft.post.core.sendout.SendOutException;
import com.eagletsoft.post.core.sendout.SendOutMessage;
import com.eagletsoft.post.core.template.Template;
import com.eagletsoft.post.core.template.TemplateManager;

@Component
public class PostDelegate {
	@Autowired
	private PostService postService;
	@Autowired
	private SendOutService sendOutService;
	@Autowired
	private PostTaskRepo taskRepo;
	@Autowired
	private TemplateManager templateManager;
	@Autowired
	private ISendOutManager sendOutManager;
	@Autowired
	private ConvertManager convertorManager;
	@Autowired
	private Router router;
	
	public String post(Payload payload) {
		return postService.post(payload);
	}
	public StatusReport advice(String id) {
		return postService.advice(id);
	}
	public void ack(AckRequest request) {
		postService.ack(request);
	}
	public SendOut findPending() {
		return sendOutService.findPending();
	}
	public int renewTimeout() {
		return sendOutService.renewTimeout();
	}
	public void cleanupOverdue() {
		sendOutService.cleanupOverdue();
	}
	
	public PostTask findTask(String id)
	{
		return taskRepo.findById(id).get();
	}
	
	public Template findTemplate(String name, String channel)
	{
		return templateManager.findTemplate(name, channel);
	}
	
	public void sendOut(SendOutMessage sm) throws SendOutException
	{
		sendOutManager.send(sm);
	}
	
	public void updateSendOutResult(String id, boolean isError, String description)
	{
		if (isError)
		{
			sendOutService.updateResult(id, SendOut.STATE.ERROR, description);
		}
		else
		{
			sendOutService.updateResult(id, SendOut.STATE.SENT, description);
		}
	}

	public Channel findChannel(String channelName) {
		Channel channel = router.findChannel(channelName);
		return channel;
	}

	public IConvertor findConvertor(String convertorName) {
		IConvertor convertor = convertorManager.findConvertor(convertorName);
		return convertor;
	}

	/*public List<String> convert(String channelName, String receiver) {
		Channel channel = router.findChannel(channelName);
		List<String> deviceIds = convertorManager.convert(channel.getConvertor(), receiver);
		return deviceIds;
	}*/
}
