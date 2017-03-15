package com.readeveryday.bean.daily;

import java.io.Serializable;

/**
 * Created by XuYanping on 2017/3/15.
 */
public class Options implements Serializable {

    private String content;

    private Author author;

    public String getContent() {
        return content;
    }

    public Author getAuthor() {
        return author;
    }

    public class Author implements Serializable {
        private String avatar;
        private String name;

        public String getAvatar() {
            return avatar;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Author{" +
                    "avatar='" + avatar + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Options{" +
                "content='" + content + '\'' +
                ", author=" + author +
                '}';
    }
}
