package com.shellever.listview;

/**
 * Author: Shellever
 * Date:   11/12/2016
 * Email:  shellever@163.com
 */

public class NewsBean {
    private String title;
    private String content;

    public NewsBean() {
    }

    public NewsBean(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NewsBean{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
