package com.readeveryday.bean.daily;

import java.io.Serializable;

/**
 * Created by XuYanping on 2017/3/15.
 */
public class HeadLine implements Serializable {

    private String description;
    private String title;

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "HeadLine{" +
                "description='" + description + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
