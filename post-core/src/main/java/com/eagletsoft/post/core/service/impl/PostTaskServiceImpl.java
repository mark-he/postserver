package com.eagletsoft.post.core.service.impl;

import java.util.Date;

import com.eagletsoft.boot.framework.common.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eagletsoft.post.core.data.Payload;
import com.eagletsoft.post.core.data.PostTask;
import com.eagletsoft.post.core.data.repo.PostTaskRepo;
import com.eagletsoft.post.core.service.PostTaskService;

@Service
public class PostTaskServiceImpl implements PostTaskService {
	@Autowired
	private PostTaskRepo repo;
	
	@Override
	public PostTask createTaskIfNotPresent(Payload payload) {
		PostTask task = buildTask(payload);
		task = repo.saveUnique(task);
		return task;
	}
	
	@Override
	public PostTask find(String id) {
		return repo.findById(id).get();
	}
	
	private PostTask buildTask(Payload payload) {
		PostTask task = new PostTask();
		
		task.setPayload(payload);
		task.setCreatedTime(new Date());
		task.setScheduledTime(payload.getScheduledTime());
		if (null == task.getScheduledTime()) {
			task.setScheduledTime(new Date());
		}
		
		task.setToken(UuidUtils.getUUID());
		task.setUniqueKey(payload.genernateUniqueKey());
		task.setCount(payload.getReceivers().length);
	
		return task;
	}
	
}
