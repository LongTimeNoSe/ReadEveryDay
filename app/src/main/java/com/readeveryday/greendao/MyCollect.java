package com.readeveryday.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by XuYanping on 2017/3/23.
 */

@Entity
public class MyCollect {
    @Id
    private Long Id;
    private String meiZhiImageUrl;
    private String meiZhiImageDesc;
    private String newsTitle;
    private String newsImageUrl;
    private String newsUrl;
    private String type;

    public MyCollect(String meiZhiImageUrl, String meiZhiImageDesc, String newsTitle, String newsImageUrl, String newsUrl, String type) {
        this.meiZhiImageUrl = meiZhiImageUrl;
        this.meiZhiImageDesc = meiZhiImageDesc;
        this.newsTitle = newsTitle;
        this.newsImageUrl = newsImageUrl;
        this.newsUrl = newsUrl;
        this.type = type;
    }

    @Generated(hash = 1506882456)
    public MyCollect(Long Id, String meiZhiImageUrl, String meiZhiImageDesc, String newsTitle, String newsImageUrl, String newsUrl,
            String type) {
        this.Id = Id;
        this.meiZhiImageUrl = meiZhiImageUrl;
        this.meiZhiImageDesc = meiZhiImageDesc;
        this.newsTitle = newsTitle;
        this.newsImageUrl = newsImageUrl;
        this.newsUrl = newsUrl;
        this.type = type;
    }

    @Generated(hash = 1158916427)
    public MyCollect() {
    }

    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getMeiZhiImageUrl() {
        return this.meiZhiImageUrl;
    }

    public void setMeiZhiImageUrl(String meiZhiImageUrl) {
        this.meiZhiImageUrl = meiZhiImageUrl;
    }

    public String getMeiZhiImageDesc() {
        return this.meiZhiImageDesc;
    }

    public void setMeiZhiImageDesc(String meiZhiImageDesc) {
        this.meiZhiImageDesc = meiZhiImageDesc;
    }

    public String getNewsTitle() {
        return this.newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsImageUrl() {
        return this.newsImageUrl;
    }

    public void setNewsImageUrl(String newsImageUrl) {
        this.newsImageUrl = newsImageUrl;
    }

    public String getNewsUrl() {
        return this.newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
