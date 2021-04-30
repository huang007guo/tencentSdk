package com.wjj.application.controller.saas.ca;


import com.wjj.application.facade.ca.casdk.config.CaConfig;
import com.wjj.application.facade.ca.casdk.util.CaUtils;
import com.wjj.application.paysdk.crypt.tools.AesCryptTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;

/**
 * <p>
 * CA 非正式环境的控制器
 * </p>
 *
 * @author hank
 */
@Api(value = "caNonProd相关", tags = "caNonProd相关")
@RequestMapping("nonProd/ca")
@Slf4j
public class CaNonProdController {

    @Autowired
    private CaConfig caConfig;

    @PostMapping("/getAccessToken/wjjGmOpenToken/{verifyCode}")
    @ApiOperation(value = "CA签名获取")
    @ResponseBody
    public String getAccessToken(@PathVariable String verifyCode, @RequestBody Boolean isForce){
        String hankCaGetAccessToken = null;
        try {
            hankCaGetAccessToken = URLDecoder.decode(verifyCode, CharEncoding.UTF_8);
            // 解密
            hankCaGetAccessToken = AesCryptTools.decrypt(hankCaGetAccessToken, "hankCaGetAccessToken");
        } catch (Exception e){
            log.error("nonProd/ca/getAccessToken decrypt error.", e);
        }
        if(StringUtils.isBlank(hankCaGetAccessToken)){
            return "131231234123124123";
        }
        String caGetAccessTokenDate = hankCaGetAccessToken.replace("caGetAccessTokenDate:", "");
        // 时间约束,有效时间15秒
        long caGetAccessTokenDateTimeMillis = Long.parseLong(caGetAccessTokenDate);
        if(System.currentTimeMillis() - caGetAccessTokenDateTimeMillis > 15000){
            return "131232424231234123";
        }
        try {
            return CaUtils.getAccessToken(caConfig, isForce, false);
        }catch (Exception e){
            return "";
        }
    }
}
