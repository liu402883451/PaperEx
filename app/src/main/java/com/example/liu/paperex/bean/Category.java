package com.example.liu.paperex.bean;

import java.util.List;

/**
 * Created by liu on 16/8/8.
 * 分类实体bean
 */
public class Category {

    private String code;
    private String msg;

    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String ID;
        private String TipContent;
        private String DescWords;
        private String LockType;
        private String LockKey;
        private String PicCategoryName;
        private String PicCategoryLogo;
        private String CategoryPic;
        /**
         * ID : 2140
         * BigCategoryId : 1042
         * PicCategoryName : 性感
         * CategoryPic :
         */

        private List<SecondCategoryListBean> SecondCategoryList;
        /**
         * id : 164
         * name : 美女
         */

        private List<TagsBean> tags;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getTipContent() {
            return TipContent;
        }

        public void setTipContent(String TipContent) {
            this.TipContent = TipContent;
        }

        public String getDescWords() {
            return DescWords;
        }

        public void setDescWords(String DescWords) {
            this.DescWords = DescWords;
        }

        public String getLockType() {
            return LockType;
        }

        public void setLockType(String LockType) {
            this.LockType = LockType;
        }

        public String getLockKey() {
            return LockKey;
        }

        public void setLockKey(String LockKey) {
            this.LockKey = LockKey;
        }

        public String getPicCategoryName() {
            return PicCategoryName;
        }

        public void setPicCategoryName(String PicCategoryName) {
            this.PicCategoryName = PicCategoryName;
        }

        public String getPicCategoryLogo() {
            return PicCategoryLogo;
        }

        public void setPicCategoryLogo(String PicCategoryLogo) {
            this.PicCategoryLogo = PicCategoryLogo;
        }

        public String getCategoryPic() {
            return CategoryPic;
        }

        public void setCategoryPic(String CategoryPic) {
            this.CategoryPic = CategoryPic;
        }

        public List<SecondCategoryListBean> getSecondCategoryList() {
            return SecondCategoryList;
        }

        public void setSecondCategoryList(List<SecondCategoryListBean> SecondCategoryList) {
            this.SecondCategoryList = SecondCategoryList;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public static class SecondCategoryListBean {
            private int ID;
            private int BigCategoryId;
            private String PicCategoryName;
            private String CategoryPic;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public int getBigCategoryId() {
                return BigCategoryId;
            }

            public void setBigCategoryId(int BigCategoryId) {
                this.BigCategoryId = BigCategoryId;
            }

            public String getPicCategoryName() {
                return PicCategoryName;
            }

            public void setPicCategoryName(String PicCategoryName) {
                this.PicCategoryName = PicCategoryName;
            }

            public String getCategoryPic() {
                return CategoryPic;
            }

            public void setCategoryPic(String CategoryPic) {
                this.CategoryPic = CategoryPic;
            }
        }

        public static class TagsBean {
            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
