package com.rlc.midServer.modules.mes.service;

import com.rlc.midServer.modules.mes.dao.EqpDetailDao;
import com.rlc.midServer.modules.mes.entity.EqpDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EqpStatusService {
    @Autowired
    private EqpDetailDao eqpDetailDao;

    public EqpDetailDTO getEqpRunStatusInfo(String device_id) {return  eqpDetailDao.getEqpRunStatusInfo(device_id);}

    public List<EqpDetailDTO> getRunStatusStatistics(String eqpType){return eqpDetailDao.getEqpRunStatusStatistics(eqpType); }
}
