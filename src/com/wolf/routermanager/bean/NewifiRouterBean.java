package com.wolf.routermanager.bean;

import java.util.List;

/**
 * Created by wuwf on 2014/12/19.
 */
public class NewifiRouterBean extends BaseRouterBean {
    private List<WifiUserBean> mac;
    private List<MacAddressFilterBean> macdisplay;

    public List<WifiUserBean> getMac() {
        return mac;
    }

    public void setMac(List<WifiUserBean> mac) {
        this.mac = mac;
    }

    public List<MacAddressFilterBean> getMacdisplay() {
        return macdisplay;
    }

    public void setMacdisplay(List<MacAddressFilterBean> macdisplay) {
        this.macdisplay = macdisplay;
    }
}
