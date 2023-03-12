package com.eagletsoft.post.core.service;

import java.util.List;

import com.eagletsoft.post.core.data.SendOut;

public interface SendOutService {
	SendOut findPending();
	int renewTimeout();
	void cleanupOverdue();
	void updateResult(String id, String state, String description);
	List<SendOut> findByTaskId(String taskId);
	List<SendOut> findByReceiver(String channel, String receiver, int page, int size);
	int countUnreadMsg(String channel, String reveiver, String sender);
	List listMsgByPage(String channel, String receiver, int page, int size);
}
