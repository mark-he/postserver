package com.eagletsoft.post;

import com.eagletsoft.boot.framework.common.utils.ApplicationUtils;
import com.eagletsoft.post.core.process.TaskManager;
import com.eagletsoft.post.core.service.SendOutService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduleConfig {

	@Scheduled(cron = "30 * * * * ?")
	public void renew() {
		SendOutService sendService =  ApplicationUtils.getBean(SendOutService.class);
		int n = sendService.renewTimeout();

		System.out.println("Renew tasks: " + n);
	}

	@Scheduled(cron = "0/10 * * * * ?")
	public void processTask() {
		TaskManager taskManager =  (TaskManager)ApplicationUtils.getBean(TaskManager.class);
		taskManager.process();
	}
}
