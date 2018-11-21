package com.best.entity;

import java.io.Serializable;

public class Result implements Serializable {
    private boolean success;
    private String message;

    public Result(boolean success, String message) {
        super();
        this.message = message;
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
