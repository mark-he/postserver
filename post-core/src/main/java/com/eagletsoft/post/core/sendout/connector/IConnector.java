package com.eagletsoft.post.core.sendout.connector;

import com.eagletsoft.post.core.sendout.SendOutException;
import com.eagletsoft.post.core.sendout.SendOutMessage;

public interface IConnector {
	String getName();
	void send(SendOutMessage sm) ;
	boolean isReady();
}
