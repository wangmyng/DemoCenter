package com.wangmyng.common.bean;

public class MainListBean {

    private String title;
    private String routePath;
    private int iconRes;
    private int bannerRes;

    public MainListBean(String routePath, String title, int iconRes, int bannerRes) {
        this.routePath = routePath;
        this.title = title;
        this.iconRes = iconRes;
        this.bannerRes = bannerRes;
    }

    public String getRoutePath() {
        return routePath;
    }

    public void setRoutePath(String routePath) {
        this.routePath = routePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public int getBannerRes() {
        return bannerRes;
    }

    public void setBannerRes(int bannerRes) {
        this.bannerRes = bannerRes;
    }
}
