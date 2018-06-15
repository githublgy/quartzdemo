package com.example.demo.quartz.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by lgy on 2018/6/11.
 */
@Entity
@DynamicInsert
@DynamicUpdate
public class TimedTask {
    /**
     * 任务No
     */
    private String jobNo;
    /**
     * 任务状态
     */
    private TaskStatus status;
    /**
     * 任务描述
     */
    private String taskDesc;
    /**
     * 运行类
     */
    private String runClassName;
    /**
     * 运行时间表达式
     */
    private String cron;
    /**
     * 任务名
     */
    private String taskName;
    /**
     * 任务组名
     */
    private String taskGroup;

    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解/生成32位UUID
    @GeneratedValue(generator="idGenerator")
    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    @Enumerated(EnumType.STRING)
    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getRunClassName() {
        return runClassName;
    }

    public void setRunClassName(String runClassName) {
        this.runClassName = runClassName;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskGroup() {
        return taskGroup;
    }

    public void setTaskGroup(String taskGroup) {
        this.taskGroup = taskGroup;
    }
}
