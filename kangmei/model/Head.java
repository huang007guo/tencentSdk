package com.wjj.application.facade.kangmei.model;

/**
 * @author haoyanbing
 * @since 2020/4/1
 */
public class Head {
    private String company_num;

    private String key;

    private String sign;

    private String new_company_pass;


    public String getCompany_num() {
        return company_num;
    }

    public void setCompany_num(String company_num) {
        this.company_num = company_num;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNew_company_pass() {
        return new_company_pass;
    }

    public void setNew_company_pass(String new_company_pass) {
        this.new_company_pass = new_company_pass;
    }

    @Override
    public String toString() {
        return "";
    }
}
