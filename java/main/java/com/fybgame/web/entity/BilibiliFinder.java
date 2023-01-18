package com.fybgame.web.entity;

import java.io.Serializable;

/**
 * @Author:fyb
 * @Date: 2021/3/5 6:15
 * @Version:1.0
 */
public class BilibiliFinder implements Serializable {
    private Integer id;
    private Integer av;
    private Integer p;
    private String text;
    private String ip;
    private String time;
    private boolean isUseHistory;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAv() {
        return av;
    }

    public void setAv(Integer av) {
        this.av = av;
    }

    public Integer getP() {
        return p;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isUseHistory() {
        return isUseHistory;
    }

    public void setUseHistory(boolean useHistory) {
        isUseHistory = useHistory;
    }

    public BilibiliFinder() {
    }

    public BilibiliFinder(Integer id, Integer av, Integer p, String text, String ip, String time, boolean isUseHistory) {
        this.id = id;
        this.av = av;
        this.p = p;
        this.text = text;
        this.ip = ip;
        this.time = time;
        this.isUseHistory = isUseHistory;
    }
}
