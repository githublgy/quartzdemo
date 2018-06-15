package com.example.demo.quartz.service;

import com.example.demo.quartz.entity.TaskStatus;
import com.example.demo.quartz.entity.TimedTask;
import com.example.demo.quartz.repository.TimedTaskRepository;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by lgy on 2018/6/11.
 */
@Service
public class ScheduleService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ScheduleService.class);
    @Autowired
    private TimedTaskRepository timedTaskRepository;
    @Autowired
    private Scheduler scheduler;

    @Transactional
    public TimedTask createTimedTask(final TimedTask timedTask) {

        return timedTaskRepository.save(timedTask);
    }

    @Transactional
    public TimedTask updateTimedTask( TimedTask timedTask) {
        TimedTask oldtimedTask = timedTaskRepository.findByJobNo(timedTask.getJobNo());
        if(timedTask.getCron()!=null) {
            oldtimedTask.setCron(timedTask.getCron());
        }
        if(timedTask.getStatus()!=null) {
            oldtimedTask.setStatus(timedTask.getStatus());
        }
        return timedTaskRepository.save(oldtimedTask);
    }

    public List<TimedTask> queryAllTimedTask() {
        return timedTaskRepository.findAll();
    }

    public TimedTask queryTimedTaskByJobNo(final String jobNo) {
        return timedTaskRepository.findByJobNo(jobNo);
    }


    public void startAllJob() throws Exception {
        final List<TimedTask> timedTaskList = timedTaskRepository.findAll();
        for (final TimedTask timedTask : timedTaskList) {
            Class jobClass = Class.forName(timedTask.getRunClassName());

            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(timedTask.getTaskName(), timedTask.getTaskGroup())
                    .build();
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(timedTask.getCron());
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(timedTask.getTaskName(), timedTask.getTaskGroup())
                    .withSchedule(cronScheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, cronTrigger);
            LOGGER.info("load timedtask " + timedTask.getTaskName() + timedTask.getStatus());
            pauseJobByStatus(timedTask);
        }
        scheduler.start();
    }

    public void resumeJob(final String jobNo) throws SchedulerException {
        TimedTask timedTask = timedTaskRepository.findByJobNo(jobNo);
        JobKey jobKey = new JobKey(timedTask.getTaskName(), timedTask.getTaskGroup());
        TimedTask newTimedTask = new TimedTask();
        newTimedTask.setJobNo(jobNo);
        newTimedTask.setStatus(TaskStatus.START);
        updateTimedTask(newTimedTask);
        scheduler.resumeJob(jobKey);

    }

    public void pauseJob(final String jobNo) throws SchedulerException {
        TimedTask timedTask = timedTaskRepository.findByJobNo(jobNo);
        JobKey jobKey = new JobKey(timedTask.getTaskName(), timedTask.getTaskGroup());
        TimedTask newTimedTask = new TimedTask();
        newTimedTask.setJobNo(jobNo);
        newTimedTask.setStatus(TaskStatus.STOP);
        updateTimedTask(newTimedTask);
        scheduler.pauseJob(jobKey);
    }

    public void pauseJobByStatus(final TimedTask timedTask) throws SchedulerException {
        if (timedTask.getStatus() == TaskStatus.STOP) {
            JobKey jobKey = new JobKey(timedTask.getTaskName(), timedTask.getTaskGroup());
            scheduler.pauseJob(jobKey);
            LOGGER.info("pause the  " + timedTask.getTaskName());
        }
    }

    public void modifyJob(final TimedTask timedTask) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(timedTask.getTaskName(), timedTask.getTaskGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(timedTask.getCron()).withMisfireHandlingInstructionDoNothing();
        trigger = trigger.getTriggerBuilder()
                .withIdentity(triggerKey)
                .withSchedule(cronScheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, trigger);
        pauseJobByStatus(timedTask);
        LOGGER.info("modify the " + timedTask.getTaskName() + " Job");
    }

    public void directStartJob(final String jobNo) throws Exception {
        TimedTask timedTask = timedTaskRepository.findByJobNo(jobNo);
        Class jobClass = Class.forName(timedTask.getRunClassName());
        Method startJob = jobClass.getMethod("startJob", null);
        startJob.invoke(jobClass.newInstance(), null);

    }


}
