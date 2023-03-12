package com.eagletsoft.post.core.service.bo;

import javax.validation.constraints.NotBlank;

public class AckConditionRequest {

    @NotBlank
    private String channel;

    @NotBlank
    private String sender;

    @NotBlank
    private String receiver;
    @NotBlank
    private String state;
    private String description;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
