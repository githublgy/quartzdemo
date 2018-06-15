package com.example.demo.quartz.controller;


import com.example.demo.quartz.entity.TimedTask;
import com.example.demo.quartz.service.ScheduleService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lgy on 2018/6/11.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;


    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object createTimedTask(@RequestBody TimedTask timedTask) {
        return scheduleService.createTimedTask(timedTask);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object queryTimedTasks() {
        return scheduleService.queryAllTimedTask();
    }


    @RequestMapping(value = "{jobNo}", method = RequestMethod.POST)
    public Object updateTimedTask(@PathVariable("jobNo") String jobNo, TimedTask timedTask) {
        timedTask.setJobNo(jobNo);
        return scheduleService.updateTimedTask(timedTask);
    }


    @RequestMapping(value = "/start/{jobNo}", method = RequestMethod.GET)
    public Object startTimedTask(@PathVariable("jobNo") final String jobNo) {
        boolean flag = true;
        try {
            scheduleService.resumeJob(jobNo);
        } catch (SchedulerException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @RequestMapping(value = "/stop/{jobNo}", method = RequestMethod.GET)
    public Object stopTimedTask(@PathVariable("jobNo") final String jobNo) {
        boolean flag = true;
        try {
            scheduleService.pauseJob(jobNo);
        } catch (SchedulerException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @RequestMapping(value = "/directstart/{jobNo}", method = RequestMethod.GET)
    public Object directtartTimedTask(@PathVariable("jobNo") String jobNo) {
        boolean flag = true;
        try {
            scheduleService.directStartJob(jobNo);
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }


}
