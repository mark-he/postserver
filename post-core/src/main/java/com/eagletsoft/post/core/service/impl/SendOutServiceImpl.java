package com.eagletsoft.post.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eagletsoft.post.core.data.SendOut;
import com.eagletsoft.post.core.data.repo.SendOutRepo;
import com.eagletsoft.post.core.service.SendOutService;

@Service
public class SendOutServiceImpl implements SendOutService {
	
	@Autowired
	private SendOutRepo repo;
	
	@Override
	public SendOut findPending() {
		return repo.findPending();
	}

	@Override
	public int  renewTimeout() {
		int i = repo.renewTimeoutForLastTry();
		i += repo.renewTimeoutForNormalTry();
		return i;
	}

	@Override
	public void cleanupOverdue() {

	}

	@Override
	public void updateResult(String id, String state, String description) {
		repo.updateResult(id, state, description);
	}

	@Override
	public List<SendOut> findByTaskId(String taskId) {
		return repo.findByTaskId(taskId);
	}

	@Override
	public List<SendOut> findByReceiver(String channel, String recevier, int page, int size) {
		Long skip = Long.valueOf(page * size);
		return repo.findByReceiver(channel, recevier, skip, size);
	}

	@Override
	public List listMsgByPage(String channel, String receiver, int page, int size) {
		//TODO
		return null;
	}

	@Override
	public int countUnreadMsg(String channel, String reveiver, String sender) {
		int count = repo.countUnreadMsgByCondition(channel, reveiver, sender);
		return count;
	}
}
