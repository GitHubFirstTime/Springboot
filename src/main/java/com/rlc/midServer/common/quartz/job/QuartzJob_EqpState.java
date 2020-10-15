package com.rlc.midServer.common.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * TODO
 * ClassName:QuartzJob_EqpState <br/>
 * Function: 机台状态更新 ADD FUNCTION. <br/>
 * Reason:	 机台状态更新 ADD REASON. <br/>
 *
 * @author RLC_ZYC
 * @version 1.0
 * @date 2020/9/29 13:25
 * @since JDK 1.8
 */
@Component
public class QuartzJob_EqpState extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }
}