package com.eagletsoft.post.core;

import com.eagletsoft.post.core.convert.ConvertManager;
import com.eagletsoft.post.core.convert.ConvertConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.eagletsoft.post.core.process.TaskManager;
import com.eagletsoft.post.core.route.IRouter;
import com.eagletsoft.post.core.route.impl.RouteConfig;
import com.eagletsoft.post.core.route.impl.Router;
import com.eagletsoft.post.core.template.TemplateManager;

@Configuration
public class PostConfig {

	@Value("${process.threadCount}")
	private int threadCount;

//	@Value("${converts.config}")
//	private String convertsConfigPath;

	@Value("${spring.profiles.active}")
	private String profile;

	@Bean
	public IRouter router() throws Exception {
		Router router = new Router();
		RouteConfig routeConfig = new RouteConfig();
		ClassPathResource resource = new ClassPathResource("templates/routes.json");
		routeConfig.init(resource.getInputStream());
		router.setConfig(routeConfig);
		return router;
	}

	@Bean
	public TemplateManager templateManager() throws Exception {
		TemplateManager templateManager = new TemplateManager();
		Resource[] resources = new PathMatchingResourcePatternResolver().getResources("templates/*.xml");
		templateManager.setResources(resources);
		return templateManager;
	}

	@Bean
	public TaskManager taskManager() {
		TaskManager taskManager = new TaskManager(threadCount);
		return taskManager;
	}

	@Bean
	public ConvertManager convertorManager() throws Exception{
		ConvertManager convertorManager = new ConvertManager();
		ClassPathResource resource = new ClassPathResource("templates/converts_" + profile +".json");
		System.out.println("+++++++++++++++++++++++++++++++" + resource.getPath());
		convertorManager.init(ConvertConfig.loadConfig(resource));
		return convertorManager;
	}
}
