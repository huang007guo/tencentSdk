/**
 * Copyright 2018 bejson.com
 */
package com.xiaochong.meet.lib.tencent.tencentvodsdk.vo.out;

/**
 *
 * 腾讯点播api 返回VO 参考地址：
 * https://cloud.tencent.com/document/product/266/9642#.E6.8E.A5.E5.8F.A3.E5.BA.94.E7.AD.94
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class TencentVodOut {

    //错误码, 0: 成功；其他值: 失败 参考 VodResCode
    private int code;
    //错误信息
    private String message;
    private String codeDesc;
    //任务id
    private String vodTaskId;
    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }
    public String getCodeDesc() {
        return codeDesc;
    }

    public void setVodTaskId(String vodTaskId) {
        this.vodTaskId = vodTaskId;
    }
    public String getVodTaskId() {
        return vodTaskId;
    }

}