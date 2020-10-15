package com.rlc.midServer.modules.fmb.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.rlc.midServer.common.service.CrudService;
import com.rlc.midServer.modules.fmb.dao.FmbEqpDao;
import com.rlc.midServer.modules.fmb.entity.FmbEqp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DS("fmbdb")
@Service
@Transactional(readOnly = true)
public class FmbEqpService extends CrudService<FmbEqpDao, FmbEqp> {
    public void insertBatch(List<FmbEqp> fmbEqpList) throws Exception{
        dao.insertBatch(fmbEqpList);
    }
    /**
     * 修改机台在mes中是否脱管
     * @param fmbEqpList
     * @throws Exception
     */
    public void updateEqpOutManageBatch(List<FmbEqp> fmbEqpList) throws Exception{
        dao.updateEqpOutManageBatch(fmbEqpList);
    }

    /**
     * 机台信息在mes中有变更
     * 目前只支持名称变更更改
     * @param fmbEqpList
     * @throws Exception
     */
    public void updateBatchByMesData(List<FmbEqp> fmbEqpList) throws Exception{
        dao.updateBatchByMesData(fmbEqpList);
    }
}