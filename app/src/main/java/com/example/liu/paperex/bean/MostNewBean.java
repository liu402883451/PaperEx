package com.example.liu.paperex.bean;

import java.util.List;

/**
 * Created by liu on 16/8/9.
 * 最新实体bean
 */
public class MostNewBean {

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
        private String TotalCount;

        private List<WallpaperListInfoBean> WallpaperListInfo;

        public String getTotalCount() {
            return TotalCount;
        }

        public void setTotalCount(String TotalCount) {
            this.TotalCount = TotalCount;
        }

        public List<WallpaperListInfoBean> getWallpaperListInfo() {
            return WallpaperListInfo;
        }

        public void setWallpaperListInfo(List<WallpaperListInfoBean> WallpaperListInfo) {
            this.WallpaperListInfo = WallpaperListInfo;
        }

        public static class WallpaperListInfoBean {
            private int ID;
            private String PicName;
            private String pic_path;
            private int BigCategoryId;
            private int SecondCategoryId;
            private String CreateTime;
            private String passtime;
            private String UserName;
            private int DownloadCount;
            private int GoodCount;
            private String tags;
            private String WallPaperMiddle;
            private String WallPaperBig;
            private String WallPaperDownloadPath;
            private String WallPaperSource;
            private String weixin_url;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getPicName() {
                return PicName;
            }

            public void setPicName(String PicName) {
                this.PicName = PicName;
            }

            public String getPic_path() {
                return pic_path;
            }

            public void setPic_path(String pic_path) {
                this.pic_path = pic_path;
            }

            public int getBigCategoryId() {
                return BigCategoryId;
            }

            public void setBigCategoryId(int BigCategoryId) {
                this.BigCategoryId = BigCategoryId;
            }

            public int getSecondCategoryId() {
                return SecondCategoryId;
            }

            public void setSecondCategoryId(int SecondCategoryId) {
                this.SecondCategoryId = SecondCategoryId;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }

            public String getPasstime() {
                return passtime;
            }

            public void setPasstime(String passtime) {
                this.passtime = passtime;
            }

            public String getUserName() {
                return UserName;
            }

            public void setUserName(String UserName) {
                this.UserName = UserName;
            }

            public int getDownloadCount() {
                return DownloadCount;
            }

            public void setDownloadCount(int DownloadCount) {
                this.DownloadCount = DownloadCount;
            }

            public int getGoodCount() {
                return GoodCount;
            }

            public void setGoodCount(int GoodCount) {
                this.GoodCount = GoodCount;
            }

            public String getTags() {
                return tags;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public String getWallPaperMiddle() {
                return WallPaperMiddle;
            }

            public void setWallPaperMiddle(String WallPaperMiddle) {
                this.WallPaperMiddle = WallPaperMiddle;
            }

            public String getWallPaperBig() {
                return WallPaperBig;
            }

            public void setWallPaperBig(String WallPaperBig) {
                this.WallPaperBig = WallPaperBig;
            }

            public String getWallPaperDownloadPath() {
                return WallPaperDownloadPath;
            }

            public void setWallPaperDownloadPath(String WallPaperDownloadPath) {
                this.WallPaperDownloadPath = WallPaperDownloadPath;
            }

            public String getWallPaperSource() {
                return WallPaperSource;
            }

            public void setWallPaperSource(String WallPaperSource) {
                this.WallPaperSource = WallPaperSource;
            }

            public String getWeixin_url() {
                return weixin_url;
            }

            public void setWeixin_url(String weixin_url) {
                this.weixin_url = weixin_url;
            }
        }
    }
}
