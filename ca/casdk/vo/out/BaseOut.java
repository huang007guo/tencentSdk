/**
  * Copyright 2020 bejson.com 
  */
package com.wjj.application.facade.ca.casdk.vo.out;

/**
 * ca返回基础类
 * @author hank
 */
public class BaseOut<D> {

    private String message;
    private String status;
    private D data;
    public void setMessage(String message) {
         this.message = message;
     }
     public String getMessage() {
         return message;
     }

    public void setStatus(String status) {
         this.status = status;
     }
     public String getStatus() {
         return status;
     }

    public void setData(D data) {
         this.data = data;
     }
     public D getData() {
         return data;
     }

    @Override
    public String toString() {
        return "BaseOut{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}