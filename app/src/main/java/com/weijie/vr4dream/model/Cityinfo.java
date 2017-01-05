package com.weijie.vr4dream.model;

import java.io.Serializable;

/**
 * 作者：guoweijie on 17/1/5 14:06
 * 邮箱：529844698@qq.com
 */
public class Cityinfo implements Serializable {

    private String id;
    private String city_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    @Override
    public String toString() {
        return "Cityinfo [id=" + id + ", city_name=" + city_name + "]";
    }

}