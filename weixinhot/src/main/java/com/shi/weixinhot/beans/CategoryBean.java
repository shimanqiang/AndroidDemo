package com.shi.weixinhot.beans;

import java.io.Serializable;

public class CategoryBean implements Serializable {
    private final static String BASE_REQUEST_UTL = "http://weixin.sogou.com/pcindex/pc/";

    /**
     * 类型：pc_0 、 pc_1 、 ... pc_n
     */
    private String type;
    /**
     * 标题名称
     */
    private String name;
    /**
     * 数据地址
     */
    private String url;
    /**
     * 加载了第几个列表:默认0
     */
    private int index;

    private CategoryBean() {
    }

    public CategoryBean(String type, String name) {
        this.type = type;
        this.name = name;
        /**
         * eg:
         * http://weixin.sogou.com/pcindex/pc/pc_0/pc_0.html    热门
         */
        this.url = BASE_REQUEST_UTL + type + "/" + type + ".html";
        this.index = 0;
    }

    public String getNextUrl() {
        /**
         * eg；
         * http://weixin.sogou.com/pcindex/pc/pc_0/1.html   继续加载
         */
        setIndex(index++);
        return BASE_REQUEST_UTL + type + "/" + getIndex() + ".html";
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}