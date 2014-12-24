package com.wolf.routermanager.bean;

/**
 * Created by wuwf on 2014/12/19.
 */
public class TpLinkRouterBean extends BaseRouterBean {
    private String count;
    private boolean dnsSafe;
    private boolean webSafe;

    public String getCount() {
        return count;
    }

    public boolean isDnsSafe() {
        return dnsSafe;
    }

    public void setDnsSafe(boolean dnsSafe) {
        this.dnsSafe = dnsSafe;
    }

    public boolean isWebSafe() {
        return webSafe;
    }

    public void setWebSafe(boolean webSafe) {
        this.webSafe = webSafe;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
