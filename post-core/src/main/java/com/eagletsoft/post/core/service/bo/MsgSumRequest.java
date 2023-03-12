package com.eagletsoft.post.core.service.bo;

import java.io.Serializable;

public class MsgSumRequest implements Serializable {

    private String channel;

    private String receiver;

    private String sender;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
