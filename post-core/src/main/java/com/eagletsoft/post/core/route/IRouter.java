package com.eagletsoft.post.core.route;

import java.util.Collection;

public interface IRouter {
	MessageType findMessageType(String name);
	Channel findChannel(String name);
	Collection<Channel> getChannels();
}