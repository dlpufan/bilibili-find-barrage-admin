package com.fybgame.web.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Author:fyb
 * @Date: 2021/3/5 2:14
 * @Version:1.0
 */
public class BarrageSender implements Serializable{
    private Barrage barrage;
    private List<User> uidDecrypt;

    public BarrageSender(Barrage barrage,List<User> uidDecrypt) {
        this.barrage = barrage;
        this.uidDecrypt = uidDecrypt;
    }

    @Override
    public String toString() {
        return "BarrageSender{" +
                "barrage=" + barrage +
                ", uidDecrypt=" + uidDecrypt +
                '}';
    }

    public Barrage getBarrage() {
        return barrage;
    }

    public void setBarrage(Barrage barrage) {
        this.barrage = barrage;
    }

    public List<User> getUidDecrypt() {
        return uidDecrypt;
    }

    public void setUidDecrypt(List<User> uidDecrypt) {
        this.uidDecrypt = uidDecrypt;
    }
}
