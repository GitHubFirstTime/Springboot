package com.rlc.midServer.modules.mes.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.rlc.midServer.common.persistence.CrudDao;
import com.rlc.midServer.common.persistence.annotation.MyBatisDao;
import com.rlc.midServer.modules.mes.entity.EqpDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
@DS("mesdb")
public interface EqpDetailDao extends CrudDao<EqpDetailDTO>{
    /*拿到一条设备信息*/
    EqpDetailDTO getEQPDetailsOne(@Param("device_id") String device_id);

    /*拿到某一类设备信息*/
    List<EqpDetailDTO> getEQPDetailsList(@Param("eqpType") String eqpType);

    /*一台设备运行状态*/
    EqpDetailDTO getEqpRunStatusInfo(@Param("device_id") String device_id);

    /*运行状态汇总信息*/
    List<EqpDetailDTO> getEqpRunStatusStatistics(@Param("eqpType") String eqpType);
}
