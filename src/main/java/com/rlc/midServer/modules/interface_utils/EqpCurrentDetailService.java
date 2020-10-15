package com.rlc.midServer.modules.interface_utils;

import com.rlc.midServer.modules.mes.service.EqpService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * TODO
 * ClassName:EqpCurrentDetailService <br/>
 * Function: 机台当前详情查询service ADD FUNCTION. <br/>
 * Reason:	 机台当前详情查询service ADD REASON. <br/>
 *
 * @author RLC_ZYC
 * @version 1.0
 * @date 2020/9/27 17:44
 * @since JDK 1.8
 */
@Component
public class EqpCurrentDetailService {
    Logger logger =  LogManager.getLogger(EqpCurrentDetailService.class);
    @Autowired
    private EqpService xqEqpService;
    public void SyncEqpState(String eqpType){
       /* try {
            List<EqpDTO> xqEqpDTOList = xqEqpService.findList(new EqpDTO());
            List<FmbEqp> fmbEqpList = fmbEqpService.findList(new FmbEqp());
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println("QuartzJob1----xqEqpDTOList="+xqEqpDTOList.size()+",fmbEqpList"+fmbEqpList.size()+"," + sdf.format(new Date()));

            //获取mes脱管机台
            List<FmbEqp> fmbEqpList_invalid = Lists.newArrayList();//无效机台（mes脱管）
            fmbEqpList_invalid = checkEqpValid(xqEqpDTOList,fmbEqpList);

            //获取mes中基础信息变更的机台
            List<FmbEqp> eqpInfoChangeList = Lists.newArrayList();
            eqpInfoChangeList = checkEqpInfoChange(xqEqpDTOList,fmbEqpList);


            //获取mes新增机台
            List<EqpDTO> xqEqpDTOListTemp = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(xqEqpDTOList)){
                for (int i = 0; i < xqEqpDTOList.size(); i++) {
                    for (int j = 0; j < fmbEqpList.size(); j++) {
                        if (Objects.equals(xqEqpDTOList.get(i).getId(),fmbEqpList.get(j).getEqpCode())){
                            xqEqpDTOListTemp.add(xqEqpDTOList.get(i));
                        }
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(xqEqpDTOListTemp)){
                xqEqpDTOList.removeAll(xqEqpDTOListTemp);
            }
            //保存mes新增机台
            if (CollectionUtils.isNotEmpty(xqEqpDTOList)){
                List<FmbEqp> fmbEqpList_new = Lists.newArrayList();
                makeNewEqp(xqEqpDTOList,fmbEqpList_new);
                saveNewEqp(fmbEqpList_new);
                logger.info("新增线切机{}台，检测时间:{}",fmbEqpList_new.size(),sdf.format(new Date()));
            }else{
                logger.info("没有新增线切机，检测时间:{}",sdf.format(new Date()));
            }

            //mes脱管机台，fmb进行信息变更
            if (CollectionUtils.isNotEmpty(fmbEqpList_invalid)){
                setInvalidEqp(fmbEqpList_invalid);
                logger.info("共有{}台线切机在mes中变为无效（脱管），检测时间:{}",fmbEqpList_invalid.size(),sdf.format(new Date()));
            }else{
                logger.info("没有线切机在mes中变为无效（脱管），检测时间:{}",sdf.format(new Date()));
            }

            //mes中基础信息变更的机台，fmb进行信息变更
            if (CollectionUtils.isNotEmpty(eqpInfoChangeList)){
                updateBatchByMesData(eqpInfoChangeList);
                logger.info("共有{}台线切机基础信息发生变更，检测时间:{}",eqpInfoChangeList.size(),sdf.format(new Date()));
            }else{
                logger.info("没有线切机基础信息变更，检测时间:{}",sdf.format(new Date()));
            }
            return ResultData.OK().message("同步成功");
        } catch (Exception e) {
            logger.error("线切机台数据同步失败",e);
            return ResultData.ERROR().message("同步失败");
        }*/
    }

}