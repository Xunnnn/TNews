package com.zr.po;

public class NewsQuery {
    private String title;
    private String typeId;
    private Boolean recommend;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    @Override
    public String toString() {
        return "NewsQuery{" +
                "title='" + title + '\'' +
                ", typeId='" + typeId + '\'' +
                ", recommend=" + recommend +
                '}';
    }
}
