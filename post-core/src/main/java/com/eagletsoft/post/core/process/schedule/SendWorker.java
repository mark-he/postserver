package com.eagletsoft.post.core.process.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.eagletsoft.post.core.process.TaskManager;

public class SendWorker extends QuartzJobBean {
	private static Logger LOG = LoggerFactory.getLogger(SendWorker.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try
		{
			ApplicationContext appContext = (ApplicationContext)context.getScheduler().getContext().get("applicationContextKey");
			TaskManager processManager =  (TaskManager)appContext.getBean(TaskManager.class);
			processManager.process();
		}
		catch(Exception ex)
		{
			LOG.error("Error happened: ", ex);
			throw new JobExecutionException(ex);
		}
	}
}
