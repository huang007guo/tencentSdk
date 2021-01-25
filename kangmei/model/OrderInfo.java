package com.wjj.application.facade.kangmei.model;

/**
 * @author haoyanbing
 * @since 2020/4/1
 */
public class OrderInfo<T> {

    private Head head;

    private T data;


    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "head=" + head +
                ", data=" + data +
                '}';
    }
}
