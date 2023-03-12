package com.eagletsoft.post.core.sendout.connector.impl;

import com.eagletsoft.post.core.sendout.ISendOutManager;
import com.eagletsoft.post.core.sendout.connector.IConnector;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public abstract class AbstractConnector implements IConnector {

    @Autowired
    private ISendOutManager sendOutManager;

    @PostConstruct
    public void init() {
        sendOutManager.register(this);
    }

    @Override
    public boolean isReady() {
        return true;
    }

}
