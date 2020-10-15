package com.rlc.midServer.common.quartz;

import com.rlc.midServer.common.quartz.job.QuartzJob_XQ;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail jobDetail1(){
        return JobBuilder.newJob(QuartzJob_XQ.class).storeDurably().build();
    }

    @Bean
    public Trigger trigger1(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(10) //每一秒执行一次
                .withIntervalInMinutes(10)
                .repeatForever(); //永久重复，一直执行下去
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail1())
                .withSchedule(scheduleBuilder)
                .build();
    }
}