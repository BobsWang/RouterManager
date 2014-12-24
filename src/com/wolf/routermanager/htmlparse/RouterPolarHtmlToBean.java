package com.wolf.routermanager.htmlparse;

import android.annotation.SuppressLint;
import com.wolf.routermanager.bean.BaseRouterBean;
import com.wolf.routermanager.bean.MacAddressFilterBean;
import com.wolf.routermanager.bean.RouterSafeAndPasswordBean;
import com.wolf.routermanager.bean.WifiUserBean;
import com.wolf.routermanager.htmlparse.inter.HtmlParseInterface;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PaulHsu on 2014/11/12.
 * 极路由html转换成对象
 */
@SuppressLint("DefaultLocale")
public class RouterPolarHtmlToBean extends HtmlParseInterface {
    @Override
    public BaseRouterBean getDhcpUser(String html) {
        return null;
    }

    @Override
    public BaseRouterBean getAllMacAddressFilter(String html) throws JSONException {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        List<MacAddressFilterBean> macAddressFilter = new ArrayList<MacAddressFilterBean>();
        if (html.contains("stop")) {
            baseRouterBean.setBlackUsers(macAddressFilter);
            return baseRouterBean;
        }
        JSONObject macfiljo = new JSONObject(html);
        String str = macfiljo.getString("macs");
        int index = str.indexOf("[\"");
        int end = str.indexOf("\"]");
        String cut = str.substring(index, end).replace("\"", "").replace("[", "");
        String[] oldArray = cut.split(",");
        if (oldArray.length >= 1) {
            for (int i = 0; i < oldArray.length; i++) {
                macAddressFilter.add(parseToBean(oldArray[i]));
            }
        }
        baseRouterBean.setBlackUsers(macAddressFilter);
        return baseRouterBean;
    }

    @Override
    public BaseRouterBean getFilterRule(String html) {
        return null;
    }


    @Override
    public BaseRouterBean getAllActiveUser(String html) throws JSONException {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        List<WifiUserBean> wifiUsers = new ArrayList<WifiUserBean>();
        JSONObject macjo = new JSONObject(html);
        org.json.JSONArray ja = macjo.getJSONArray("devices");
        for (int i = 0; i < ja.length(); i++) {
            JSONObject j = ja.getJSONObject(i);
            WifiUserBean bean = new WifiUserBean();
            bean.setMacAddress(j.getString("mac"));
            bean.setIpAddress(j.getString("ip"));
            bean.setUserName(j.getString("name"));
            wifiUsers.add(bean);
        }
        baseRouterBean.setActiveUsers(wifiUsers);
        return baseRouterBean;
    }

    @Override
    public BaseRouterBean getRouterSafeAndPassword(String html) throws JSONException {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        RouterSafeAndPasswordBean bean = new RouterSafeAndPasswordBean();
        JSONObject wifisecjo = new JSONObject(html);
        String ja = wifisecjo.getString("encryption");
        bean.setPassword(wifisecjo.getString("wifi_key"));
        if (!"mixed-psk".equals(ja)) {
            bean.setType(0);
        }
        return baseRouterBean;
    }

}
