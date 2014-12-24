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
 * 将返回的html内容转换为javabean
 *
 * @author 狼骑兵
 */
@SuppressLint("DefaultLocale")
public class RouterNetCoreHtmlToBean extends HtmlParseInterface {

    /**
     * 将所有连接的设备列表字符串解析为对象
     */
    @Override
    public BaseRouterBean getDhcpUser(final String html) throws JSONException {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        List<WifiUserBean> wifiUsers = new ArrayList<WifiUserBean>();
        JSONObject jo = new JSONObject(html);
        org.json.JSONArray ja = jo.getJSONArray("dhcp_client_list");
        for (int i = 0; i < ja.length(); i++) {
            JSONObject j = ja.getJSONObject(i);
            WifiUserBean mac = new WifiUserBean();
            mac.setMacAddress(j.getString("mac"));
            mac.setUserName(j.getString("host"));
            mac.setIpAddress(j.getString("ip"));
            wifiUsers.add(mac);
        }
        baseRouterBean.setDhcpUsers(wifiUsers);
        return baseRouterBean;
    }

    @Override
    public BaseRouterBean getAllActiveUser(final String html) throws JSONException {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        List<WifiUserBean> wifiUsers = new ArrayList<WifiUserBean>();
        JSONObject macjo = new JSONObject(html);
        org.json.JSONArray ja = macjo.getJSONArray("wl_link_list");
        for (int i = 0; i < ja.length(); i++) {
            JSONObject j = ja.getJSONObject(i);
            WifiUserBean mac = new WifiUserBean();
            mac.setMacAddress(j.getString("mac"));
            wifiUsers.add(mac);
        }
        baseRouterBean.setActiveUsers(wifiUsers);

        return baseRouterBean;
    }

    @Override
    public BaseRouterBean getAllMacAddressFilter(final String html) throws JSONException {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        List<MacAddressFilterBean> wifiUsers = new ArrayList<MacAddressFilterBean>();
        JSONObject macfiljo = new JSONObject(html);
        org.json.JSONArray ja = macfiljo.getJSONArray("wl_mac_filter_list");
        for (int i = 0; i < ja.length(); i++) {
            JSONObject j = ja.getJSONObject(i);
            MacAddressFilterBean mac = new MacAddressFilterBean();
            mac.setMacAddress(j.getString("macaddr"));
            wifiUsers.add(mac);
        }
        baseRouterBean.setBlackUsers(wifiUsers);
        return baseRouterBean;
    }

    /**
     * 检查过滤规则
     */
    @Override
    public BaseRouterBean getFilterRule(String html) throws JSONException {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        JSONObject macfltjo = new JSONObject(html);
        String enable = macfltjo.getString("wl_mac_filter_enable");
        String rule = macfltjo.getString("wl_mac_filter_rule");
        if ("10".equals(enable + rule)) {
            baseRouterBean.setMacFilterOpen(true);
        } else {
            baseRouterBean.setMacFilterOpen(false);
        }

        return baseRouterBean;
    }

    @Override
    public BaseRouterBean getRouterSafeAndPassword(String html) throws JSONException {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        RouterSafeAndPasswordBean bean = new RouterSafeAndPasswordBean();
        JSONObject wifisecjo = new JSONObject(html);
        String ja = wifisecjo.getString("sec_mode");
        bean.setPassword(wifisecjo.getString("key_wpa"));
        bean.setType(Integer.valueOf(ja));

        baseRouterBean.setRouterSafePassword(bean);
        return baseRouterBean;
    }
}
