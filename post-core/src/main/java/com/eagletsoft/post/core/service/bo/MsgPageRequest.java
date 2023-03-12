package com.eagletsoft.post.core.service.bo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class MsgPageRequest implements Serializable {

    private String sender;

    @NotBlank
    private String channel;

    @NotBlank
    private String receiver;

    private int page;

    private int size;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
