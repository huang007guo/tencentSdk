package com.wjj.application.facade.kangmei.model.save;

import java.util.List;

/**
 * @author haoyanbing
 * @since 2020/4/1
 */
public class Data {
    private String order_time;
    private String reg_num;
    private String addr_str;
    private String consignee;
    private String con_tel;
    private String pay_status;
    private String callback_url;
    private String logis_url_callback;
    private List<Pdetail> prescript;

    public String getLogis_url_callback() {
        return logis_url_callback;
    }

    public void setLogis_url_callback(String logis_url_callback) {
        this.logis_url_callback = logis_url_callback;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getReg_num() {
        return reg_num;
    }

    public void setReg_num(String reg_num) {
        this.reg_num = reg_num;
    }

    public String getAddr_str() {
        return addr_str;
    }

    public void setAddr_str(String addr_str) {
        this.addr_str = addr_str;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getCon_tel() {
        return con_tel;
    }

    public void setCon_tel(String con_tel) {
        this.con_tel = con_tel;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getCallback_url() {
        return callback_url;
    }

    public void setCallback_url(String callback_url) {
        this.callback_url = callback_url;
    }

    public List<Pdetail> getPrescript() {
        return prescript;
    }

    public void setPrescript(List<Pdetail> prescript) {
        this.prescript = prescript;
    }

    @Override
    public String toString() {
        return "Data{" +
                "order_time='" + order_time + '\'' +
                ", reg_num='" + reg_num + '\'' +
                ", addr_str='" + addr_str + '\'' +
                ", consignee='" + consignee + '\'' +
                ", con_tel='" + con_tel + '\'' +
                ", pay_status='" + pay_status + '\'' +
                ", callback_url='" + callback_url + '\'' +
                ", logis_url_callback='" + logis_url_callback + '\'' +
                ", prescript=" + prescript +
                '}';
    }
}
