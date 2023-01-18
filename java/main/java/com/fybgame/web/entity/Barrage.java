package com.fybgame.web.entity;

import java.awt.*;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author:fyb
 * @Date: 2021/3/5 0:50
 * @Version:1.0
 */
public class Barrage implements Serializable {
    private String sendTimeInVideo;
    private int type;
    private int size;
    private MyColor myColor;
    private String sendTime;
    private int barragePool;
    private String uid;
    private String rowId;
    private String value;
    private String barrageLevel;

    public String getBarrageLevel() {
        return barrageLevel;
    }

    public void setBarrageLevel(String barrageLevel) {
        this.barrageLevel = barrageLevel;
    }

    @Override
    public String toString() {
        return "Barrage{" +
                "sendTimeInVideo='" + sendTimeInVideo + '\'' +
                ", type=" + type +
                ", size=" + size +
                ", myColor=" + myColor +
                ", sendTime='" + sendTime + '\'' +
                ", barragePool=" + barragePool +
                ", uid='" + uid + '\'' +
                ", rowId='" + rowId + '\'' +
                ", value='" + value + '\'' +
                ", barrageLevel='" + barrageLevel + '\'' +
                '}';
    }

    public Barrage() {
    }
    public Barrage(Barrage barrage){
        this.sendTimeInVideo = barrage.sendTimeInVideo;
        this.type = barrage.type;
        this.size = barrage.size;
        this.myColor = barrage.myColor;
        this.sendTime = barrage.sendTime;
        this.barragePool = barrage.barragePool;
        this.uid = barrage.uid;
        this.rowId = barrage.rowId;
        this.value = barrage.value;
    }
    public boolean isMatching(String match){
        if(this.value == null){
            return false;
        }
        if(value.indexOf(match)!=-1){
            return true;
        }
        try {
            Pattern p = Pattern.compile(match);
            Matcher m = p.matcher(value);
            return m.matches();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public String getSendTimeInVideo() {
        return sendTimeInVideo;
    }

    public void setSendTimeInVideo(String sendTimeInVideo) {
        this.sendTimeInVideo = sendTimeInVideo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public MyColor getColor() {
        return myColor;
    }

    public void setColor(MyColor myColor) {
        this.myColor = myColor;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public int getBarragePool() {
        return barragePool;
    }

    public void setBarragePool(int barragePool) {
        this.barragePool = barragePool;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Barrage(String sendTimeInVideo, int type, int size, MyColor myColor, String sendTime, int barragePool, String uid, String rowId, String value, String barrageLevel) {
        this.sendTimeInVideo = sendTimeInVideo;
        this.type = type;
        this.size = size;
        this.myColor = myColor;
        this.sendTime = sendTime;
        this.barragePool = barragePool;
        this.uid = uid;
        this.rowId = rowId;
        this.value = value;
        this.barrageLevel = barrageLevel;
    }
}
