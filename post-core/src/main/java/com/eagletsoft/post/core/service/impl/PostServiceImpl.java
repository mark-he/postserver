package com.eagletsoft.post.core.service.impl;

import java.util.List;

import com.eagletsoft.post.core.convert.ConvertManager;
import com.eagletsoft.post.core.service.bo.AckConditionRequest;
import com.eagletsoft.post.core.service.bo.AckRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eagletsoft.post.core.data.Payload;
import com.eagletsoft.post.core.data.PostTask;
import com.eagletsoft.post.core.data.SendOut;
import com.eagletsoft.post.core.data.StatusReport;
import com.eagletsoft.post.core.data.repo.SendOutRepo;
import com.eagletsoft.post.core.route.IRouter;
import com.eagletsoft.post.core.route.MessageType;
import com.eagletsoft.post.core.route.Route;
import com.eagletsoft.post.core.service.PostService;
import com.eagletsoft.post.core.service.PostTaskService;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostTaskService postTaskService;
	@Autowired
	private SendOutRepo sendOutRepo;
	@Autowired
	private IRouter router;
	
	@Override
	public String post(Payload payload) {
		PostTask task = postTaskService.createTaskIfNotPresent(payload);
		MessageType mtype = router.findMessageType(task.getPayload().getType());
		split(task, mtype);
		return task.getToken();
	}

	@Override
	public StatusReport advice(String id) {
		List<SendOut> list = sendOutRepo.findByTaskId(id);
		StatusReport report = new StatusReport(id);
		for (SendOut rec : list)
		{
			report.addState(rec.getReceiver(), rec.getChannel(), rec.getState());
		}
		return report;
	}

	@Override
	public void ack(AckRequest request) {
		sendOutRepo.updateResult(request.getId(), request.getState(), request.getDescription());
	}

	@Override
	public void ackByCondition(AckConditionRequest request) {
		sendOutRepo.updateResultByCondition(request.getChannel(),request.getSender(), request.getReceiver(), request.getState(), request.getDescription());
	}

	private void split(PostTask task, MessageType mtype)
	{
		if (task.getPayload().getReceivers().length > 0)
		{
			for (String receiver: task.getPayload().getReceivers())
			{
				for (Route route : mtype.getRoutes())
				{
					SendOut rec = new SendOut();

					rec.setChannel(route.getChannel());
					rec.setSender(task.getPayload().getSender());
					rec.setReceiver(receiver);
					rec.setTaskId(task.getId());
					rec.setScheduledTime(task.getScheduledTime());
					rec.setState(SendOut.STATE.PENDING);
					rec.setTemplate(route.getTemplate());

					sendOutRepo.saveUnique(rec);
				}
			}
		}
	}

}
