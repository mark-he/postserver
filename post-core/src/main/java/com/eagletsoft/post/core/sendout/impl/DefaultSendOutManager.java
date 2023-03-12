package com.eagletsoft.post.core.sendout.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.eagletsoft.post.core.sendout.ISendOutManager;
import com.eagletsoft.post.core.sendout.SendOutException;
import com.eagletsoft.post.core.sendout.SendOutMessage;
import com.eagletsoft.post.core.sendout.connector.IConnector;

@Component
public class DefaultSendOutManager implements ISendOutManager {
	private Map<String, IConnector> connectors = new HashMap<>();

	@Override
	public void register(IConnector connector) {
		connectors.put(connector.getName(), connector);
	}

	@Override
	public void send(SendOutMessage sm) throws SendOutException {
		IConnector connector = connectors.get(sm.getChannel());
		if (null != connector) {
			if (connector.isReady()) {
				connector.send(sm);
			} else {
				throw new SendOutException("Connector Not Ready", "Connector Not Ready: " + sm.getChannel());
			}
		} else {
			throw new SendOutException("No Connector", "No Connector for Channel: " + sm.getChannel());
		}
	}
}
