package com.wolf.routermanager.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuwf on 2014/12/19.
 * 存储路由器相关的所有数据
 */
public class BaseRouterBean {
    private List<WifiUserBean> activeUsers = new ArrayList<WifiUserBean>();
    private List<WifiUserBean> dhcpUsers = new ArrayList<WifiUserBean>();
    private List<MacAddressFilterBean> blackUsers = new ArrayList<MacAddressFilterBean>();
    private RouterSafeAndPasswordBean routerSafePassword = new RouterSafeAndPasswordBean();
    private boolean macFilterOpen;

    public boolean isMacFilterOpen() {
        return macFilterOpen;
    }

    public void setMacFilterOpen(boolean macFilterOpen) {
        this.macFilterOpen = macFilterOpen;
    }

    public RouterSafeAndPasswordBean getRouterSafePassword() {
        return routerSafePassword;
    }

    public void setRouterSafePassword(RouterSafeAndPasswordBean routerSafePassword) {
        this.routerSafePassword = routerSafePassword;
    }

    public List<WifiUserBean> getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(List<WifiUserBean> activeUsers) {
        this.activeUsers = activeUsers;
    }

    public List<MacAddressFilterBean> getBlackUsers() {
        return blackUsers;
    }

    public void setBlackUsers(List<MacAddressFilterBean> blackUsers) {
        this.blackUsers = blackUsers;
    }

    public List<WifiUserBean> getDhcpUsers() {
        return dhcpUsers;
    }

    public void setDhcpUsers(List<WifiUserBean> dhcpUsers) {
        this.dhcpUsers = dhcpUsers;
    }
}
