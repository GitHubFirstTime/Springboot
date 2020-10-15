package com.rlc.midServer.modules.mes.service;

import com.rlc.midServer.common.service.CrudService;
import com.rlc.midServer.modules.mes.dao.EqpDetailDao;
import com.rlc.midServer.modules.mes.entity.EqpDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EqpDetailService extends CrudService<EqpDetailDao,EqpDetailDTO>{
    @Autowired
    private EqpDetailDao eqpDetailDao;

    public EqpDetailDTO getEqpDetailsOne(String device_id) {
        return eqpDetailDao.getEQPDetailsOne(device_id);
    }

    public List<EqpDetailDTO> getEQPDetailsList(String eqpType){return eqpDetailDao.getEQPDetailsList(eqpType);}
}
