package com.eagletsoft.post.core.sendout;

import com.eagletsoft.post.core.sendout.connector.IConnector;

public interface ISendOutManager {
	void register(IConnector connector);

	void send(SendOutMessage sm) throws SendOutException;
}
