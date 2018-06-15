package com.example.demo.quartz.runner;

import com.example.demo.quartz.service.ScheduleService;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by lgy on 2018/6/11.
 */
@Component
public class TimedTaskInitRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimedTaskInitRunner.class);

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(String... strings) throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>>>>>init timedtask<<<<<<<<<<<<<<<<<<<<<<");
        scheduleService.startAllJob();
        LOGGER.info(">>>>>>>>>>>>>>>>>>>.end timedtask<<<<<<<<<<<<<<<<<<<<<<<");
    }
}
