package com.aisisea.oa.commons.domain;

import java.util.Map;

public class ReturnObject {

    private String state;
    private String msg;
    private Map<String, Object> data;

    public ReturnObject() {
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ReturnObject{" +
                "state='" + state + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
