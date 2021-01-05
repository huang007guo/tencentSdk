package com.wjj.application.facade.ca.casdk;

import com.wjj.application.facade.ca.casdk.vo.in.BaseIn;
import com.wjj.application.facade.ca.casdk.vo.in.DoctorSynIn;
import com.wjj.application.facade.ca.casdk.vo.in.SelfSignGetGrantResultIn;
import com.wjj.application.facade.ca.casdk.vo.in.recipesyn.RecipeSynIn;
import com.wjj.application.facade.ca.casdk.vo.out.DoctorSynOut;
import com.wjj.application.facade.ca.casdk.vo.out.RecipeSynOut;
import com.wjj.application.facade.ca.casdk.vo.out.SelfSignGetGrantResultOut;
import feign.Headers;
import feign.RequestLine;

/**
 * ca接口请求feign
 *
 * <p>\@throws com.wjj.application.facade.ca.casdk.exception.CaResponseException http请求错误抛出
 * <p>\@throws com.wjj.application.facade.ca.casdk.exception.CaTokenException 获取token失败抛出
 * <p>\@throws com.wjj.application.facade.ca.casdk.exception.CaResStatusException 返回的status不为0(成功)抛出
 *
 * <p>example:
 *     <p>\@Autowired
 *     <p>private CaFeignClient caFeignClient;
 *
 *         <p>DoctorSynIn body = new DoctorSynIn();
 *         <p>body.setDoctorIdType("YS");
 *         <p>body.setUid("360681199006263017");
 *         <p>body.setPhone("18600000000");
 *         <p>body.setDepartment("呼吸科");
 *         <p>body.setUidCardType("SF");
 *         <p>body.setDoctorName("黄国梁");
 *         <p>DoctorSynOut doctorSynOut = caFeignClient.accountImport(BaseIn.build(body, BaseIn.class));
 */
@Headers("Content-Type: application/json")
public interface CaFeignClient {

    /**
     * 医师信息同步接口
     * <p>example:
     *     <p>\@Autowired
     *     <p>private CaFeignClient caFeignClient;
     *
     *         <p>DoctorSynIn body = new DoctorSynIn();
     *         <p>body.setDoctorIdType("YS");
     *         <p>body.setUid("360681199006263017");
     *         <p>body.setPhone("18600000000");
     *         <p>body.setDepartment("呼吸科");
     *         <p>body.setUidCardType("SF");
     *         <p>body.setDoctorName("黄国梁");
     *         <p>DoctorSynOut doctorSynOut = caFeignClient.accountImport(BaseIn.build(body, BaseIn.class));
     * @param doctorSyn
     * @throws com.wjj.application.facade.ca.casdk.exception.CaResponseException http请求错误抛出
     * @throws com.wjj.application.facade.ca.casdk.exception.CaTokenException 获取token失败抛出
     * @throws com.wjj.application.facade.ca.casdk.exception.CaResStatusException 返回的status不为0(成功)抛出
     * @return
     */
    @RequestLine("POST /am/v2/doctor/syn")
    DoctorSynOut doctorSyn(
            BaseIn<DoctorSynIn> doctorSyn
    );

    /**
     * 	签名信息同步服务
     * @param doctorSyn
     * @return
     */
    @RequestLine("POST /am/v2/recipe/syn")
    RecipeSynOut recipeSyn(
            RecipeSynIn<?> doctorSyn
    );
    /**
     * 	自动签名授权-获取授权结果接口
     * @param doctorSyn
     * @return
     */
    @RequestLine("POST /am/v2/selfSign/getGrantResult")
    SelfSignGetGrantResultOut selfSignGetGrantResult(
            BaseIn<SelfSignGetGrantResultIn> doctorSyn
    );



}
