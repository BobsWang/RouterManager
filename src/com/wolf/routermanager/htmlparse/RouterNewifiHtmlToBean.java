package com.wolf.routermanager.htmlparse;

import com.wolf.routermanager.bean.BaseRouterBean;
import com.wolf.routermanager.bean.MacAddressFilterBean;
import com.wolf.routermanager.bean.NewifiRouterBean;
import com.wolf.routermanager.bean.RouterSafeAndPasswordBean;
import com.wolf.routermanager.bean.WifiUserBean;
import com.wolf.routermanager.htmlparse.inter.HtmlParseInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RouterNewifiHtmlToBean extends HtmlParseInterface {
    private List<MacAddressFilterBean> mMacAddressFilterBeans = new ArrayList<MacAddressFilterBean>();

    @Override
    public BaseRouterBean getDhcpUser(String html) {
        return null;
    }

    /**
     * 获取黑名单
     */
    @Override
    public BaseRouterBean getAllMacAddressFilter(final String html) {
        return null;
    }

    @Override
    public BaseRouterBean getFilterRule(String html) {
        return null;
    }

    @Override
    public BaseRouterBean getAllActiveUser(String html) throws JSONException {
        NewifiRouterBean baseRouterBean = new NewifiRouterBean();
        List<WifiUserBean> wifiUsers = new ArrayList<WifiUserBean>();
        List<WifiUserBean> macAddressFilterBeans = new ArrayList<WifiUserBean>();
        List<MacAddressFilterBean> macAddressFilterBeansList = new ArrayList<MacAddressFilterBean>();
        JSONObject macjo;
        macjo = new JSONObject(html);
        JSONArray deviceList = macjo.getJSONArray("device_list");
        for (int i = 0; i < deviceList.length(); i++) {
            JSONObject j = deviceList.getJSONObject(i);
            WifiUserBean mac = new WifiUserBean();
            mac.setMacAddress(j.getString("mac"));
            mac.setUserName(j.getString("name"));
            mac.setIpAddress(j.getString("ip"));
            wifiUsers.add(mac);
        }
        JSONObject blackList = macjo.getJSONObject("blacklist");
        JSONArray j = blackList.getJSONArray("list");
        for (int k = 0; k < j.length(); k++) {
            WifiUserBean macAddressFilterBean = new WifiUserBean();
            MacAddressFilterBean bean = new MacAddressFilterBean();
            JSONObject object = j.getJSONObject(k);
            macAddressFilterBean.setMacAddress(object.getString("mac"));
            bean.setMacAddress(object.getString("mac"));
            macAddressFilterBean.setIpAddress(object.getString("ip"));
            macAddressFilterBean.setUserName(object.getString("name"));
            macAddressFilterBeansList.add(bean);
            macAddressFilterBeans.add(macAddressFilterBean);
        }

        baseRouterBean.setActiveUsers(wifiUsers);
        baseRouterBean.setMac(macAddressFilterBeans);
        baseRouterBean.setMacdisplay(macAddressFilterBeansList);

        mMacAddressFilterBeans = macAddressFilterBeansList;

        return baseRouterBean;
    }


    @Override
    public BaseRouterBean getRouterSafeAndPassword(String html) throws JSONException {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        RouterSafeAndPasswordBean bean = new RouterSafeAndPasswordBean();
        JSONObject wifisecjo = new JSONObject(html);
        bean.setPassword(wifisecjo.getString("w_passwd"));
        if ("wpa2".equals(wifisecjo.getString("w_encryption"))) {
            bean.setType(1);
        } else if ("wpa/wpa2".equals(wifisecjo.getString("w_encryption"))) {
            bean.setType(3);
        } else if ("none".equals(wifisecjo.getString("w_encryption"))) {
            bean.setType(0);
        }

        baseRouterBean.setRouterSafePassword(bean);
        return baseRouterBean;
    }

}
