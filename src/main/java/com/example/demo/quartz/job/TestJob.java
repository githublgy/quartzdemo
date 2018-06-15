package com.example.demo.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by lgy on 2018/6/11.
 */
public class TestJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        startJob();
    }
    public void startJob() {
        System.out.println("开始 testjob");
    }
}
