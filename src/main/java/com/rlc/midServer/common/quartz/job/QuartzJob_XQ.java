package com.rlc.midServer.common.quartz.job;

import com.rlc.midServer.modules.interface_utils.SyncEqpDataService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * 线切机台数据同步定时任务
 */
@Component
public class QuartzJob_XQ extends QuartzJobBean {
    Logger logger =  LogManager.getLogger(QuartzJob_XQ.class);
    @Autowired
    private SyncEqpDataService syncEqpDataService;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        syncEqpDataService.Sync_EqpData("XQ");
    }

}