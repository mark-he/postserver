package com.eagletsoft.post.core.service;

import com.eagletsoft.post.core.data.Payload;
import com.eagletsoft.post.core.data.StatusReport;
import com.eagletsoft.post.core.service.bo.AckConditionRequest;
import com.eagletsoft.post.core.service.bo.AckRequest;

public interface PostService {
	
	/**
	 * Try to add envelope into a sending queue
	 * @param envelope
	 * @return
	 */
	String post(Payload envelope);
	
	/**
	 * Ask for sending status
	 * @param id
	 * @return
	 */
	StatusReport advice(String id);
	
	/**
	 * Feeds back the sending status
	 * @param request
	 * @return
	 */
	void ack(AckRequest request);

	/**
	 * feeds back sending status conditionally
	 */
	void ackByCondition(AckConditionRequest request);
}
