package com.eagletsoft.post.core.service.bo;

import javax.validation.constraints.NotBlank;

public class AckRequest {
    @NotBlank
    private String id;
    @NotBlank
    private String state;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
