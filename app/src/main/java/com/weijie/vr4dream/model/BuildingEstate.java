package com.weijie.vr4dream.model;

import cn.bmob.v3.BmobObject;

/**
 * 楼盘
 * 作者：guoweijie on 16/12/28 10:54
 * 邮箱：529844698@qq.com
 */
public class BuildingEstate extends BmobObject {

    private String name;

    /**
     * 省份
     */
    private String province;

    /**
     * 省份编码
     */
    private String provinceCode;

    /**
     * 城市名称
     */
    private String city;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 地区名
     */
    private String district;

    /**
     * 地区编码
     */
    private String districtCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }
}
