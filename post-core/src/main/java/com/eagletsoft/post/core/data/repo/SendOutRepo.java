package com.eagletsoft.post.core.data.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eagletsoft.post.core.data.SendOut;

public interface SendOutRepo extends MongoRepository<SendOut, String> {
	SendOut findPending();
	int renewTimeoutForNormalTry();
	int renewTimeoutForLastTry();
	void updateResult(String id, String state, String description);
	void updateResultByCondition(String channel,String sender, String receiver, String state, String description);
	List<SendOut> findByTaskId(String taskId);
	boolean saveUnique(SendOut rec);
	List<SendOut> findByReceiver(String channel, String receiver,long skip, int limit);
	int countUnreadMsgByCondition(String channel, String receiver, String sender);
}
