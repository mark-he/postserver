package com.eagletsoft.post.core.process.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.eagletsoft.post.core.service.SendOutService;

public class RenewWorker extends QuartzJobBean {
	private static Logger LOG = LoggerFactory.getLogger(RenewWorker.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		try
		{
			ApplicationContext appContext = (ApplicationContext)context.getScheduler().getContext().get("applicationContextKey");
			SendOutService sendService =  appContext.getBean(SendOutService.class);
			int n = sendService.renewTimeout();
			LOG.warn("Renewed: " + n);
		}
		catch(Exception ex)
		{
			LOG.error("Error happened: ", ex);
			throw new JobExecutionException(ex);
		}
	}
}
