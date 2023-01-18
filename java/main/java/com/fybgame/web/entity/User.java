package com.fybgame.web.entity;

import java.io.Serializable;

/**
 * @Author:fyb
 * @Date: 2021/3/5 0:13
 * @Version:1.0
 */
public class User implements Serializable {
    private String id;
    private String crc32b;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", crc32b='" + crc32b + '\'' +
                '}';
    }

    public User() {
    }

    public User(String id, String crc32b) {
        this.id = id;
        this.crc32b = crc32b;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCrc32b() {
        return crc32b;
    }

    public void setCrc32b(String crc32b) {
        this.crc32b = crc32b;
    }
}
