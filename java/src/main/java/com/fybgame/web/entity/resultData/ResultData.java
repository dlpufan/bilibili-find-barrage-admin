package com.fybgame.web.entity.resultData;


import com.fybgame.web.servicecode.HttpCode;

import java.io.Serializable;


/**
 * @Author:fyb
 * @Date: 2021/1/1 2:18
 * @Version:1.0
 */
public class ResultData implements Serializable {
    private int code = HttpCode.OK;
    private String msg = "请求成功";
    private Object data = null;

    @Override
    public String toString() {
        return "ResultData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public ResultData(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultData(String msg, Object data) {
        this.msg = msg;
        this.data = data;
    }
    public ResultData(int code){
        switch(code){
            case HttpCode.OK:
                this.msg = "请求成功";
                break;
            case HttpCode.ERROR:
                this.msg = "未知错误";
                break;
            case HttpCode.MissingParameter:
                this.msg = "缺少参数";
                break;
            case HttpCode.ParameterValueError:
                this.msg = "参数错误";
                break;
            default:
                this.msg = code+"";
        }
    }
    public ResultData(Object data) {
        this.data = data;
    }

    public ResultData(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultData(String msg) {
        this.code = HttpCode.OK;
        this.msg = msg;
    }


    public ResultData() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
