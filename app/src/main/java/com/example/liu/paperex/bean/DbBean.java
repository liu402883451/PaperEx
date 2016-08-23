package com.example.liu.paperex.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by liu on 16/8/23.
 */
@Table(name = "download")
public class DbBean {
    @Column(name = "_id",isId = true,autoGen = true)
    private int id;
    @Column(name = "url")
    private String url;

    public DbBean() {
    }

    public DbBean(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
