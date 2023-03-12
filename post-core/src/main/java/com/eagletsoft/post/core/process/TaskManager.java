package com.eagletsoft.post.core.process;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.eagletsoft.post.core.service.PostDelegate;

public class TaskManager implements SendExecutor.IExecutionListener {
	private static Logger LOG = LoggerFactory.getLogger(TaskManager.class);
	@Autowired
	private PostDelegate serviceDelegate;
	
	private ExecutorService pool;
	
	public TaskManager(int threadCount)
	{
		 pool = Executors.newFixedThreadPool(threadCount);
	}

	public void process()
	{
		addNewTask();
	}
	
	private void addNewTask()
	{
		pool.execute(new SendExecutor(serviceDelegate, this));
	}
	
	@Override
	public void onExecutionFailed(String trackId, int errorCode, String message) {
		serviceDelegate.updateSendOutResult(trackId, true, message);
		addNewTask();
	}

	@Override
	public void onExecutionSucceeded(String trackId) {
		serviceDelegate.updateSendOutResult(trackId, false, "Sent.");
		addNewTask();
	}

	@Override
	public void onExecutionError(int errorCode, String message) {
		LOG.warn("Sendout code: " + errorCode + " message: " + message);
	}
}
