package com.wjj.application.facade.kangmei;

import com.alibaba.fastjson.JSON;
import com.wjj.application.bo.DiagnoseAggregateBo;
import com.wjj.application.common.enums.GenderEnum;
import com.wjj.application.common.enums.PrescriptionTypeEnum;
import com.wjj.application.common.enums.SaasReturnCode;
import com.wjj.application.config.ExecutorConfig;
import com.wjj.application.constant.dispensing.DispensingConst;
import com.wjj.application.constant.medicalhistory.MedicalHistoryConst;
import com.wjj.application.date.DateUtil;
import com.wjj.application.dto.dispensing.QueryHistoryDispensingDTO;
import com.wjj.application.entity.dispensing.Dispensing;
import com.wjj.application.entity.medicalhistory.MedicalHistory;
import com.wjj.application.entity.medicalhistory.MedicalHistoryDiagnose;
import com.wjj.application.entity.medicalhistory.MedicalHistoryPrescription;
import com.wjj.application.exception.CommonException;
import com.wjj.application.facade.kangmei.model.OrderInfo;
import com.wjj.application.facade.kangmei.model.save.Data;
import com.wjj.application.facade.kangmei.model.save.OrderInfo1;
import com.wjj.application.facade.kangmei.model.save.Pdetail;
import com.wjj.application.facade.kangmei.model.save.Xq;
import com.wjj.application.facade.kangmei.util.KangMeiUtil;
import com.wjj.application.facade.medicalhistory.MedicalHistoryPrescriptionComponent;
import com.wjj.application.response.ReturnCode;
import com.wjj.application.service.dispensing.DispensingService;
import com.wjj.application.service.medicalhistory.MedicalHistoryDiagnoseService;
import com.wjj.application.service.medicalhistory.MedicalHistoryPrescriptionService;
import com.wjj.application.service.medicalhistory.MedicalHistoryService;
import com.wjj.application.service.warehousing.WarehousingOrderService;
import com.wjj.application.util.HmacUtils;
import com.wjj.application.util.ObjectUtil;
import com.wjj.application.vo.dispensing.ConsigneeAndLogisticsDataVO;
import com.wjj.application.vo.dispensing.DispensingVO;
import com.wjj.application.vo.medicalhistory.MedicalHistoryPrescriptionChineseListVO;
import com.wjj.application.vo.medicalhistory.MedicalHistoryPrescriptionChineseVO;
import com.wjj.application.vo.warehousing.ItemVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hank
 * @since 2020/11/18
 */
@Component
@Slf4j
@Lazy
public class KangMeiComponent {
    public static final String DATE_FORMAT =  "yyyy-MM-dd HH:mm:ss";

    @Autowired
    private KangMeiUtil kangMeiUtil;
    @Autowired
    private MedicalHistoryPrescriptionComponent medicalHistoryPrescriptionComponent;
    @Autowired
    private MedicalHistoryService medicalHistoryService;
    @Autowired
    private MedicalHistoryPrescriptionService medicalHistoryPrescriptionService;
    @Autowired
    private DispensingService dispensingService;
    @Autowired
    private WarehousingOrderService warehousingOrderService;
    @Autowired
    private MedicalHistoryDiagnoseService medicalHistoryDiagnoseService;

    @Value("${saasBaseUrl}")
    private String saasBaseUrl;

    private final String PARAM_KEY = "NiuBiHANK007$";


    /**
     * 异步同步订单
     * @param medicalHistory
     */
    @Async(ExecutorConfig.COMMON_EXECUTOR)
    public void asyncSyncOrderInfo(MedicalHistory medicalHistory) throws CommonException{
        log.info("KangMeiComponent.asyncSyncOrderInfo medicalHistory:{}", medicalHistory);
        syncOrderInfo(medicalHistory);
    }

    /**
     * 获取param
     * @param medicalHistory
     * @return
     * @throws Exception
     */
    private String getParam(MedicalHistory medicalHistory) throws Exception {
        return URLEncoder.encode(HmacUtils.encryptHMACSigned(medicalHistory.getId()+medicalHistory.getCreateTime().toString(),PARAM_KEY), "UTF-8");
    }

    /**
     * 校验Param
     * @param medicalHistoryId
     * @param param
     * @return
     * @throws Exception
     */
    public Boolean checkParam(String medicalHistoryId, String param) throws Exception {
        MedicalHistory medicalHistory = medicalHistoryService.getById(medicalHistoryId);
        if (medicalHistory == null){
            return false;
        }

        String encryptHMAC = HmacUtils.encryptHMACSigned(medicalHistory.getId() + medicalHistory.getCreateTime().toString(), PARAM_KEY);
        return encryptHMAC.equals(param);
    }

    public static void main(String[] args) throws Exception {
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setId(1331069227993513985L);
        medicalHistory.setCreateTime(DateUtil.parse("2020-11-24 10:56:49", "yyyy-MM-dd HH:mm:ss"));
        System.out.println("https://tcm-saas-test.nhf.cn/api/wjj-saas-system/dispensing" + DispensingConst.KANGMEI_AUDIT_CALLBACK.replaceAll("\\{medicalHistoryId\\}", medicalHistory.getId().toString())+
                "?param="+(new KangMeiComponent()).getParam(medicalHistory));
    }

    /**
     * 同步处方到康美
     * 0. 判断同步状态是否为未同步
     * 1. 修改同步状态为同步中
     * 2. 同步订单处方
     * 3. 修改同步状态
     * @param medicalHistory
     * @return
     * @throws Exception
     */
    public void syncOrderInfo(MedicalHistory medicalHistory) throws CommonException{
        log.info("KangMeiComponent.syncOrderInfo medicalHistory:{}", medicalHistory);
        if (ObjectUtil.objectIsNull(medicalHistory)) {
            throw new CommonException(ReturnCode.RESOURCE_NOT_FOUND.getCode(), "病历单不存在");
        }
        Long medicalHistoryId = medicalHistory.getId();
        List<MedicalHistoryPrescriptionChineseVO> medicalHistoryPrescriptionChineseVoList = medicalHistoryPrescriptionComponent.getMedicalHistoryPrescriptionChineseVoList(medicalHistoryId, Collections.singletonList(MedicalHistoryConst.PRESCRIPTION_STATUS_PAYED));
        if (CollectionUtils.isEmpty(medicalHistoryPrescriptionChineseVoList)) {
            throw new CommonException(ReturnCode.RESOURCE_NOT_FOUND.getCode(), "不存在已支付的处方");
        }
        QueryHistoryDispensingDTO queryHistoryDispensingDTO = new QueryHistoryDispensingDTO();
        queryHistoryDispensingDTO.setMedicalHistoryId(medicalHistoryId);
        queryHistoryDispensingDTO.setPrescriptionType(PrescriptionTypeEnum.HERBAL.getType());
        // 发药单信息
        List<DispensingVO> dispensingVOS = dispensingService.listWaitAuditDispensingByHistoryId(queryHistoryDispensingDTO);
        if(CollectionUtils.isEmpty(dispensingVOS)) {
            throw new CommonException(SaasReturnCode.DATA_NOT_EXIST.getCode(), "不存在待发药单");
        }
        if(!DispensingConst.CLOUD_PHARMACY_SYNC_STATUS_NOT.equals(dispensingVOS.get(0).getCloudPharmacySyncStatus()) && !DispensingConst.CLOUD_PHARMACY_SYNC_STATUS_FAIL.equals(dispensingVOS.get(0).getCloudPharmacySyncStatus())) {
            log.info("云药房同步状态不是未同步或者同步失败 dispensing: {}", JSON.toJSONString(dispensingVOS.get(0)));
            throw new CommonException(SaasReturnCode.DATA_NOT_ALLOW_MODIFY.getCode(), "云药房同步状态不是未同步或者同步失败,无法再次同步");
        }
        // 修改发药单同步状态
        boolean update = dispensingService.lambdaUpdate()
                .eq(Dispensing::getMedicalHistoryId, medicalHistoryId)
                .in(Dispensing::getCloudPharmacySyncStatus, DispensingConst.CLOUD_PHARMACY_SYNC_STATUS_NOT, DispensingConst.CLOUD_PHARMACY_SYNC_STATUS_FAIL)
                .set(Dispensing::getCloudPharmacySyncStatus, DispensingConst.CLOUD_PHARMACY_SYNC_STATUS_ING)
                .update();
        if(!update){
            throw new CommonException(SaasReturnCode.DATA_NOT_ALLOW_MODIFY.getCode(), "重复同步或已经同步完成,请刷新后重试");
        }

        DispensingVO dispensingVO = dispensingVOS.get(0);
        OrderInfo<Data> orderInfo = new OrderInfo<>();
        try {
            Data data = new Data();
            // 康美审方回调接口
            data.setCallback_url(saasBaseUrl + "/dispensing" + DispensingConst.KANGMEI_AUDIT_CALLBACK.replaceAll("\\{medicalHistoryId\\}", medicalHistoryId.toString())+
                    "?param="+getParam(medicalHistory));
            // 康美审方回调接口
            data.setLogis_url_callback(saasBaseUrl + "/dispensing" + DispensingConst.KANGMEI_LOGISTICS_CALLBACK.replaceAll("\\{medicalHistoryId\\}", medicalHistoryId.toString())+
                    "?param="+getParam(medicalHistory));
            // 订单生成时间
            data.setOrder_time(new SimpleDateFormat(DATE_FORMAT).format(medicalHistory.getCreateTime()));
            // ID号
            data.setReg_num(medicalHistoryId.toString());

            // 发药单信息
            ConsigneeAndLogisticsDataVO consigneeAndLogisticsData = dispensingVO.getConsigneeAndLogisticsData();
            if(consigneeAndLogisticsData == null){
                log.error("同步康美订单失败, 发货地址不存在 dispensingVO:{}", dispensingVO);
                throw new CommonException(SaasReturnCode.DATA_NOT_EXIST.getCode(), dispensingVO.getDispensingNo()+",发货地址不存在");
            }
            data.setAddr_str(
                    consigneeAndLogisticsData.getProvinceName() + "," +
                            consigneeAndLogisticsData.getCityName() + "," +
                            consigneeAndLogisticsData.getDistrictName() + "," +
                            (StringUtils.isNotBlank(consigneeAndLogisticsData.getTownName()) ? consigneeAndLogisticsData.getTownName() + "," : "") +
                            consigneeAndLogisticsData.getAddress()
            );
            data.setConsignee(consigneeAndLogisticsData.getConsignee());
            data.setCon_tel(consigneeAndLogisticsData.getPhoneNo());
            // 支付方式 1在线支付,2货到付款
            data.setPay_status("1");
            List<Pdetail> pDetailList = new LinkedList<>();
            for (MedicalHistoryPrescriptionChineseVO medicalHistoryPrescriptionChineseVO : medicalHistoryPrescriptionChineseVoList) {
                Pdetail pdetail = new Pdetail();
                pdetail.setUser_name(medicalHistory.getName());
                pdetail.setAge(medicalHistory.getPatientAge().toString());
                //性别 0 女，1 男，2 未知(病人没有登记性别的情况下)
                pdetail.setGender(GenderEnum.MAN.getType().equals(medicalHistory.getSex()) ? "1" : (GenderEnum.FEMALE.getType().equals(medicalHistory.getSex()) ? "0" : "2"));
                pdetail.setTel(medicalHistory.getPhone());
                // 迪迪产品确认:
                //是否煎煮(是否加工是就是代煎,否就是不代煎)  取值范围：0 否，1 是； 膏方、丸剂、免煎颗粒统一非煎煮，传0
                pdetail.setIs_suffering(MedicalHistoryConst.PRESCRIPTION_SUBJOIN_IS_PROCESS_YES.equals(medicalHistoryPrescriptionChineseVO.getIsProcess()) ? "1" : "0");
                // 处方实际剂数，当处方为西药、膏方或丸剂时传1 (一个处方最大剂数为99)
                pdetail.setAmount(String.valueOf(medicalHistoryPrescriptionChineseVO.getDays() * medicalHistoryPrescriptionChineseVO.getEverydayQuantity()));
                //处方类型 0 中药，1西药，2 膏方，3 丸剂，5散剂，7 免煎颗粒
                pdetail.setType("0");
                // 0 内服，1 外用
                pdetail.setIs_within(StringUtils.isBlank(medicalHistoryPrescriptionChineseVO.getUsageName()) ? "0" : medicalHistoryPrescriptionChineseVO.getUsageName().contains("外用")?"1":"0");
                pdetail.setOther_pres_num(medicalHistoryPrescriptionChineseVO.getPrescriptionNo());
                // 处方特殊说明 取中医病名+证型
                List<MedicalHistoryDiagnose> medicalHistoryDiagnoses = medicalHistoryDiagnoseService.lambdaQuery()
                        .eq(MedicalHistoryDiagnose::getMedicalHistoryId, medicalHistoryId)
                        .eq(MedicalHistoryDiagnose::getDiagnoseType, MedicalHistoryConst.DIAGNOSE_TYPE_CHINESE)
                        .orderByAsc(MedicalHistoryDiagnose::getId)
                        .list();
                DiagnoseAggregateBo diagnoseAggregateBo = medicalHistoryDiagnoseService.diagnoseAggregate(medicalHistoryDiagnoses);
                pdetail.setSpecial_instru(diagnoseAggregateBo.getDiagnosis());
                pdetail.setDoctor(medicalHistory.getEmployeeName());
                pdetail.setMoney(medicalHistoryPrescriptionChineseVO.getTotalMoney().stripTrailingZeros().toPlainString());
                pdetail.setMedication_instruction(
                        com.wjj.application.common.util.StringUtils.joinNoNeedEmpty(Arrays.asList(medicalHistoryPrescriptionChineseVO.getUsageName(), medicalHistoryPrescriptionChineseVO.getFrequencyName()), ",", "")
                );
                //发票相关 0 不需要 1 需要
                pdetail.setIs_invoice("0");
                if(CollectionUtils.isNotEmpty(medicalHistoryPrescriptionChineseVO.getMedicalHistoryPrescriptionChineseItemDTOList())) {
                    List<Xq> medici_xq = new LinkedList<>();
                    for (MedicalHistoryPrescriptionChineseListVO medicalHistoryPrescriptionChineseListVO : medicalHistoryPrescriptionChineseVO.getMedicalHistoryPrescriptionChineseItemDTOList()) {
                        Xq xq = new Xq();
                        ItemVO itemVO = warehousingOrderService.queryItemByItemCode(medicalHistoryPrescriptionChineseListVO.getHerbalMedicineItemCode(),
                                MedicalHistoryConst.MEDICINAL_TYPE_CHINESE_HERBAL_MEDICINE.equals(medicalHistoryPrescriptionChineseListVO.getMedicinalType()) ? 1 :
                                        (MedicalHistoryConst.MEDICINAL_TYPE_GRANULES.equals(medicalHistoryPrescriptionChineseListVO.getMedicinalType()) ? 4 :
                                                (MedicalHistoryConst.MEDICINAL_TYPE_REFINED_SLICES.equals(medicalHistoryPrescriptionChineseListVO.getMedicinalType()) ? 5 : 1))

                        );
                        if(itemVO == null){
                            log.error("同步康美订单失败, 药品数据被删除 itemCode:{}", medicalHistoryPrescriptionChineseListVO.getHerbalMedicineItemCode());
                            throw new CommonException(SaasReturnCode.DATA_NOT_EXIST.getCode(), "药品数据被删除");
                        }
                        xq.setMedicines(itemVO.getItemName());
                        xq.setDose(medicalHistoryPrescriptionChineseListVO.getOnceQuantity().stripTrailingZeros().toPlainString());
                        xq.setUnit(medicalHistoryPrescriptionChineseListVO.getSalesUnit());
                        xq.setHerb_unit_price(medicalHistoryPrescriptionChineseListVO.getSalesUnitPriceRaw().stripTrailingZeros().toPlainString());
                        xq.setGoods_num(itemVO.getRemark());
                        xq.setM_usage(medicalHistoryPrescriptionChineseListVO.getSpecialUsageName());
                        medici_xq.add(xq);
                    }
                    pdetail.setMedici_xq(medici_xq);
                }
                pDetailList.add(pdetail);
            }
            data.setPrescript(pDetailList);
            orderInfo.setData(data);
            OrderInfo1 saveOrderInfo = kangMeiUtil.saveOrderInfo(orderInfo);
            // 写到病历表
            medicalHistoryService.lambdaUpdate()
                    .eq(MedicalHistory::getId, medicalHistoryId)
                    .set(MedicalHistory::getCloudPharmacyOrderId, saveOrderInfo.getOrderid())
                    .set(MedicalHistory::getCloudPharmacyPrescriptionIds, saveOrderInfo.getPrescriptionIds())
                    .update();
            // 写到处方表
            medicalHistoryPrescriptionService.lambdaUpdate()
                    .eq(MedicalHistoryPrescription::getMedicalHistoryId, medicalHistoryId)
                    .eq(MedicalHistoryPrescription::getStatus, MedicalHistoryConst.PRESCRIPTION_STATUS_PAYED)
                    .set(MedicalHistoryPrescription::getCloudPharmacyOrderId, saveOrderInfo.getOrderid())
                    .update();
            // 写到发药单表
            dispensingService.lambdaUpdate()
                    .eq(Dispensing::getMedicalHistoryId, medicalHistoryId)
                    .eq(Dispensing::getCloudPharmacySyncStatus, DispensingConst.CLOUD_PHARMACY_SYNC_STATUS_ING)
                    .set(Dispensing::getCloudPharmacySyncStatus, DispensingConst.CLOUD_PHARMACY_SYNC_STATUS_DONE)
                    .set(Dispensing::getCloudPharmacyOrderId, saveOrderInfo.getOrderid())
                    .update();
        }catch (Exception e){
            // 异常还原发药单同步状态
            dispensingService.lambdaUpdate()
                    .eq(Dispensing::getMedicalHistoryId, medicalHistoryId)
                    .eq(Dispensing::getCloudPharmacySyncStatus, DispensingConst.CLOUD_PHARMACY_SYNC_STATUS_ING)
                    .set(Dispensing::getCloudPharmacySyncStatus, DispensingConst.CLOUD_PHARMACY_SYNC_STATUS_FAIL)
                    .update();
            if(e instanceof CommonException){
                throw (CommonException) e;
            }else{
                log.error("同步康美订单错误, orderInfo:{}", JSON.toJSONString(orderInfo));
                log.error("同步康美订单错误.", e);
                throw new CommonException(ReturnCode.BIZ_FAIL.getCode(), "同步失败，请重新尝试");
            }
        }
    }
}
