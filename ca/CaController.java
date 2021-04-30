package com.wjj.application.controller.saas.ca;


import com.alibaba.fastjson.JSON;
import com.wjj.application.facade.ca.CaComponent;
import com.wjj.application.facade.ca.casdk.common.code.ResCode;
import com.wjj.application.facade.ca.casdk.vo.in.callback.AutoSignCallbackIn;
import com.wjj.application.facade.ca.casdk.vo.in.callback.DoctorStatusCallbackIn;
import com.wjj.application.facade.ca.casdk.vo.in.callback.SignCallbackIn;
import com.wjj.application.facade.ca.casdk.vo.out.callback.CallbackOut;
import com.wjj.application.service.employee.QualificationAuthService;
import com.wjj.application.util.ContextUtil;
import com.wjj.application.util.SpringUtils;
import com.wjj.application.vo.Response;
import com.wjj.application.vo.ca.SelfSignRequestInVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * CA
 * </p>
 *
 * @author hank
 */
@Api(value = "CA相关", tags = "CA相关")
@RestController
@RequestMapping("/ca")
@Slf4j
public class CaController {

    @Autowired
    private CaComponent caComponent;

    @Autowired
    private QualificationAuthService qualificationAuthService;

    @Autowired
    private SpringUtils springUtils;


    /**
     * 签名状态通知接口
     * @param signCallbackIn
     * @return
     */
    @PostMapping("/sign/notify/wjjGmOpenToken")
    public CallbackOut caSignNotify(@RequestBody SignCallbackIn signCallbackIn){
//        // 获取输入流
//        BufferedReader streamReader = new BufferedReader(new InputStreamReader(httpServletRequest.getInputStream(), "UTF-8"));
//
//        // 数据写入Stringbuilder
//        StringBuilder sb = new StringBuilder();
//        String line = null;
//        while ((line = streamReader.readLine()) != null) {
//            sb.append(line);
//        }
//        System.out.println(sb.toString());
        CallbackOut callbackOut = null;
        try {
            callbackOut = caComponent.caSignNotify(signCallbackIn);
        }catch (Exception e){
            log.error("ca 签名通知处理失败 signCallbackIn:{}", JSON.toJSONString(signCallbackIn));
            log.error("ca 签名通知处理失败", e);
            return CallbackOut.fail(e.getMessage());
        }
        // 自动判断非正式环境,二次转发
        CallbackOut twiceCallbackOut = nonProdTwiceCaSignNotify(callbackOut, signCallbackIn);
        return twiceCallbackOut == null ? callbackOut : twiceCallbackOut;
    }

    /**
     * 医师状态通知接口
     * @param doctorStatusCallbackIn
     * @return
     */
    @PostMapping("/doctor/sync/notify/wjjGmOpenToken")
    public CallbackOut caDoctorAuditNotify(@RequestBody DoctorStatusCallbackIn doctorStatusCallbackIn){
        try {
            CallbackOut callbackOut = qualificationAuthService.caDoctorAuditNotify(doctorStatusCallbackIn);
            // 自动判断非正式环境,二次转发
            CallbackOut twiceCallbackOut = nonProdTwiceCaDoctorAuditNotify(callbackOut, doctorStatusCallbackIn);
            return twiceCallbackOut == null ? callbackOut : twiceCallbackOut;
        } catch (Exception e) {
            log.error("ca 医师状态通知处理失败 doctorStatusCallbackIn:{}", JSON.toJSONString(doctorStatusCallbackIn));
            log.error("ca 医师状态通知处理失败", e);
            return CallbackOut.fail(e.getMessage());
        }
    }

    /**
     * 自动签名授权-请求接口
     * @param selfSignRequestInVO
     * @return
     */
    @PostMapping("/self/sign/Request")
    @ApiOperation(value = "CA自动签名设置")
    public Response<?> caSelfSignRequest(@RequestBody @Validated SelfSignRequestInVO selfSignRequestInVO){
        caComponent.caSelfSignRequest(selfSignRequestInVO, ContextUtil.getUserId());
        return Response.ok();
    }

    /**
     * 医生签名,自动判断非正式环境,二次转发
     * @param callbackOut
     * @param signCallbackIn
     * @return 未处理返回null, 处理了返回二次的CallbackOut
     */
    private CallbackOut nonProdTwiceCaSignNotify(CallbackOut callbackOut, SignCallbackIn signCallbackIn){
        // 不成功并且 不是正式环境并且 不是第二次, 转发
        if(
            // 不是正式环境
            !springUtils.isProd()
            // 不成功
            && callbackOut != null && !ResCode.SUCCESS.getStatus().equals(callbackOut.getStatus())
            // 不是第二次
            && !signCallbackIn.getIsTwice()
        ){
            // 转发 到uat
            try {
                log.info("caSignNotify once callbackOut:{}", callbackOut);
                RestTemplate restTemplate = new RestTemplate();
                signCallbackIn.setIsTwice(true);
                ResponseEntity<CallbackOut> objectResponseEntity = restTemplate
                        .postForEntity("https://tcm-saas-uat.nhf.cn/api/wjj-saas-system/ca/sign/notify/wjjGmOpenToken", signCallbackIn, CallbackOut.class);
                return objectResponseEntity.getBody();
            }catch (Exception e){
                log.error("caSignNotify twice error.", e);
            }
        }
        return null;
    }
    /**
     * 医生状态通知,自动判断非正式环境,二次转发
     * !这里不管第一次成功与否都会转发一次
     * @param callbackOut
     * @param doctorStatusCallbackIn
     * @return 处理过并且第一次不是成功的情况,返回二次的CallbackOut否者返回null
     */
    private CallbackOut nonProdTwiceCaDoctorAuditNotify(CallbackOut callbackOut, DoctorStatusCallbackIn doctorStatusCallbackIn){
        // 不是正式环境并且 不是第二次, 转发
        if(
            // 不是正式环境
            !springUtils.isProd()
            // 不是第二次
            && !doctorStatusCallbackIn.getIsTwice()
        ){
            // 转发 到uat
            try {
                log.info("caDoctorAuditNotify once callbackOut:{}", callbackOut);
                RestTemplate restTemplate = new RestTemplate();
                doctorStatusCallbackIn.setIsTwice(true);
                ResponseEntity<CallbackOut> objectResponseEntity = restTemplate
                        .postForEntity("https://tcm-saas-uat.nhf.cn/api/wjj-saas-system/ca/doctor/sync/notify/wjjGmOpenToken", doctorStatusCallbackIn, CallbackOut.class);
                CallbackOut twiceCallbackOut = objectResponseEntity.getBody();
                // 这里如果第一次不是成功状态,才返回,否者以第一次的返回为准
                if(callbackOut == null || !ResCode.SUCCESS.getStatus().equals(callbackOut.getStatus())){
                    return twiceCallbackOut;
                }
            }catch (Exception e){
                log.error("caDoctorAuditNotify twice error.", e);
            }
        }
        return null;
    }


    /**
     * 自动签名授权通知接口
     * @deprecated (因为在app设置自动签名的场景没办法设置回调地址,所以这个值没有准确性了弃用)
     * @param autoSignCallbackIn
     * @return
     */
    @PostMapping("/auto/sign/notify/wjjGmOpenToken")
    public CallbackOut autoCaSignNotify(@RequestBody AutoSignCallbackIn autoSignCallbackIn){
        return caComponent.autoCaSignNotify(autoSignCallbackIn);
    }
}
