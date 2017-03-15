package com.readeveryday.bean.daily;

import java.io.Serializable;

/**
 *Created by XuYanping on 2017/3/15.
 */
public class Category implements Serializable {

    private String image_lab;
    private String title;

    public String getImage_lab() {
        return image_lab;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Category{" +
                "image_lab='" + image_lab + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
