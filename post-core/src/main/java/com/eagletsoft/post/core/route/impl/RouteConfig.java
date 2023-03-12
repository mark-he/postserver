package com.eagletsoft.post.core.route.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eagletsoft.boot.framework.common.utils.JsonUtils;
import com.eagletsoft.post.core.route.Channel;
import com.eagletsoft.post.core.route.MessageType;
import com.eagletsoft.post.core.route.Route;
import com.eagletsoft.post.core.route.impl.raw.RawConfig;
import com.eagletsoft.post.core.route.impl.raw.RawConfig.RawMessageType;

public class RouteConfig {
	private static Logger LOG = LoggerFactory.getLogger(RouteConfig.class);

	private Map<String, Channel> channelMap = new HashMap<>();
	private Map<String, MessageType> typeMap = new HashMap<>();

	public Map<String, Channel> getChannelMap() {
		return channelMap;
	}

	public Map<String, MessageType> getTypeMap() {
		return typeMap;
	}

	public void init(InputStream inputStream) throws Exception {
		LOG.warn("Started to load route configuration.");
		loadFile(inputStream);
	}

	private void loadFile(InputStream inputStream) throws Exception {
		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			while ((line = bf.readLine()) != null) {
				stringBuilder.append(line);
			}
			String str = new String(stringBuilder.toString().getBytes("UTF-8"));
			RawConfig config = JsonUtils.createMapper().readValue(str, RawConfig.class);

			for (Map<String, Object> map : config.getChannels()) {
				Channel channel = new Channel();
				channel.setProps(map);
				channelMap.put(channel.getName(), channel);
			}

			for (RawMessageType type : config.getMessageTypes()) {
				MessageType messageType = new MessageType();

				messageType.setName(type.getName());
				messageType.setArrival(type.getArrival());
				typeMap.put(messageType.getName(), messageType);

				for (Map<String, Object> map : type.getRoutes()) {
					Route route = new Route();
					route.setProps(map);
					messageType.getRoutes().add(route);
				}
			}
			System.out.println("Config ===" + JsonUtils.writeValue(config));
		} catch (Exception e) {
			LOG.error("Error in reading route configuration!");
			throw e;
		}
	}
}
