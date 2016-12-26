package com.shi.weixinhot.model;

/**
 * Created by shimanqiang on 16/12/26.
 */

public class BannerBean {
    private int type; //0:轮播图 banner ; 1:普通条目
    private String title;//标题
    private String url;//链接地址
    private String imgUrl; //图片地址

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
