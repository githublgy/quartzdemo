package com.example.demo.quartz.repository;


import com.example.demo.quartz.entity.TimedTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lgy on 2018/6/11.
 */
public interface TimedTaskRepository extends JpaRepository<TimedTask, String>, JpaSpecificationExecutor<TimedTask> {
    TimedTask findByJobNo(String jobNo);

}
