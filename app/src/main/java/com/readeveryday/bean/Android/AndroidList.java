package com.readeveryday.bean.Android;

import java.util.List;

/**
 * Created by XuYanping on 2017/3/17.
 */

public class AndroidList {


    /**
     * error : false
     * results : [{"_id":"58c7a38e421aa90f03345176","createdAt":"2017-03-14T16:02:22.511Z","desc":"Android IPC 机制详解，你想知道的都在这","publishedAt":"2017-03-16T11:37:02.85Z","source":"web","type":"Android","url":"http://qlm.pw/2017/03/14/android-ipc-%E6%9C%BA%E5%88%B6/","used":true,"who":"Linmin Qiu"},{"_id":"58c7c94e421aa90f13178667","createdAt":"2017-03-14T18:43:26.169Z","desc":"深入了解Java之类加载和案例分析","images":["http://img.gank.io/b3c9a0d4-f050-425d-b0f6-6d7f72b38159"],"publishedAt":"2017-03-16T11:37:02.85Z","source":"web","type":"Android","url":"http://itfeifei.win/2017/03/14/%E6%B7%B1%E5%85%A5%E4%BA%86%E8%A7%A3Java%E4%B9%8B%E7%B1%BB%E5%8A%A0%E8%BD%BD%E5%92%8C%E6%A1%88%E4%BE%8B%E5%88%86%E6%9E%90/","used":true},{"_id":"58c9102b421aa90f03345186","createdAt":"2017-03-15T17:58:03.963Z","desc":"一个好看的 Dialog","images":["http://img.gank.io/9335de88-3732-4a28-b7da-a7800a760060"],"publishedAt":"2017-03-16T11:37:02.85Z","source":"chrome","type":"Android","url":"https://github.com/geniusforapp/fancyDialog","used":true,"who":"蒋朋"},{"_id":"58c9f83f421aa90f0334518a","createdAt":"2017-03-16T10:28:15.169Z","desc":"细说Bitmap那些事","publishedAt":"2017-03-16T11:37:02.85Z","source":"web","type":"Android","url":"https://mp.weixin.qq.com/s?__biz=MzI3OTU3OTQ1Mw==&mid=2247483753&idx=1&sn=8b25e2915c72aacdf2e1cfa38aa1cb87&chksm=eb44df3bdc33562d7784753776ba820361d71228b0081e66661c6070008c0038bbabf0558ab8&mpshare=1&scene=23&srcid=0316pLW7Dlj2Y0bHTIUNHY2D%23rd","used":true},{"_id":"58c259ce421aa90f03345158","createdAt":"2017-03-10T15:46:22.219Z","desc":"编码中陌生单词不认识？是时候打造一款AndroidStudio单词备忘插件了","images":["http://img.gank.io/b24f270b-ea5c-4e6b-87bb-628c329c01cf"],"publishedAt":"2017-03-15T11:47:17.825Z","source":"web","type":"Android","url":"http://www.jianshu.com/p/760c98f682ea","used":true,"who":"bolex"},{"_id":"58c65797421aa90f1317865b","createdAt":"2017-03-13T16:25:59.413Z","desc":"以Android项目为例了解Travis CI的使用","publishedAt":"2017-03-15T11:47:17.825Z","source":"web","type":"Android","url":"https://mp.weixin.qq.com/s?__biz=MzI3ODI3NTU3Mg==&mid=2247483753&idx=1&sn=00a7bced404e340bf4a64a0756afd517&chksm=eb5835f2dc2fbce46d5ac68823bab3e1e8b9ae62339febd48bc673a4d355900d7a64bb9907b0&mpshare=1&scene=1&srcid=03130uTYA7UqtjW8cJCGtogS&key=d17593927da82b11b08880e61e8349e4a307c7f1dfeec7c933ed81cc10531d108373970a84fb88e290d90ae002506ff8fdccaad139406eaeb109f92b76ed0c8bd2ee19ca14de6918f3e3f46323395b24&ascene=0&uin=MTA4NDYwMDI4Mg%3D%3D&devicetype=iMac+MacBookPro11%2C4+OSX+OSX+10.12.2+build(16C67)&version=12010310&nettype=WIFI&fontScale=100&pass_ticket=NUgGEHBSuBid4sBnBK45euKREnaJHtZLK%2F2wj8bpb8lKQ2o8B4s3T9Qy0SWZeIGT","used":true,"who":"Eric"},{"_id":"58c69733421aa90f03345171","createdAt":"2017-03-13T20:57:23.723Z","desc":"简单轻量的Android Router 框架","images":["http://img.gank.io/79d2ca8a-2af8-48fd-baa7-1c5e8c8aca10"],"publishedAt":"2017-03-15T11:47:17.825Z","source":"web","type":"Android","url":"https://github.com/themores/AntCaves","used":true},{"_id":"58c88e08421aa90f1317866a","createdAt":"2017-03-15T08:42:48.806Z","desc":"安卓开发测试模板","publishedAt":"2017-03-15T11:47:17.825Z","source":"chrome","type":"Android","url":"https://github.com/jaredsburrows/android-gradle-java-app-template","used":true,"who":"蒋朋"},{"_id":"58c8ab42421aa95810795c6b","createdAt":"2017-03-15T10:47:30.697Z","desc":"Android 流式标签布局","images":["http://img.gank.io/57f31ba9-d015-45db-a202-29ce51c3f363"],"publishedAt":"2017-03-15T11:47:17.825Z","source":"chrome","type":"Android","url":"https://github.com/nex3z/FlowLayout","used":true,"who":"带马甲"},{"_id":"58c8ac54421aa95810795c6c","createdAt":"2017-03-15T10:52:04.465Z","desc":"Android  Holding Button 效果","images":["http://img.gank.io/2f0871b0-056b-4ad9-973f-18cdd8c78d55"],"publishedAt":"2017-03-15T11:47:17.825Z","source":"chrome","type":"Android","url":"https://github.com/dewarder/HoldingButton","used":true,"who":"马夹袋"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 58c7a38e421aa90f03345176
         * createdAt : 2017-03-14T16:02:22.511Z
         * desc : Android IPC 机制详解，你想知道的都在这
         * publishedAt : 2017-03-16T11:37:02.85Z
         * source : web
         * type : Android
         * url : http://qlm.pw/2017/03/14/android-ipc-%E6%9C%BA%E5%88%B6/
         * used : true
         * who : Linmin Qiu
         * images : ["http://img.gank.io/b3c9a0d4-f050-425d-b0f6-6d7f72b38159"]
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;
        private List<String> images;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
