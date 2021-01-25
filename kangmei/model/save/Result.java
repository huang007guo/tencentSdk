package com.wjj.application.facade.kangmei.model.save;

/**
 * @author haoyanbing
 * @since 2020/4/1
 */
public class Result {
    private String description;
    private String resultCode;
    private String state;

    private OrderInfo1 orderInfo;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public OrderInfo1 getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo1 orderInfo) {
        this.orderInfo = orderInfo;
    }

    @Override
    public String toString() {
        return "Result{" +
                "description='" + description + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", state='" + state + '\'' +
                ", orderInfo=" + orderInfo +
                '}';
    }
}
