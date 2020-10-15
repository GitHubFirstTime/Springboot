package com.rlc.midServer.modules.fmb.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.rlc.midServer.common.persistence.CrudDao;
import com.rlc.midServer.common.persistence.annotation.MyBatisDao;
import com.rlc.midServer.modules.fmb.entity.FmbEqp;

import java.util.List;
import java.util.Objects;

@MyBatisDao
@DS("fmbdb")
public interface FmbEqpDao extends CrudDao<FmbEqp> {
    /**
     * 插入新机台信息
     * @param fmbEqpList
     * @throws Exception
     */
    public void insertBatch(List<FmbEqp> fmbEqpList) throws Exception;
    /**
     * 修改机台在mes中是否脱管
     * @param fmbEqpList
     * @throws Exception
     */
    public void updateEqpOutManageBatch(List<FmbEqp> fmbEqpList) throws Exception;

    /**
     * 机台信息在mes中有变更
     * 目前只支持名称变更更改
     * @param fmbEqpList
     * @throws Exception
     */
    public void updateBatchByMesData(List<FmbEqp> fmbEqpList) throws Exception;
}
