package com.wjj.application.facade.ca.casdk.vo.out.callback;

import com.wjj.application.facade.ca.casdk.common.code.ResCode;

/**
 * CA回调返回
 *
 * status	String	是	返回码	0：成功非零：失败
 * message	String	是	描述信息	sucess
 * @author hank
 * @since 2020/12/25 0025 下午 14:23
 */
public class CallbackOut{

    private String status;
    private String message;

    public CallbackOut(String status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * 处理成功返回
     * @return
     */
    public static CallbackOut success(){
        return new CallbackOut(ResCode.SUCCESS.getStatus(), ResCode.SUCCESS.getMessage());
    }


    /**
     * 处理失败返回
     * @return
     */
    public static CallbackOut fail(String msg){
        return new CallbackOut(ResCode.FAIL.getStatus(), msg);
    }

    /**
     * 处理失败返回
     * @return
     */
    public static CallbackOut fail(){
        return fail(ResCode.FAIL.getMessage());
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CallbackOut{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
