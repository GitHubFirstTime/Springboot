package com.rlc.midServer.modules.stereoLayer.controller;

import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rlc.midServer.common.security.TokenUtil;
import com.rlc.midServer.common.web.BaseController;
import com.rlc.midServer.modules.mes.entity.CrystalBarInfoDTO;
import com.rlc.midServer.modules.mes.entity.EqpDetailDTO;
import com.rlc.midServer.modules.mes.service.CrystalBarInfoService;
import com.rlc.midServer.modules.mes.service.EqpDetailService;
import com.rlc.midServer.modules.mes.service.EqpService;
import com.rlc.midServer.modules.mes.service.EqpStatusService;
import com.rlc.midServer.modules.stereoLayer.common.result.StereoResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

/**
 * TODO
 * ClassName:StereoLayerController <br/>
 * Function: 3D展示层数据接口 ADD FUNCTION. <br/>
 * Reason:	 3D展示层数据接口 ADD REASON. <br/>
 *
 * @author RLC_ZYC
 * @version 1.0
 * @date 2020/9/18 13:56
 * @since JDK 1.8
 */
@RestController
@RequestMapping(value = "v1")
public class StereoLayerController extends BaseController {
    Gson gson = new GsonBuilder().create();
    @Autowired
    private EqpService xqEqpService;

    @Autowired
    private EqpDetailService eqpDetailService;

    @Autowired
    private CrystalBarInfoService crystalBarInfoService;

    @Autowired
    private EqpStatusService eqpStatusService;
    @RequestMapping("auth/getToken")
    public StereoResult getToken(String appId){
        String token = TokenUtil.createToken(appId,30);
        return StereoResult.SUCCESS().MESSAGE("统计数据获取成功").CONTENT(token);
    }
    /**
     * 获取设备容量
     * @param device_type
     * @param device_id
     * @return StereoResult
     */
    @RequestMapping("/eqpCapacity")
    public StereoResult eqpCapacity(String device_type, String device_id){

        return StereoResult.SUCCESS().MESSAGE("统计数据获取成功");
    }

    /**
     * 设备产量
     * @param device_type
     * @param device_id
     * @return StereoResult
     */
    @RequestMapping("/eqpOutput")
    public StereoResult eqpOutput(String device_type, String device_id, Date start_time,Date end_time,
                                  int page_num,int page_size,String period){

        return StereoResult.SUCCESS().MESSAGE("统计数据获取成功");
    }

    /**
     * 设备机台利用率
     * @param device_type
     * @param device_id
     * @return
     */
    @RequestMapping("/eqpWorkingRate")
    public StereoResult eqpWorkingRate(String device_type, String device_id, Date start_time,Date end_time,
                                       int page_num,int page_size,String period){

        return StereoResult.SUCCESS().MESSAGE("统计数据获取成功");
    }

    /**
     * 设备成品率
     * @param device_type
     * @param device_id
     * @return
     */
    @RequestMapping("/eqpAcceptedGoodsRate")
    public StereoResult eqpAcceptedGoodsRate(String device_type, String device_id, Date start_time,Date end_time,
                                             int page_num,int page_size,String period){
        logger.error("start_time:{}",start_time);
        return StereoResult.SUCCESS().MESSAGE("统计数据获取成功");
    }

    /**
     * 获取机台信息
     * @param eqpDetailParamJson
     * @return
     */
    @RequestMapping("/eqpDetail")
    public StereoResult eqpDetail(String device_id,String is_buffer_station){
        /*EqpDTO xqEqpDTO = new EqpDTO();
        xqEqpDTO.setEqpName(device_id);
    public StereoResult eqpDetail(@RequestBody(required=true)String eqpDetailParamJson){
        try {
            String eqpDetailParamJsonStr = URLDecoder.decode(eqpDetailParamJson,"UTF-8");
            JSONObject jsonObject = JSONObject.fromObject(eqpDetailParamJsonStr);
            EqpDTO xqEqpDTO = new EqpDTO();
            xqEqpDTO.setEqpName(jsonObject.getString("device_id"));
            xqEqpDTO = xqEqpService.getEqpCurrentInfo(xqEqpDTO);
            return StereoResult.SUCCESS().MESSAGE("获取机台信息").CONTENT(gson.toJson(xqEqpDTO));
        } catch (Exception e) {
            logger.error("eqpDetail error is:",e);
            return StereoResult.ERROR().MESSAGE("获取机台失败").CODE("500");
        }
        Gson gson = new GsonBuilder().create();
        return StereoResult.SUCCESS().MESSAGE("获取机台信息").CONTENT(gson.toJson(xqEqpDTO));*/
        if(StringUtils.isBlank(device_id)){
            return StereoResult.MISSINGPARAMERROR().MESSAGE("缺少参数");
        }
        if( Boolean.valueOf(is_buffer_station) ){
            String eqpType = eqpDetailService.getEqpDetailsOne(device_id).getEqpType();
            String eqpName = eqpDetailService.getEqpDetailsOne(device_id).getEqpName();
            JSONObject job = new JSONObject();
            if(eqpType.equals("XQ")){
                try{
                    CrystalBarInfoDTO bufferStationCrystalInfoOne = crystalBarInfoService.getBufferStationCrystalInfoOne(eqpName);
                    if(bufferStationCrystalInfoOne!=null){
                        job.put("buffer_device_name",eqpName);
                        job.put("crystal_bar_id",bufferStationCrystalInfoOne.getAppId());
                        job.put("produce_plan",bufferStationCrystalInfoOne.getOrderNumber());
                        job.put("produce_plan_color",bufferStationCrystalInfoOne.getOrderNumberColor());
                        job.put("rod_length",bufferStationCrystalInfoOne.getTotalLength());
                        job.put("mold_type",bufferStationCrystalInfoOne.getMoldType());
                        String s = bufferStationCrystalInfoOne.getTrackInTime().substring(0,15);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmmss");
                        Date date = sdf.parse(s);
                        job.put("enter_machine_time",date.getTime());
                    }else {
                        return StereoResult.UNKNOWNERROR().MESSAGE("线切机缓存工位晶棒信息为空");
                    }
                }catch (Exception e){
                    return StereoResult.UNKNOWNERROR().MESSAGE("eqpDetail error is"+e.getMessage());
                }
                return StereoResult.SUCCESS().MESSAGE("线切机缓存工位晶棒信息").CONTENT(job.toString());
            }else {
                return StereoResult.UNKNOWNERROR().MESSAGE("缓存工位不属于线切机");
            }
        }
        JSONObject job = new JSONObject();
        try{
            EqpDetailDTO eqpDetailDTO = eqpDetailService.getEqpDetailsOne(device_id);
            String eqpName = eqpDetailDTO.getEqpName();
            job.put("device_id", eqpDetailDTO.getId());
            job.put("device_name", eqpDetailDTO.getEqpName());
            job.put("device_type_name", eqpDetailDTO.getEqpType());
            job.put("device_floor", eqpDetailDTO.getFloor());
            job.put("running_mode", eqpDetailDTO.getEqpRunState());
            job.put("send_mode", eqpDetailDTO.getEqpDispatchMode());
            job.put("control_mode", eqpDetailDTO.getEqpControlMode());
            job.put("receipe_code", eqpDetailDTO.getRecipeName());

            List<CrystalBarInfoDTO> crystalInfoList = crystalBarInfoService.getCrystalInfoList(eqpName);
            JSONArray jsonArray = new JSONArray();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmmss");
            for (int i = 0; i < crystalInfoList.size(); i++) {
                CrystalBarInfoDTO crystalBarInfoOne = crystalInfoList.get(i);
                JSONObject crystalBarInfo = new JSONObject();
                crystalBarInfo.put("crystal_bar_id",crystalBarInfoOne.getAppId());
                crystalBarInfo.put("produce_plan",crystalBarInfoOne.getOrderNumber());
                crystalBarInfo.put("produce_plan_color",crystalBarInfoOne.getOrderNumberColor());
                crystalBarInfo.put("rod_length",crystalBarInfoOne.getTotalLength());
                crystalBarInfo.put("mold_type",crystalBarInfoOne.getMoldType());
                String s = crystalBarInfoOne.getTrackInTime().substring(0,15);
                Date date = sdf.parse(s);
                crystalBarInfo.put("enter_machine_time",date.getTime());
                jsonArray.add(crystalBarInfo);
            }
            job.put("crystal_bar_info",jsonArray);
        }catch (Exception e){
            StereoResult.UNKNOWNERROR().MESSAGE(""+e.getMessage());
        }
        return StereoResult.SUCCESS().MESSAGE("统计数据获取成功").CONTENT(job.toString());
    }

    /**
     * 设备维护信息列表
     * @param device_id
     * @param start_time
     * @param end_time
     * @param page_num
     * @param page_size
     * @return
     */
    @RequestMapping("/eqpPMSList")
    public StereoResult eqpPMSList(String device_id, Date start_time,Date end_time,
                                  int page_num,int page_size){

        return StereoResult.SUCCESS().MESSAGE("统计数据获取成功");
    }

    /**
     * 设备维护信息详情
     * @param device_id
     * @param pms_id
     * @return
     */
    @RequestMapping("/eqpPMSDetail")
    public StereoResult eqpPMSDetail(String device_id, String pms_id){

        return StereoResult.SUCCESS().MESSAGE("统计数据获取成功");
    }

    /**
     * 设备告警列表
     * @param device_type
     * @param device_id
     * @param start_time
     * @param end_time
     * @param page_num
     * @param page_size
     * @param period
     * @return
     */
    @RequestMapping("/eqpAlarmList")
    public StereoResult eqpAlarmList(String device_type,String device_id, Date start_time,Date end_time,
                                     int page_num,int page_size,String period){

        return StereoResult.SUCCESS().MESSAGE("统计数据获取成功");
    }
    /**
     * 设备告警详情
     * @param device_id
     * @param alarm_info_id
     * @return
     */
    @RequestMapping("/eqpAlarmDetail")
    public StereoResult eqpAlarmDetail(String device_id, String alarm_info_id){

        return StereoResult.SUCCESS().MESSAGE("统计数据获取成功");
    }

    /**
     * 设备运行状态
     * @param device_id
     * @return
     */
    @RequestMapping("/eqpRunStatus")
    public StereoResult eqpRunStatus(String device_id){
        StringUtils.isBlank(device_id);
        JSONObject job = null;
        try{
            EqpDetailDTO eqpRunStatusInfo = eqpStatusService.getEqpRunStatusInfo(device_id);
            job = new JSONObject();
            job.put("running_status", eqpRunStatusInfo.getEqpRunState());
            job.put("device_id", eqpRunStatusInfo.getId());
            job.put("device_name", eqpRunStatusInfo.getEqpName());
            job.put("device_type_name", eqpRunStatusInfo.getEqpType());
        }catch (Exception e){
            return StereoResult.UNKNOWNERROR().MESSAGE("eqpRunStatus error is:"+e.getMessage());
        }
        return StereoResult.SUCCESS().MESSAGE("统计数据获取成功").CONTENT(job.toString());
    }
    @RequestMapping("/eqpRunStatusStatistics")
    public StereoResult eqpRunStatusStatistics(@RequestBody List<String> eqpTypes){

        JSONObject job = new JSONObject();
        for (int i = 0; i < eqpTypes.size(); i++) {
            String eqpType = eqpTypes.get(i);
            try{
                List<EqpDetailDTO> runStatusStatistics = eqpStatusService.getRunStatusStatistics(eqpType);
                JSONArray jsonArray = new JSONArray();
                for (int j = 0; j < runStatusStatistics.size() ; j++) {
                    EqpDetailDTO eqpRunStatusOne = runStatusStatistics.get(j);
                    JSONObject eqpRunStatusInfo = new JSONObject();
                    eqpRunStatusInfo.put("device_status",eqpRunStatusOne.getEqpRunState());
                    eqpRunStatusInfo.put("device_num",eqpRunStatusOne.getTotalNumber());
                    jsonArray.add(eqpRunStatusInfo);
                }
                job.put(eqpType,jsonArray);
            }catch (Exception e){
                StereoResult.UNKNOWNERROR().MESSAGE("eqpRunStatusStatistics error is:"+e.getMessage());
            }
        }
        return StereoResult.SUCCESS().MESSAGE("统计数据获取成功").CONTENT(job.toString());
    }
}