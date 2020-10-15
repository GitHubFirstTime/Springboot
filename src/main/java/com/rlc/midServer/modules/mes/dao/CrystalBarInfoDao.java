package com.rlc.midServer.modules.mes.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.rlc.midServer.common.persistence.CrudDao;
import com.rlc.midServer.common.persistence.annotation.MyBatisDao;
import com.rlc.midServer.modules.mes.entity.CrystalBarInfoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
@DS("mesdb")
public interface CrystalBarInfoDao extends CrudDao<CrystalBarInfoDTO> {
    /*拿到设备上晶棒信息*/
    List<CrystalBarInfoDTO> getCrystalInfoList(@Param("eqpName") String eqpName);
    /*XQ对应的缓存工位上的晶棒信息*/
    CrystalBarInfoDTO getBufferStationCrystalInfoOne(@Param("eqpName") String eqpName);
}
