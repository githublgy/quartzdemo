package com.example.demo.quartz.entity;

/**
 * Created by lgy on 2018/6/11.
 */
public enum TaskStatus {
    START("START"),
    STOP("STOP");

    private String status;


    TaskStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
