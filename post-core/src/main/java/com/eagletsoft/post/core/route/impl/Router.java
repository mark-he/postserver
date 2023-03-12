package com.eagletsoft.post.core.route.impl;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.eagletsoft.post.core.route.Channel;
import com.eagletsoft.post.core.route.IRouter;
import com.eagletsoft.post.core.route.MessageType;

@Component
public class Router implements IRouter {

	private RouteConfig config;

	public RouteConfig getConfig() {
		return config;
	}

	public void setConfig(RouteConfig config) {
		this.config = config;
	}

	@PostConstruct
	public void init() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lianjia.postcenter.route.IRouter#findMessageType(java.lang.String)
	 */
	@Override
	public MessageType findMessageType(String name) {
		return config.getTypeMap().get(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lianjia.postcenter.route.IRouter#findChannel(java.lang.String)
	 */
	@Override
	public Channel findChannel(String name) {
		return config.getChannelMap().get(name.toLowerCase());
	}

	@Override
	public Collection<Channel> getChannels() {
		return config.getChannelMap().values();
	}

}
