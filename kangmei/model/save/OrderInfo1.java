package com.wjj.application.facade.kangmei.model.save;

/**
 * @author haoyanbing
 * @since 2020/4/1
 */
public class OrderInfo1 {
    private String reg_num;
    private Boolean IsSuccess;
    private String message;
    private String orderid;
    private String prescriptionIds;

    public String getReg_num() {
        return reg_num;
    }

    public void setReg_num(String reg_num) {
        this.reg_num = reg_num;
    }

    public Boolean getSuccess() {
        return IsSuccess;
    }

    public void setSuccess(Boolean success) {
        IsSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getPrescriptionIds() {
        return prescriptionIds;
    }

    public void setPrescriptionIds(String prescriptionIds) {
        this.prescriptionIds = prescriptionIds;
    }

    @Override
    public String toString() {
        return "OrderInfo1{" +
                "reg_num='" + reg_num + '\'' +
                ", IsSuccess=" + IsSuccess +
                ", message='" + message + '\'' +
                ", orderid='" + orderid + '\'' +
                ", prescriptionIds='" + prescriptionIds + '\'' +
                '}';
    }
}
