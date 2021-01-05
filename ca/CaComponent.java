package com.wjj.application.facade.ca;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.wjj.application.bo.patient.PatientFileExtendFullBo;
import com.wjj.application.common.enums.*;
import com.wjj.application.common.tuple.Tuple;
import com.wjj.application.constant.consultation.ConsultationBillConst;
import com.wjj.application.constant.medicalhistory.MedicalHistoryConst;
import com.wjj.application.constant.system.SysUserConst;
import com.wjj.application.dto.MessageSmsCommonDTO;
import com.wjj.application.dto.conversation.ConversationRecordDTO;
import com.wjj.application.dto.medicalhistory.PrescriptionPharmacistAuditDTO;
import com.wjj.application.entity.ca.CaSignCallBackInfo;
import com.wjj.application.entity.consultation.ConsultationBill;
import com.wjj.application.entity.employee.Employee;
import com.wjj.application.entity.medicalhistory.MedicalHistory;
import com.wjj.application.entity.medicalhistory.MedicalHistoryPrescription;
import com.wjj.application.entity.medicalhistory.MedicalHistoryPrescriptionChineseList;
import com.wjj.application.entity.medicalhistory.MedicalHistoryPrescriptionGenericList;
import com.wjj.application.entity.patient.PatientFileExtend;
import com.wjj.application.entity.user.SysUser;
import com.wjj.application.eventcenter.event.medicalhistory.MedicalHistorySubmitDoneEvent;
import com.wjj.application.exception.CommonException;
import com.wjj.application.facade.ca.casdk.CaFeignClient;
import com.wjj.application.facade.ca.casdk.common.CaConst;
import com.wjj.application.facade.ca.casdk.config.CaConfig;
import com.wjj.application.facade.ca.casdk.util.CaUtils;
import com.wjj.application.facade.ca.casdk.vo.in.BaseIn;
import com.wjj.application.facade.ca.casdk.vo.in.SelfSignGetGrantResultIn;
import com.wjj.application.facade.ca.casdk.vo.in.callback.AutoSignCallbackIn;
import com.wjj.application.facade.ca.casdk.vo.in.callback.SignCallbackIn;
import com.wjj.application.facade.ca.casdk.vo.in.recipesyn.RecipeIn;
import com.wjj.application.facade.ca.casdk.vo.in.recipesyn.RecipeInfoIn;
import com.wjj.application.facade.ca.casdk.vo.in.recipesyn.RecipeSynIn;
import com.wjj.application.facade.ca.casdk.vo.out.RecipeSynOut;
import com.wjj.application.facade.ca.casdk.vo.out.SelfSignGetGrantResultOut;
import com.wjj.application.facade.ca.casdk.vo.out.callback.CallbackOut;
import com.wjj.application.facade.medicalhistory.MedicalHistoryComponent;
import com.wjj.application.feign.MessageFeignClient;
import com.wjj.application.response.ReturnCode;
import com.wjj.application.service.SysUserService;
import com.wjj.application.service.bgmanage.SaasCommonConfigurationService;
import com.wjj.application.service.ca.CaSignCallBackInfoService;
import com.wjj.application.service.consultation.ConsultationBillService;
import com.wjj.application.service.conversation.ConversationRecordService;
import com.wjj.application.service.employee.EmployeeService;
import com.wjj.application.service.im.ImConsultationContentService;
import com.wjj.application.service.medicalhistory.MedicalHistoryPrescriptionService;
import com.wjj.application.service.medicalhistory.MedicalHistoryService;
import com.wjj.application.service.patient.PatientFileService;
import com.wjj.application.util.BeanMapperUtil;
import com.wjj.application.vo.bgmanage.SaasCommonConfigurationVO;
import com.wjj.application.vo.conversation.ConverSationTemplateVO;
import com.wjj.application.vo.medicalhistory.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author hank
 * @since 2020/1/14 0014 上午 11:26
 */
@Component
@Slf4j
public class CaComponent {

    @Autowired
    private MedicalHistoryService medicalHistoryService;

    @Autowired
    private MedicalHistoryPrescriptionService medicalHistoryPrescriptionService;

    @Autowired
    private ConsultationBillService consultationBillService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private MedicalHistoryComponent medicalHistoryComponent;

    @Autowired
    private CaFeignClient caFeignClient;

    @Autowired
    private CaConfig caConfig;

    @Lazy
    @Autowired
    private PatientFileService patientFileService;

    @Autowired
    private ConversationRecordService conversationRecordService;

    @Autowired
    private ImConsultationContentService imConsultationContentService;

    @Autowired
    private CaUtils caUtils;

    @Autowired
    private MessageFeignClient messageFeignClient;

    @Autowired
    private CaSignCallBackInfoService caSignCallBackInfoService;

    @Autowired
    private SaasCommonConfigurationService saasCommonConfigurationService;


    /**
     * 同步数据到Ca
     * @param medicalHistory 病历单
     * @return
     */
    public void recipeSynToCa(MedicalHistory medicalHistory, Integer platType) {

        // 没有提交的病历,或者不是视频问诊
        if(
            // 1.没有病历
            medicalHistory == null || !MedicalHistoryConst.MEDICAL_HISTORY_STATUS_SUBMIT.equals(medicalHistory.getStatus())
            // 2.不是视频问诊
            || !ConsultationBillConst.CONSULTATION_WAY_REMOTE.equals(medicalHistory.getConsultationWay())
        ){
            return;
        }
        // 没有处方
        int submitCount = medicalHistoryPrescriptionService.getCountByMedicalHistoryId(medicalHistory.getId(), Collections.singletonList(MedicalHistoryConst.PRESCRIPTION_STATUS_SUBMIT));
        if(submitCount < 1){
            return;
        }

        // 取全量数据
        MedicalHistoryFullVO medicalHistoryFullInfo = medicalHistoryComponent.getFullInfoByMedicalHistoryId(medicalHistory.getId(), false);

        // 同步的处方单号
        String caUrIdNo = medicalHistoryPrescriptionService.createCaUrIdNo();
        Employee employee = employeeService.getById(medicalHistoryFullInfo.getMedicalHistoryVO().getEmployeeId());
        // 数据同步
        RecipeSynIn<RecipeIn> recipeRecipeSynIn = getRecipeSynCommonInfo(medicalHistoryFullInfo.getMedicalHistoryVO(), PlatTypeEnum.PLAT_TYPE_DOC_APP.getType().equals(platType)? "1": "2", employee.getUserId());
        RecipeIn recipeIn = recipeRecipeSynIn.getMsg().getBody();
        recipeIn.setUrId(caUrIdNo);
        recipeIn.setSubject("处方单");
        // 同步的药品信息
        List<RecipeInfoIn> recipeInfo = new LinkedList<>();
        // 同步的处方单id集合
        List<Long> syncPrescriptionIds = new LinkedList<>();
        if (CollectionUtils.isNotEmpty(medicalHistoryFullInfo.getMedicalHistoryPrescriptionChineseVOList())) {
            for (MedicalHistoryPrescriptionChineseVO medicalHistoryPrescriptionChineseVO : medicalHistoryFullInfo.getMedicalHistoryPrescriptionChineseVOList()) {
                // 必须是新提交的处方
                if(MedicalHistoryConst.PRESCRIPTION_STATUS_SUBMIT.equals(medicalHistoryPrescriptionChineseVO.getStatus())) {
                    syncPrescriptionIds.add(medicalHistoryPrescriptionChineseVO.getId());
                    String nowBillingTime = CaConst.DATE_FORMAT.format(medicalHistoryPrescriptionChineseVO.getBillingTime());
                    // 取最大开单时间
                    if(recipeIn.getRecipeTime() == null ||  recipeIn.getRecipeTime().compareTo(nowBillingTime) < 0) {
                        recipeIn.setRecipeTime(nowBillingTime);
                    }
                    if(CollectionUtils.isNotEmpty(medicalHistoryPrescriptionChineseVO.getMedicalHistoryPrescriptionChineseItemDTOList())) {
                        for (MedicalHistoryPrescriptionChineseListVO medicalHistoryPrescriptionChineseListVO : medicalHistoryPrescriptionChineseVO.getMedicalHistoryPrescriptionChineseItemDTOList()) {
                            RecipeInfoIn recipeInfoIn = new RecipeInfoIn();
                            recipeInfoIn.setQuantity(medicalHistoryPrescriptionChineseListVO.getOnceQuantity().multiply(new BigDecimal(medicalHistoryPrescriptionChineseVO.getDays() * medicalHistoryPrescriptionChineseVO.getEverydayQuantity())).stripTrailingZeros().toPlainString());
                            recipeInfoIn.setStandard(medicalHistoryPrescriptionChineseListVO.getStandard());
                            recipeInfoIn.setPrice(medicalHistoryPrescriptionChineseListVO.getSalesUnitPrice().stripTrailingZeros().toPlainString());
                            recipeInfoIn.setName(medicalHistoryPrescriptionChineseListVO.getHerbalMedicineName());
                            recipeInfoIn.setDays(medicalHistoryPrescriptionChineseVO.getDays().toString());
                            recipeInfoIn.setDosage(medicalHistoryPrescriptionChineseListVO.getOnceQuantity().toPlainString());
//                        recipeInfoIn.setUnitOf(); // @菲凡 非必传就不用管了 20201224
                            recipeInfoIn.setFrequency(medicalHistoryPrescriptionChineseVO.getFrequencyName());
                            recipeInfoIn.setUnit(medicalHistoryPrescriptionChineseListVO.getSalesUnit());
                            recipeInfoIn.setUsage(medicalHistoryPrescriptionChineseVO.getUsageName());
                            recipeInfo.add(recipeInfoIn);
                        }
                    }
                }
            }
        }
        // 成药
        MedicalHistoryPrescriptionGenericVO medicalHistoryPrescriptionGenericVO = medicalHistoryFullInfo.getMedicalHistoryPrescriptionGenericVO();
        // 必须是新提交的处方
        if (medicalHistoryPrescriptionGenericVO != null && MedicalHistoryConst.PRESCRIPTION_STATUS_SUBMIT.equals(medicalHistoryPrescriptionGenericVO.getStatus())) {
            // 数据同步
            String nowBillingTime = CaConst.DATE_FORMAT.format(medicalHistoryPrescriptionGenericVO.getBillingTime());
            // 取最大开单时间
            if(recipeIn.getRecipeTime() == null ||  recipeIn.getRecipeTime().compareTo(nowBillingTime) < 0) {
                recipeIn.setRecipeTime(nowBillingTime);
            }
            syncPrescriptionIds.add(medicalHistoryPrescriptionGenericVO.getId());
            if(CollectionUtils.isNotEmpty(medicalHistoryPrescriptionGenericVO.getMedicalHistoryPrescriptionGenericListVoS())) {
                for (MedicalHistoryPrescriptionGenericListVO medicalHistoryPrescriptionGenericListVo : medicalHistoryPrescriptionGenericVO.getMedicalHistoryPrescriptionGenericListVoS()) {
                    RecipeInfoIn recipeInfoIn = new RecipeInfoIn();
                    recipeInfoIn.setQuantity(medicalHistoryPrescriptionGenericListVo.getTotalQuantity().stripTrailingZeros().toPlainString());
                    recipeInfoIn.setStandard(medicalHistoryPrescriptionGenericListVo.getStandard());
                    recipeInfoIn.setPrice(medicalHistoryPrescriptionGenericListVo.getSalesUnitPrice().stripTrailingZeros().toPlainString());
                    recipeInfoIn.setName(medicalHistoryPrescriptionGenericListVo.getPatentMedicineInfoMedicineName());
                    recipeInfoIn.setDays(medicalHistoryPrescriptionGenericListVo.getDays().toString());
                    recipeInfoIn.setDosage(medicalHistoryPrescriptionGenericListVo.getOnceQuantityExplain());
                    // recipeInfoIn.setUnitOf(); // @菲凡 非必传就不用管了 20201224
                    recipeInfoIn.setFrequency(medicalHistoryPrescriptionGenericListVo.getFrequencyName());
                    recipeInfoIn.setUnit(medicalHistoryPrescriptionGenericListVo.getSalesUnit());
                    recipeInfoIn.setUsage(medicalHistoryPrescriptionGenericListVo.getUsageName());
                    recipeInfo.add(recipeInfoIn);
                }
            }
        }
        if(CollectionUtils.isNotEmpty(syncPrescriptionIds)) {
            recipeIn.setRecipeInfo(recipeInfo);
            RecipeSynOut recipeSynOut = caFeignClient.recipeSyn(recipeRecipeSynIn);
            medicalHistoryPrescriptionService.lambdaUpdate().in(MedicalHistoryPrescription::getId, syncPrescriptionIds)
                    .set(MedicalHistoryPrescription::getCaUniqueId, recipeSynOut.getData().getUniqueId())
                    .set(MedicalHistoryPrescription::getCaUrId, caUrIdNo)
                    .set(MedicalHistoryPrescription::getCaSignOpenid, recipeIn.getOpenId())
                    .update();
        }
    }

    /**
     * 根据病历单ID查询 医生签名信息<br/>
     * @param medicalHistoryId 病历单ID
     * @return
     */
    public MedicalHistoryDoctorSignInfoVO getMedicalHistoryDoctorSignInfo(Long medicalHistoryId) {
        // 所有待签名处方
        MedicalHistoryPrescription medicalHistoryPrescription = medicalHistoryPrescriptionService.lambdaQuery()
                .eq(MedicalHistoryPrescription::getMedicalHistoryId, medicalHistoryId)
                .eq(MedicalHistoryPrescription::getStatus, MedicalHistoryConst.PRESCRIPTION_STATUS_SUBMIT)
                .ne(MedicalHistoryPrescription::getCaUniqueId, "")
                .last("limit 1")
                .one();
        if (medicalHistoryPrescription == null) {
            return new MedicalHistoryDoctorSignInfoVO(false, null);
        }
        Employee employee = employeeService.getById(medicalHistoryPrescription.getEmployeeId());
        SysUser sysUser = sysUserService.getById(employee.getUserId());
        if(StringUtils.isBlank(sysUser.getCaOpenId())){
            throw new CommonException(ReturnCode.PARAM_INVALID.getCode(), "医生没有同步到医信");
        }
        // 开启了自动签名
        if(TrueOrFalseEnum.TRUE.getType().equals(sysUser.getCaIsAutoSign())){
            // 请求Ca二次判断是否开启了
            SelfSignGetGrantResultOut selfSignGetGrantResultOut = caFeignClient.selfSignGetGrantResult(new BaseIn<>(new SelfSignGetGrantResultIn(sysUser.getCaOpenId(), null)));
            if(selfSignGetGrantResultOut.getData()){
                return new MedicalHistoryDoctorSignInfoVO(false, null);
            }
        }
        return new MedicalHistoryDoctorSignInfoVO(true, caUtils.getSignQRCodeUrl(caConfig.getSignQRCodeUrlRedirectUrl(), sysUser.getCaOpenId(), medicalHistoryPrescription.getCaUniqueId()));
    }

    /**
     * 根据病历单ID查询 医生签名状态<br/>
     * @param medicalHistoryId 病历单ID
     * @return true.全部处方已签名 false。待签名
     */
    public boolean getMedicalHistoryDoctorSignStatus(Long medicalHistoryId) {
        MedicalHistoryPrescription medicalHistoryPrescription = medicalHistoryPrescriptionService.lambdaQuery()
                .eq(MedicalHistoryPrescription::getMedicalHistoryId, medicalHistoryId)
                .eq(MedicalHistoryPrescription::getStatus, MedicalHistoryConst.PRESCRIPTION_STATUS_SUBMIT)
                .ne(MedicalHistoryPrescription::getCaUniqueId, "")
                .last("limit 1")
                .one();
        return medicalHistoryPrescription == null;
    }

    /**
     * 处方审核处方
     *  这里审核通过只同步处方到ca,真正的通过逻辑在签名成功后处理(caSignNotify)
     * @param auditDTO 请求参数
     * @return 返回结果
     */
    @Transactional
    public void pharmacistAuditPrescription(PrescriptionPharmacistAuditDTO auditDTO, Long userId) throws Exception {
        //校验处方单状态是否是药师待审核
        MedicalHistoryPrescription medicalHistoryPrescription = medicalHistoryPrescriptionService.lambdaQuery()
                .eq(MedicalHistoryPrescription::getCaPharmacistUserId, userId)
                .eq(MedicalHistoryPrescription::getStatus, MedicalHistoryConst.PRESCRIPTION_STATUS_WAIT_AUDIT)
                .eq(MedicalHistoryPrescription::getId, auditDTO.getId())
                .one();

        if(medicalHistoryPrescription == null){
            throw new CommonException(ReturnCode.RESOURCE_NOT_FOUND);
        }
        if(MedicalHistoryConst.PRESCRIPTION_STATUS_AUDIT_NOT.equals(auditDTO.getStatus())) {
            // 审核拒绝
            pharmacistAuditPrescriptionNoPass(auditDTO, userId, medicalHistoryPrescription);
        }else if(MedicalHistoryConst.PRESCRIPTION_STATUS_AUDIT_PASS.equals(auditDTO.getStatus())){
            pharmacistAuditPrescriptionPass(medicalHistoryPrescription);
        }
    }


    /**
     * 处方审核不通过
     *      * 1.更新
     *      * 1.1.校验处方单状态是否是药师待审核
     *      * 1.2.更新处方单状态为审核拒绝
     *      * 1.3.插入审核人、审核时间
     *
     *      * 2.消息提醒
     *      * 2.1.系统消息提醒 发送IM系统消息 您与【病历提交时间】给患者【患者姓名】的视频问诊【处方1】被驳回，请修改。
     *      * 2.2.短信提醒 向医生发送短信：
     * @param auditDTO 请求参数
     * @return 返回结果
     */
    private void pharmacistAuditPrescriptionNoPass(PrescriptionPharmacistAuditDTO auditDTO, Long userId, MedicalHistoryPrescription medicalHistoryPrescription) throws Exception {
        //校验处方单状态是否是药师待审核
        if (!medicalHistoryPrescriptionService.lambdaUpdate()
                .eq(MedicalHistoryPrescription::getCaPharmacistUserId, userId)
                .eq(MedicalHistoryPrescription::getStatus, MedicalHistoryConst.PRESCRIPTION_STATUS_WAIT_AUDIT)
                .eq(MedicalHistoryPrescription::getId, auditDTO.getId())
                .set(MedicalHistoryPrescription::getStatus, MedicalHistoryConst.PRESCRIPTION_STATUS_AUDIT_NOT)
                .set(MedicalHistoryPrescription::getPharmacistAuditTime, new Date())
                .set(MedicalHistoryPrescription::getPharmacistAuditReason, auditDTO.getRemark())
                .update()) {
            throw new CommonException(ReturnCode.UPDATE_FAIL.getCode(), "操作失败,请刷新后重试");
        }
        //系统消息提醒
        pharmacistAuditPrescriptionNoPassSendSystemMessage(medicalHistoryPrescription);
    }



    /**
     * 处方审核通过
     */
    private void pharmacistAuditPrescriptionPass(MedicalHistoryPrescription medicalHistoryPrescription){
        MedicalHistoryVO medicalHistoryVO = medicalHistoryComponent.getMedicalHistoryVOByMedicalHistory(medicalHistoryService.getById(medicalHistoryPrescription.getMedicalHistoryId()));
        // 待同步的数据
        RecipeSynIn<RecipeIn> recipeRecipeSynIn = getRecipeSynCommonInfo(medicalHistoryVO, "1", medicalHistoryPrescription.getCaPharmacistUserId());
        // 审核通过, 同步签名
        // 数据同步
        RecipeIn recipeIn = recipeRecipeSynIn.getMsg().getBody();
        recipeIn.setUrId(medicalHistoryPrescription.getPrescriptionNo());
        recipeIn.setSubject(medicalHistoryPrescription.getTitle());
        // 同步的药品信息
        List<RecipeInfoIn> recipeInfo = new LinkedList<>();
        // 全量处方信息
        Map<Integer, List<MedicalHistoryPrescription>> medicalHistoryPrescriptionFullList = medicalHistoryPrescriptionService.getMedicalHistoryPrescriptionFullList(Collections.singletonList(medicalHistoryPrescription));
        for (List<MedicalHistoryPrescription> medicalHistoryPrescriptions : medicalHistoryPrescriptionFullList.values()) {
            for (MedicalHistoryPrescription prescription : medicalHistoryPrescriptions) {
                // 中药
                if(PrescriptionTypeEnum.HERBAL.getType().equals(prescription.getPrescriptionType())){
                    // 必须是有效处方
                    String nowBillingTime = CaConst.DATE_FORMAT.format(medicalHistoryPrescription.getBillingTime());
                    // 取最大开单时间
                    if (recipeIn.getRecipeTime() == null || recipeIn.getRecipeTime().compareTo(nowBillingTime) < 0) {
                        recipeIn.setRecipeTime(nowBillingTime);
                    }
                    List<MedicalHistoryPrescriptionChineseList> medicalHistoryPrescriptionChineseLists = prescription.getMedicalHistoryPrescriptionChineseLists();
                    if(CollectionUtils.isNotEmpty(medicalHistoryPrescriptionChineseLists)) {
                        for (MedicalHistoryPrescriptionChineseList medicalHistoryPrescriptionChineseListVO : medicalHistoryPrescriptionChineseLists) {
                            RecipeInfoIn recipeInfoIn = new RecipeInfoIn();
                            recipeInfoIn.setQuantity(medicalHistoryPrescriptionChineseListVO.getOnceQuantity()
                                    .multiply(new BigDecimal(prescription.getMedicalHistoryPrescriptionSubjoin().getDays() * prescription.getMedicalHistoryPrescriptionSubjoin().getEverydayQuantity())).stripTrailingZeros().toPlainString());
                            recipeInfoIn.setStandard(medicalHistoryPrescriptionChineseListVO.getStandard());
                            recipeInfoIn.setPrice(medicalHistoryPrescriptionChineseListVO.getSalesUnitPrice().stripTrailingZeros().toPlainString());
                            recipeInfoIn.setName(medicalHistoryPrescriptionChineseListVO.getHerbalMedicineName());
                            recipeInfoIn.setDays(prescription.getMedicalHistoryPrescriptionSubjoin().getDays().toString());
                            recipeInfoIn.setDosage(medicalHistoryPrescriptionChineseListVO.getOnceQuantity().toPlainString());
//                        recipeInfoIn.setUnitOf(); // @菲凡 非必传就不用管了 20201224
                            recipeInfoIn.setFrequency(medicalHistoryPrescription.getMedicalHistoryPrescriptionSubjoin().getFrequencyName());
                            recipeInfoIn.setUnit(medicalHistoryPrescriptionChineseListVO.getSalesUnit());
                            recipeInfoIn.setUsage(medicalHistoryPrescription.getMedicalHistoryPrescriptionSubjoin().getUsageName());
                            recipeInfo.add(recipeInfoIn);
                        }
                    }
                }else if(PrescriptionTypeEnum.PATENT.getType().equals(prescription.getPrescriptionType())){
                    // 数据同步
                    String nowBillingTime = CaConst.DATE_FORMAT.format(prescription.getBillingTime());
                    // 取最大开单时间
                    if(recipeIn.getRecipeTime() == null ||  recipeIn.getRecipeTime().compareTo(nowBillingTime) < 0) {
                        recipeIn.setRecipeTime(nowBillingTime);
                    }
                    if(CollectionUtils.isNotEmpty(prescription.getMedicalHistoryPrescriptionGenericLists())) {
                        for (MedicalHistoryPrescriptionGenericList medicalHistoryPrescriptionGenericListVo : prescription.getMedicalHistoryPrescriptionGenericLists()) {
                            RecipeInfoIn recipeInfoIn = new RecipeInfoIn();
                            recipeInfoIn.setQuantity(medicalHistoryPrescriptionGenericListVo.getTotalQuantity().stripTrailingZeros().toPlainString());
                            recipeInfoIn.setStandard(medicalHistoryPrescriptionGenericListVo.getStandard());
                            recipeInfoIn.setPrice(medicalHistoryPrescriptionGenericListVo.getSalesUnitPrice().stripTrailingZeros().toPlainString());
                            recipeInfoIn.setName(medicalHistoryPrescriptionGenericListVo.getPatentMedicineInfoMedicineName());
                            recipeInfoIn.setDays(medicalHistoryPrescriptionGenericListVo.getDays().toString());
                            recipeInfoIn.setDosage(medicalHistoryPrescriptionGenericListVo.getOnceQuantityExplain());
                            // recipeInfoIn.setUnitOf(); // @菲凡 非必传就不用管了 20201224
                            recipeInfoIn.setFrequency(medicalHistoryPrescriptionGenericListVo.getFrequencyName());
                            recipeInfoIn.setUnit(medicalHistoryPrescriptionGenericListVo.getSalesUnit());
                            recipeInfoIn.setUsage(medicalHistoryPrescriptionGenericListVo.getUsageName());
                            recipeInfo.add(recipeInfoIn);
                        }
                    }
                }
            }
        }
        // 同步数据到ca
        recipeIn.setRecipeInfo(recipeInfo);
        RecipeSynOut recipeSynOut = caFeignClient.recipeSyn(recipeRecipeSynIn);
        medicalHistoryPrescriptionService.lambdaUpdate().in(MedicalHistoryPrescription::getId, medicalHistoryPrescription.getId())
                .set(MedicalHistoryPrescription::getCaPharmacistUniqueId, recipeSynOut.getData().getUniqueId())
                .update();
    }
    /**
     * 签名成功回调
     * @param signCallbackIn
     * @return
     */
    public CallbackOut caSignNotify(SignCallbackIn signCallbackIn) throws CommonException{
        log.info("caSignNotify signCallbackIn:{}", signCallbackIn);
        if(!caUtils.checkSign(signCallbackIn.getBody(), signCallbackIn.getHead().getSign())){
            log.error("ca caSignNotify fail 验签失败, signCallbackIn:{}", JSON.toJSONString(signCallbackIn));
            return CallbackOut.fail("验签失败");
        }
        //签名成功， 这里只处理意思签名
        if (CaConst.SIGN_CALL_BACK_STATUS_SUCCESS.equals(signCallbackIn.getBody().getStatus())) {
            // 写入记录表
            CaSignCallBackInfo caSignCallBackInfo = BeanMapperUtil.map(signCallbackIn, CaSignCallBackInfo.class);
            caSignCallBackInfoService.save(caSignCallBackInfo);

            String uniqueId = signCallbackIn.getBody().getUniqueId();
            String openId = signCallbackIn.getBody().getOpenId();
            // 获得医生
            SysUser sysUser = sysUserService.lambdaQuery()
                    .eq(SysUser::getCaOpenId, openId)
                    .eq(SysUser::getAuthStatus, UserAuthStatusEnum.AUTHENTICATED.getType())
                    .orderByDesc(SysUser::getId)
                    .last("limit 1")
                    .one();
            if(sysUser == null){
                log.error("ca caSignNotify fail 系统中不存在当前openid,对应的医生, signCallbackIn:{}", JSON.toJSONString(signCallbackIn));
                return CallbackOut.fail("系统中不存在当前openid,对应的医生");
            }
            // 医生签名的处方列表
            List<MedicalHistoryPrescription> docSignPrescriptionList = medicalHistoryPrescriptionService.lambdaQuery()
                    .eq(MedicalHistoryPrescription::getCaUniqueId, uniqueId)
                    .eq(MedicalHistoryPrescription::getCaSignOpenid, openId)
                    .eq(MedicalHistoryPrescription::getStatus, MedicalHistoryConst.PRESCRIPTION_STATUS_SUBMIT)
                    .list();
            // 1. 是否是医生签名
            if (CollectionUtils.isNotEmpty(docSignPrescriptionList)) {
                long countHerbal = docSignPrescriptionList.stream().filter(medicalHistoryPrescription -> PrescriptionTypeEnum.HERBAL.getType().equals(medicalHistoryPrescription.getPrescriptionType())).count();
                // 中西医随机药师
                Iterator<SysUser> assignPharmacistHerbalIterator = null, assignPharmacistPatentIterator = null;
                List<SysUser> assignPharmacistHerbal = null, assignPharmacistPatent = null;
                // 中药
                if(countHerbal > 0){
                    assignPharmacistHerbal = getAssignPharmacist((int) countHerbal, PrescriptionTypeEnum.HERBAL.getType());
                    if(CollectionUtils.isNotEmpty(assignPharmacistHerbal)){
                        assignPharmacistHerbalIterator = assignPharmacistHerbal.iterator();
                    }
                }
                // 西药
                long countPatent = docSignPrescriptionList.stream().filter(medicalHistoryPrescription -> PrescriptionTypeEnum.PATENT.getType().equals(medicalHistoryPrescription.getPrescriptionType())).count();
                if(countPatent > 0){
                    assignPharmacistPatent = getAssignPharmacist((int) countPatent, PrescriptionTypeEnum.PATENT.getType());
                    if(CollectionUtils.isNotEmpty(assignPharmacistPatent)){
                        assignPharmacistPatentIterator = assignPharmacistPatent.iterator();
                    }
                }
                // 更新处方
                for (MedicalHistoryPrescription medicalHistoryPrescription : docSignPrescriptionList) {
                    LambdaUpdateChainWrapper<MedicalHistoryPrescription> prescriptionLambdaUpdateChainWrapper = medicalHistoryPrescriptionService.lambdaUpdate()
                            .eq(MedicalHistoryPrescription::getCaUniqueId, uniqueId)
                            .eq(MedicalHistoryPrescription::getCaSignOpenid, openId)
                            .eq(MedicalHistoryPrescription::getId, medicalHistoryPrescription.getId())
                            .eq(MedicalHistoryPrescription::getStatus, MedicalHistoryConst.PRESCRIPTION_STATUS_SUBMIT)
                            .set(MedicalHistoryPrescription::getCaSignImg, sysUser.getCaSignImg())
                            // 更新药师审核状态,处方状态
                            .set(MedicalHistoryPrescription::getPharmacistAuditStatus, MedicalHistoryConst.PRESCRIPTION_STATUS_WAIT_AUDIT)
                            .set(MedicalHistoryPrescription::getStatus, MedicalHistoryConst.PRESCRIPTION_STATUS_WAIT_AUDIT);
                    // 分配的药师
                    SysUser assignPharmacistSysUser = null;
                    // 中医
                    if(PrescriptionTypeEnum.HERBAL.getType().equals(medicalHistoryPrescription.getPrescriptionType()) && assignPharmacistHerbalIterator != null){
                        // 获取药师
                        if (!assignPharmacistHerbalIterator.hasNext()) {
                            // 重置 assignPharmacistHerbalIterator
                            assignPharmacistHerbalIterator = assignPharmacistHerbal.iterator();
                        }
                        assignPharmacistSysUser = assignPharmacistHerbalIterator.next();

                    }
                    // 西医
                    else if (PrescriptionTypeEnum.PATENT.getType().equals(medicalHistoryPrescription.getPrescriptionType()) && assignPharmacistPatentIterator != null){
                        // 获取药师
                        if (!assignPharmacistPatentIterator.hasNext()) {
                            // 重置 assignPharmacistHerbalIterator
                            assignPharmacistPatentIterator = assignPharmacistPatent.iterator();
                        }
                        assignPharmacistSysUser = assignPharmacistPatentIterator.next();
                    }
                    // 分配的药师,写入到updateWrapper
                    if(assignPharmacistSysUser != null){
                        prescriptionLambdaUpdateChainWrapper
                                .set(MedicalHistoryPrescription::getCaPharmacistSignOpenid, assignPharmacistSysUser.getCaOpenId())
                                .set(MedicalHistoryPrescription::getCaPharmacistUserId, assignPharmacistSysUser.getId())
                                .set(MedicalHistoryPrescription::getCaPharmacistUserName, assignPharmacistSysUser.getUserName())
                                .set(MedicalHistoryPrescription::getPharmacistAssignTime, new Date());
                        SaasCommonConfigurationVO saasCommonConfigurationVO = saasCommonConfigurationService.querySaasCommonConfiguration();
                        // 自动审核开启
                        if(saasCommonConfigurationVO != null && (new Integer(10)).equals(saasCommonConfigurationVO.getPharmacistAutoAuditConfig())){
                            // 开启了自动签名
                            if(TrueOrFalseEnum.TRUE.getType().equals(assignPharmacistSysUser.getCaIsAutoSign())){
                                // 请求Ca二次判断是否开启了
                                SelfSignGetGrantResultOut selfSignGetGrantResultOut = caFeignClient.selfSignGetGrantResult(new BaseIn<>(new SelfSignGetGrantResultIn(sysUser.getCaOpenId(), null)));
                                if(selfSignGetGrantResultOut.getData()){
                                    // 自动审核通过,提交数据到ca自动签名
                                    pharmacistAuditPrescriptionPass(medicalHistoryPrescription);
                                }
                            }
                        }
                    }
                    prescriptionLambdaUpdateChainWrapper.update();
                    MedicalHistory medicalHistory = medicalHistoryService.getById(docSignPrescriptionList.get(0).getMedicalHistoryId());
                    // 1.提交完成处理
                    medicalHistoryService.submitDoneProcess(medicalHistory);
                    // 2. 生成收费单(统一在: 重新提交, 药师审核完成, 废弃处方的地方激发)
                    // 3.事务提交完发送提交完成事件
                    TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                        @Override
                        public void afterCommit() {
                            MedicalHistorySubmitDoneEvent.publishEvent(Tuple.tuple(medicalHistory.getCreateTime().equals(medicalHistory.getUpdateTime()), medicalHistory, true));
                        }
                    });
                }
            }else{
                // 药师签名的处方列表
                List<MedicalHistoryPrescription> pharmacistSignPrescriptionList = medicalHistoryPrescriptionService.lambdaQuery()
                        .eq(MedicalHistoryPrescription::getCaPharmacistUniqueId, uniqueId)
                        .eq(MedicalHistoryPrescription::getCaPharmacistSignOpenid, openId)
                        .eq(MedicalHistoryPrescription::getStatus, MedicalHistoryConst.PRESCRIPTION_STATUS_WAIT_AUDIT)
                        .list();
                // 2. 更新失败代表不是医生签名， 查询是否药师签名
                if(medicalHistoryPrescriptionService.lambdaUpdate()
                        .eq(MedicalHistoryPrescription::getCaPharmacistUniqueId, uniqueId)
                        .eq(MedicalHistoryPrescription::getCaPharmacistSignOpenid, openId)
                        .eq(MedicalHistoryPrescription::getStatus, MedicalHistoryConst.PRESCRIPTION_STATUS_WAIT_AUDIT)
                        // 更新药师审核状态,处方状态
                        .set(MedicalHistoryPrescription::getPharmacistAuditStatus, MedicalHistoryConst.PRESCRIPTION_STATUS_AUDIT_PASS)
                        .set(MedicalHistoryPrescription::getStatus, MedicalHistoryConst.PRESCRIPTION_STATUS_AUDIT_PASS)
                        .set(MedicalHistoryPrescription::getCaPharmacistSignImg, sysUser.getCaSignImg())
                        .update()){
                    // 自动判断是否需要生成收费单
                    medicalHistoryService.generateChargeContent(
                            medicalHistoryService.getById(pharmacistSignPrescriptionList.get(0).getMedicalHistoryId()),
                            null,
                            null,
                            null,
                            null,
                            null
                    );
                }
            }
        }
        return CallbackOut.success();
    }

    public CallbackOut autoCaSignNotify(AutoSignCallbackIn autoSignCallbackIn){
        if(!caUtils.checkSign(autoSignCallbackIn.getBody(), autoSignCallbackIn.getHead().getSign())){
            log.error("ca autoCaSignNotify fail 验签失败, autoSignCallbackIn:{}", JSON.toJSONString(autoSignCallbackIn));
            return CallbackOut.fail("验签失败");
        }
        sysUserService.lambdaUpdate()
                .eq(SysUser::getCaOpenId, autoSignCallbackIn.getBody().getOpenId())
                .set(SysUser::getCaAuthStatus, Boolean.TRUE.equals(autoSignCallbackIn.getBody().getResult()) ? TrueOrFalseEnum.TRUE.getType() :  TrueOrFalseEnum.FALSE.getType())
                .update();
        return CallbackOut.success();
    }

    /**
     * 获取分配的药师
     * // 处方随机分配，分配药师必须要已认证CA的。优先分配在线药师，若无在线药师，则随机分配给离线药师。分配给药师后，若1分钟内未审核处方，则短信提醒；（在线——药师登录状态就算“在线”。反之“离线”。session半小时失效规则作废）
     * @param prescriptionCount 需要的处方个数,这里返回的个数与这个参数不一定相等
     * @param type 1.中医处方 2.成药
     * @return 分配的药师
     */
    private List<SysUser> getAssignPharmacist(int prescriptionCount, int type){
        // 在线的随机
        List<SysUser> assignPharmacist = sysUserService.getAssignPharmacist(prescriptionCount, type, Arrays.asList(SysUserConst.ONLINE, SysUserConst.BUSY));
        if(CollectionUtils.isNotEmpty(assignPharmacist)){
            return assignPharmacist;
        }
        // 所有的随机
        return sysUserService.getAssignPharmacist(prescriptionCount, type, null);
    }

    /**
     * 药师审核失败发短信
     * @param prescriptionById
     * @throws Exception
     */
    private void pharmacistAuditPrescriptionNoPassSendSystemMessage(MedicalHistoryPrescription prescriptionById) throws Exception {
        //发送IM系统消息
        MedicalHistory medicalHistoryById = medicalHistoryService.getById(prescriptionById.getMedicalHistoryId());
        ConsultationBill byId = consultationBillService.getById(medicalHistoryById.getConsultationBillId());
        PatientFileExtend extend = patientFileService.getExtendByTenantIdAndPatientId(byId.getPatientId(), byId.getTenantId());
        Employee doctor = employeeService.getById(byId.getDoctorId());
        Map<String, Object> imArgsMap = new HashMap<>();
        HashMap<String, String> extraParameter = new HashMap<>();
        String medicalHistoryDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        extraParameter.put("type", SystemMessageTriggerConditionEnum.PRESCRIPTION_AUDIT_REMIND.getCode().toString());
        imArgsMap.put("medicalHistoryDate", medicalHistoryDate);
        imArgsMap.put("patientName", extend.getPatientName());
        imArgsMap.put("title", prescriptionById.getTitle());
        extraParameter.put("doctorId", doctor.getId().toString());
        extraParameter.put("sd", byId.getId().toString());
        ConversationRecordDTO systemConversationRecordDTO = imConsultationContentService.getSystemConversationRecordDTO(byId.getTenantId(), doctor, ConverSationTemplateVO.PRESCRIPTION_AUDIT_REMIND, imArgsMap, extraParameter);
        log.info("处方审核不通过系统消息提醒REQUEST: {}", JSON.toJSONString(systemConversationRecordDTO));
        conversationRecordService.systemSendMessage(systemConversationRecordDTO);
        // 发送短信
        MessageSmsCommonDTO request = new MessageSmsCommonDTO();
        request.setPhoneNo(doctor.getPhoneNo());
        request.setTemplateCode("1079");
        request.setExtend(medicalHistoryDate + ";" + extend.getPatientName()+ ";" + prescriptionById.getTitle());
        log.info("处方审核不通过短信提醒REQUEST: {}", JSON.toJSONString(request));
        com.wjj.application.response.Response response = messageFeignClient.sendSms(request);
        if (!response.isSuccess()) {
            log.error("send message for sendsyStemMessageByAudit fail, param:{},reason:{}", request, response.getMessage());
        }
    }

    /**
     *
     * @param medicalHistoryVO
     * @param signType
     *      * 签名方式
     *      * 0：推送签名（仅限推送到医网信APP）
     *      * 1：SDK集成签名
     *      * 2：PC二维码签名
     * @param userId 签名人userId
     * @return
     */
    private RecipeSynIn<RecipeIn> getRecipeSynCommonInfo(MedicalHistoryVO medicalHistoryVO, String signType, Long userId){
        SysUser sysUser = sysUserService.getById(userId);
        RecipeSynIn<RecipeIn> recipeRecipeSynIn = new RecipeSynIn<>();
        recipeRecipeSynIn.setSignType(signType);
        recipeRecipeSynIn.getMsg().getHead().setTemplateId("hash_005");
        RecipeIn recipeIn = new RecipeIn();
        recipeIn.setRecipeTerm("1");
        recipeIn.setPatientAge(medicalHistoryVO.getPatientAge().toString());
        PatientFileExtendFullBo patientFileExtendFullBo = patientFileService.getExtendFullByTenantIdAndPatientId(
                medicalHistoryVO.getTenantId(),
                medicalHistoryVO.getPatientFileId()
        );
        // 诊断
        StringBuilder diagnose = new StringBuilder("");
        if (CollectionUtils.isNotEmpty(medicalHistoryVO.getMedicalHistoryDescribeDTO().getChineseDiagnoseDTOList())) {
            for (MedicalHistoryDiagnoseVO medicalHistoryDiagnoseVO : medicalHistoryVO.getMedicalHistoryDescribeDTO().getChineseDiagnoseDTOList()) {
                diagnose.append(",");
                diagnose.append(medicalHistoryDiagnoseVO.getDiseaseName());
                if(StringUtils.isNotBlank(medicalHistoryDiagnoseVO.getSyndromeTcmName())){
                    diagnose.append(",").append(medicalHistoryDiagnoseVO.getSyndromeTcmName());
                }
            }
        }
        if (CollectionUtils.isNotEmpty(medicalHistoryVO.getMedicalHistoryDescribeDTO().getWesternDiagnoseDTOList())) {
            for (MedicalHistoryDiagnoseVO medicalHistoryDiagnoseVO : medicalHistoryVO.getMedicalHistoryDescribeDTO().getChineseDiagnoseDTOList()) {
                diagnose.append(",").append(medicalHistoryDiagnoseVO.getDiseaseName());
            }
        }
        recipeIn.setDiagnose(diagnose.length() > 0 ? diagnose.substring(1) : "");

        // 证件号
        if (StringUtils.isNotBlank(patientFileExtendFullBo.getIdentityCard())){
            recipeIn.setPatientCard(patientFileExtendFullBo.getIdentityCard());
            recipeIn.setPatientCardType("SF");
        }else{
            recipeIn.setPatientCard("");
            recipeIn.setPatientCardType("QT");
        }
        recipeIn.setPatientName(patientFileExtendFullBo.getPatientName());
        recipeIn.setPathogeny(medicalHistoryVO.getMedicalHistoryDescribeDTO().getChiefComplaint());
        recipeIn.setOpenId(sysUser.getCaOpenId());
        recipeIn.setPatientSex(
                GenderEnum.MAN.getType().equals(medicalHistoryVO.getSex()) ? "男" :
                        (GenderEnum.FEMALE.getType().equals(medicalHistoryVO.getSex()) ? "女" :
                                "未知"));
        recipeRecipeSynIn.getMsg().setBody(recipeIn);
        return recipeRecipeSynIn;
    }
}
