package com.example.liu.paperex.bean;

import java.util.List;

/**
 * Created by liu on 16/8/9.
 * 滚动广告实体bean
 */
public class TopicsBean {


    private String code;
    private String msg;

    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int TotalCount;

        private List<TopicBean> Topic;

        public int getTotalCount() {
            return TotalCount;
        }

        public void setTotalCount(int TotalCount) {
            this.TotalCount = TotalCount;
        }

        public List<TopicBean> getTopic() {
            return Topic;
        }

        public void setTopic(List<TopicBean> Topic) {
            this.Topic = Topic;
        }

        public static class TopicBean {
            private String id;
            private String typeid;
            private String subject;
            private String cover_path;
            private String focus_picture_path;
            private String description;
            private String lang;
            private String advert_url;
            private String passtime;
            private String holiday;
            private String ctime;
            private String mtime;
            private String status;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTypeid() {
                return typeid;
            }

            public void setTypeid(String typeid) {
                this.typeid = typeid;
            }

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public String getCover_path() {
                return cover_path;
            }

            public void setCover_path(String cover_path) {
                this.cover_path = cover_path;
            }

            public String getFocus_picture_path() {
                return focus_picture_path;
            }

            public void setFocus_picture_path(String focus_picture_path) {
                this.focus_picture_path = focus_picture_path;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getLang() {
                return lang;
            }

            public void setLang(String lang) {
                this.lang = lang;
            }

            public String getAdvert_url() {
                return advert_url;
            }

            public void setAdvert_url(String advert_url) {
                this.advert_url = advert_url;
            }

            public String getPasstime() {
                return passtime;
            }

            public void setPasstime(String passtime) {
                this.passtime = passtime;
            }

            public String getHoliday() {
                return holiday;
            }

            public void setHoliday(String holiday) {
                this.holiday = holiday;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public String getMtime() {
                return mtime;
            }

            public void setMtime(String mtime) {
                this.mtime = mtime;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
