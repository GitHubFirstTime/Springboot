package com.rlc.midServer.modules.mes.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.rlc.midServer.common.persistence.CrudDao;
import com.rlc.midServer.common.persistence.annotation.MyBatisDao;
import com.rlc.midServer.modules.mes.entity.EqpDTO;

import java.util.List;
import java.util.Map;

@MyBatisDao
@DS("mesdb")
public interface EqpDao extends CrudDao<EqpDTO> {

    /**
     * 查询所有机台状态信息
     * @param eqpDTO
     * @return List<EqpDTO>
     * @throws Exception
     */
    List<EqpDTO> findEqpStateList(EqpDTO eqpDTO) throws Exception;

    /**
     * 查询设备当前处理的晶棒信息
     * @param eqpDTO
     * @return List<Map<String,Object>>
     * @throws Exception
     */
    List<Map<String,Object>> findEqpLotList(EqpDTO eqpDTO) throws Exception;

    /**
     * 查询设备当前详情
     * @param xqEqpDTO
     * @return EqpDTO
     * @throws Exception
     */
    EqpDTO getEqpCurrentInfo(EqpDTO xqEqpDTO) throws Exception;
}
