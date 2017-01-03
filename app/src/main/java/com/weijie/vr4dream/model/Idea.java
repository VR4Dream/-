package com.weijie.vr4dream.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * 灵感 文章
 * 作者：guoweijie on 16/12/28 10:17
 * 邮箱：529844698@qq.com
 */
public class Idea extends BmobObject {

    /**
     * 1、案例 2、客厅 3、卧室 4、儿童房 5、攻略 6、其他
     */
    private Integer type;

    private Integer showTime;

    private String link;

    private BmobRelation likes;

    private String cover;

    private String title;

    private String tag;

    private Integer likesNum;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getShowTime() {
        return showTime;
    }

    public void setShowTime(Integer showTime) {
        this.showTime = showTime;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getLikesNum() {
        if(likesNum==null) {
            return 0;
        }
        return likesNum;
    }

    public void setLikesNum(Integer likesNum) {
        this.likesNum = likesNum;
    }
}
