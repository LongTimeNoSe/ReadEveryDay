package com.readeveryday.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by XuYanping on 2017/3/23.
 */

@Entity
public class MeiZhiCollection {
    @Id
    private Long meiZhiId;
    private String imageUrl;
    private String imageDesc;
    @Generated(hash = 1274947492)
    public MeiZhiCollection(Long meiZhiId, String imageUrl, String imageDesc) {
        this.meiZhiId = meiZhiId;
        this.imageUrl = imageUrl;
        this.imageDesc = imageDesc;
    }
    @Generated(hash = 1979022418)
    public MeiZhiCollection() {
    }
    public Long getMeiZhiId() {
        return this.meiZhiId;
    }
    public void setMeiZhiId(Long meiZhiId) {
        this.meiZhiId = meiZhiId;
    }
    public String getImageUrl() {
        return this.imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getImageDesc() {
        return this.imageDesc;
    }
    public void setImageDesc(String imageDesc) {
        this.imageDesc = imageDesc;
    }
}
