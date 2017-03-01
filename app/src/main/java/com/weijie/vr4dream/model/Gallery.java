package com.weijie.vr4dream.model;


/**
 * VR 继承Idea的type 1、3D   2、2D
 * 作者：guoweijie on 16/12/28 10:24
 * 邮箱：529844698@qq.com
 */
public class Gallery extends Idea {

    /**
     * true全屋   false局部
     */
    private Boolean mode;

    /**
     * 1、单间   2、二居   3、三居   4、四居   5、五居
     */
    private Integer buildType;

    /**
     * 1、60m²以下    2、60m²~80m²    3、80m²~100m²    4、100m²~120m²    5、120m²~150m²    6、150m²以上
     */
    private Integer area;

    /**
     * 1、1万以下    2、1万~2万    3、2万~3万    4、3万~4万    5、4万以上
     */
    private Integer budget;

    /**
     * 1、现代    2、中式    3、美式    4、北欧    5、法式    6、韩式    7、欧式    8、东南亚    9、乡村
     */
    private Integer style;

    //楼盘
    private BuildingEstate buildingEstate;

    //2D链接
    private String link2d;

    public Boolean getMode() {
        return mode;
    }

    public void setMode(Boolean mode) {
        this.mode = mode;
    }

    public Integer getBuildType() {
        return buildType;
    }

    public void setBuildType(Integer buildType) {
        this.buildType = buildType;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Integer getStyle() {
        return style;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }

    public BuildingEstate getBuildingEstate() {
        return buildingEstate;
    }

    public void setBuildingEstate(BuildingEstate buildingEstate) {
        this.buildingEstate = buildingEstate;
    }

    public String getLink2d() {
        return link2d;
    }

    public void setLink2d(String link2d) {
        this.link2d = link2d;
    }

    public String getTitleText() {
        String buildName = buildingEstate.getName();
        if( buildName == null )
            return getBuildTypeText()+getStyleText();
        else
            return buildingEstate.getName()+getBuildTypeText()+getStyleText();
    }

    private String getBuildTypeText() {
        switch (buildType) {
            case 1:
                return "-单间";
            case 2:
                return "-二居";
            case 3:
                return "-三居";
            case 4:
                return "-四居";
            case 5:
                return "-五居";
            default:
                return "";
        }
    }

    private String getStyleText() {
        switch (style) {
            case 1:
                return "-现代";
            case 2:
                return "-中式";
            case 3:
                return "-美式";
            case 4:
                return "-北欧";
            case 5:
                return "-法式";
            case 6:
                return "-韩式";
            case 7:
                return "-欧式";
            case 8:
                return "-东南亚";
            case 9:
                return "-乡村";
            default:
                return "";
        }
    }
}
