package com.rlc.midServer.modules.interface_utils;

import com.google.common.collect.Lists;
import com.rlc.midServer.common.result.ResultData;
import com.rlc.midServer.modules.fmb.entity.FmbEqp;
import com.rlc.midServer.modules.fmb.service.FmbEqpService;
import com.rlc.midServer.modules.mes.entity.EqpDTO;
import com.rlc.midServer.modules.mes.service.EqpService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * TODO
 * ClassName:SyncEqpDataService <br/>
 * Function: 同步机台数据的service ADD FUNCTION. <br/>
 * Reason:	 同步机台数据的service ADD REASON. <br/>
 *
 * @author RLC_ZYC
 * @version 1.0
 * @date 2020/9/14 9:42
 * @since JDK 1.8
 */
@Component
public class SyncEqpDataService{

    Logger logger =  LogManager.getLogger(SyncEqpDataService.class);
    @Autowired
    private EqpService xqEqpService;
    @Autowired
    private FmbEqpService fmbEqpService;

    public ResultData Sync_EqpData(String eqpType){
        String msg = "";
        try {
            EqpDTO eqpDTO = new EqpDTO();
            eqpDTO.setEqpType(eqpType);
            List<EqpDTO> xqEqpDTOList = xqEqpService.findList(eqpDTO);
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
                msg = "新增线切机"+fmbEqpList_new.size()+"台";
            }else{
                logger.info("没有新增线切机，检测时间:{}",sdf.format(new Date()));
                msg = "没有新增线切机";
            }

            //mes脱管机台，fmb进行信息变更
            if (CollectionUtils.isNotEmpty(fmbEqpList_invalid)){
                setInvalidEqp(fmbEqpList_invalid);
                logger.info("共有{}台线切机在mes中变为无效（脱管），检测时间:{}",fmbEqpList_invalid.size(),sdf.format(new Date()));
                msg = msg+"\n共有"+fmbEqpList_invalid.size()+"台线切机在mes中变为无效（脱管）";
            }else{
                logger.info("没有线切机在mes中变为无效（脱管），检测时间:{}",sdf.format(new Date()));
                msg = msg+"\n没有线切机在mes中变为无效（脱管）";
            }

            //mes中基础信息变更的机台，fmb进行信息变更
            if (CollectionUtils.isNotEmpty(eqpInfoChangeList)){
                updateBatchByMesData(eqpInfoChangeList);
                logger.info("共有{}台线切机基础信息发生变更，检测时间:{}",eqpInfoChangeList.size(),sdf.format(new Date()));
                msg = msg+"\n共有"+eqpInfoChangeList.size()+"台线切机基础信息发生变更";
            }else{
                logger.info("没有线切机基础信息变更，检测时间:{}",sdf.format(new Date()));
                msg = msg+"\n没有线切机基础信息变更";
            }
            return ResultData.OK().message("同步成功\n"+msg);
        } catch (Exception e) {
            logger.error("线切机台数据同步失败",e);
            return ResultData.ERROR().message("同步失败");
        }
    }

    /**
     * 检查fmbeqp 有效性
     * @param xqEqpDTOList
     * @param fmbEqpList
     */
    public List<FmbEqp> checkEqpValid(List<EqpDTO> xqEqpDTOList, List<FmbEqp> fmbEqpList){
        List<FmbEqp> invalidEqpList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(fmbEqpList)){
            for (int i = 0; i < fmbEqpList.size(); i++) {
                int token = 0;
                for (int j = 0; j < xqEqpDTOList.size(); j++) {
                    if (Objects.equals(xqEqpDTOList.get(j).getId(),fmbEqpList.get(i).getEqpCode())
                            &&Objects.equals("0",fmbEqpList.get(i).getMesState())){
                        token++;
                    }
                }
                if (token==0){
                    if (Objects.equals("0",fmbEqpList.get(i).getMesState())){
                        fmbEqpList.get(i).setMesState("1");//机台有效性0:有效；1:无效
                        invalidEqpList.add(fmbEqpList.get(i));
                    }
                }
            }
        }
        return invalidEqpList;
    }

    /**
     * 组装新机台信息
     * @param xqEqpDTOList
     * @param fmbEqpList
     */
    public void makeNewEqp(List<EqpDTO> xqEqpDTOList, List<FmbEqp> fmbEqpList){
        if (CollectionUtils.isNotEmpty(xqEqpDTOList)){
            for (int i = 0; i < xqEqpDTOList.size(); i++) {
                FmbEqp fmbEqp = new FmbEqp();
                fmbEqp.setEqpCode(xqEqpDTOList.get(i).getId());
                fmbEqp.setEqpName(xqEqpDTOList.get(i).getEqpName());
                fmbEqp.setEqpType(xqEqpDTOList.get(i).getEqpType());
                fmbEqp.setMesState("0");//机台有效性0:有效；1:无效
                fmbEqp.setControlMode(xqEqpDTOList.get(i).getEqpControlMode());
                fmbEqp.setEqpState(xqEqpDTOList.get(i).getEqpRunState());
                fmbEqp.setDispatchMode(xqEqpDTOList.get(i).getEqpControlMode());
//                fmbEqp.setTransportMode(xqEqpDTOList.get(i).);
                fmbEqp.setTakeOver("0");//接管状态 0:未接管；1：已接管'
                fmbEqp.preInsert();
                fmbEqpList.add(fmbEqp);
            }
        }
    }
    /**
     * 插入新机台
     * @param fmbEqpList
     */
    public void saveNewEqp(List<FmbEqp> fmbEqpList){
        try {
            fmbEqpService.insertBatch(fmbEqpList);
        }catch (Exception e){
            logger.error("saveNewEqp,",e);
        }
    }

    /**
     * 设置无效机台(mes脱管)
     * @param fmbEqpList
     */
    public void setInvalidEqp(List<FmbEqp> fmbEqpList){
        try {
            fmbEqpService.updateEqpOutManageBatch(fmbEqpList);
        } catch (Exception e) {
            logger.error("setInvalidEqp",e);
        }
    }

    /**
     * 检测并返回有基础信息变更的机台集合
     * @param xqEqpDTOList
     * @param fmbEqpList
     * @return List<FmbEqp>
     */
    public List<FmbEqp> checkEqpInfoChange(List<EqpDTO> xqEqpDTOList, List<FmbEqp> fmbEqpList){
        List<FmbEqp> eqpInfoChangeList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(fmbEqpList)){
            for (int i = 0; i < fmbEqpList.size(); i++) {
                int token = 0;
                for (int j = 0; j < xqEqpDTOList.size(); j++) {
                    if (Objects.equals(fmbEqpList.get(i).getEqpCode(),xqEqpDTOList.get(j).getId())
                            &&!Objects.equals(fmbEqpList.get(i).getEqpName(),xqEqpDTOList.get(j).getEqpName())){
                        token++;
                        fmbEqpList.get(i).setEqpName(xqEqpDTOList.get(j).getEqpName());
                    }
                }
                if (token>0){
                    eqpInfoChangeList.add(fmbEqpList.get(i));
                }
            }
        }
        return eqpInfoChangeList;
    }
    /**
     * 更新基础信息与mes不一致的机台
     * 当前只支持名称变更
     * @param fmbEqpList
     */
    public void updateBatchByMesData(List<FmbEqp> fmbEqpList){
        try {
            fmbEqpService.updateBatchByMesData(fmbEqpList);
        } catch (Exception e) {
            logger.error("updateBatchByMesData",e);
        }
    }
}