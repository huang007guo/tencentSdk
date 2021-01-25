package com.wjj.application.facade.kangmei.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.wjj.application.facade.kangmei.KangMeiConfig;
import com.wjj.application.facade.kangmei.annotation.RetryProcess;
import com.wjj.application.facade.kangmei.exception.KangmeiParamErrorException;
import com.wjj.application.facade.kangmei.exception.KangmeiResultException;
import com.wjj.application.facade.kangmei.model.Head;
import com.wjj.application.facade.kangmei.model.OrderInfo;
import com.wjj.application.facade.kangmei.model.save.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 康美请求工具类
 * 这里所有公共方法都应该是对外的接口,公共方法会经过切面:
 * 失败重试二次失败发邮件,KangmeiBaseException异常直接邮件
 */
@Slf4j
@Component
@Lazy
public class KangMeiUtil {

    private KangMeiConfig kangMeiConfig;

    public static final String HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
    private Client client;
    private XStream xStream;

    private static final String SAVE_ORDER_INFO = "saveOrderInfo";

    private static final String UPDATE_ACCOUNT_INFO = "updateAccountInfo";

    private static final String GET_ORDER_INFO_BY_ORDER_ID = "getOrderInfoByOrderId";

    @Autowired
    public KangMeiUtil(KangMeiConfig kangMeiConfig) {
        this.kangMeiConfig = kangMeiConfig;
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        client = dcf.createClient(kangMeiConfig.getWsdlUrl());
        xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("_-", "_")));

        xStream.aliasSystemAttribute(null, "class");
//        XStream xs = new XStream();
        //解决xStream:Security framework of XStream not initialized, XStream is probably vulnerable
        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypes(new Class[]{Result.class});

        xStream.alias("data", Data.class);
        xStream.alias("head", Head.class);
        xStream.alias("orderInfo", OrderInfo.class);
        xStream.alias("pdetail", Pdetail.class);
        xStream.alias("xq", Xq.class);
        xStream.alias("result", Result.class);
        xStream.alias("orderInfo", OrderInfo1.class);

    }

    /**
     * 同步订单,只有同步成功才会返回,
     * 其他情况,抛出异常
     * @param orderInfo
     * @return
     * @throws Exception
     */
    @RetryProcess(apiName = "订单同步")
    public OrderInfo1 saveOrderInfo(OrderInfo orderInfo) throws Exception {
        if (orderInfo == null || orderInfo.getData() == null){
            throw new KangmeiParamErrorException(orderInfo);
        }
        orderInfo.setHead(getHead(SAVE_ORDER_INFO));
        String xml = xStream.toXML(orderInfo);
        xml = HEADER + "\n" + xml;
        String params = Base64.getEncoder().encodeToString(xml.getBytes());

        // webService调用
        Object[] objects = client.invoke(SAVE_ORDER_INFO, params);

        String s = new String((byte[]) objects[0], StandardCharsets.UTF_8);
        log.info("KangMeiUtil.saveOrderInfo ret:"+s);
        xStream.alias("result", Result.class);
        Result result = (Result) xStream.fromXML(s);
        if (result == null || !"0".equals(result.getResultCode()) || result.getOrderInfo() == null || !result.getOrderInfo().getSuccess()){
            // 失败 康美异常
            throw new KangmeiResultException("同步下单失败", orderInfo, result);
        }
        return result.getOrderInfo();
    }

 /*   @RetryProcess(apiName = "订单获取")
    public void getOrderInfoByOrderId(String orderId) throws Exception {
        Head head = getHead(GET_ORDER_INFO_BY_ORDER_ID);
        com.wjj.application.facade.kangmei.model.query.Data data = new com.wjj.application.facade.kangmei.model.query.Data();
        data.setOrder_id(orderId);

        OrderInfo<com.wjj.application.facade.kangmei.model.query.Data> orderInfo = new OrderInfo<>();
        orderInfo.setHead(head);
        orderInfo.setData(data);

        String xml = xStream.toXML(orderInfo);
        xml = HEADER + "\n" + xml;

        String params = Base64.getEncoder().encodeToString(xml.getBytes());

        // webService调用
        Object[] objects = client.invoke(GET_ORDER_INFO_BY_ORDER_ID, params);

        String s = new String((byte[]) objects[0], StandardCharsets.UTF_8);
        s = s.substring(38);

        System.out.println(s);
    }*/


    //-------------------------------------------------------------------------------------

    private Head getHead(String methodName) throws NoSuchAlgorithmException {
        Head head = new Head();
        Long mills = System.currentTimeMillis();
        head.setCompany_num(kangMeiConfig.getCompanyNum());
        head.setKey(String.valueOf(mills));
        head.setSign(getSign(methodName, mills, kangMeiConfig.getPassword()));
        return head;
    }


    private String getSign(String methodName, Long mills, String password) throws NoSuchAlgorithmException {
        return getMD5String(methodName + mills + getMD5String(password).toLowerCase());
    }

    private String getMD5String(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());
        return new BigInteger(1, md.digest()).toString(16);
    }
}
