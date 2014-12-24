package com.wolf.routermanager.htmlparse;

import android.annotation.SuppressLint;
import android.util.Log;
import com.wolf.routermanager.bean.BaseRouterBean;
import com.wolf.routermanager.bean.MacAddressFilterBean;
import com.wolf.routermanager.bean.RouterSafeAndPasswordBean;
import com.wolf.routermanager.bean.StarWifiConfigBean;
import com.wolf.routermanager.bean.WifiUserBean;
import com.wolf.routermanager.htmlparse.inter.HtmlParseInterface;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 将返回的html内容转换为javabean
 *
 * @author 狼骑兵
 */
@SuppressLint("DefaultLocale")
public class RouterStarWifiHtmlToBean extends HtmlParseInterface {

    /**
     * 将所有连接的设备列表字符串解析为对象
     */
    @Override
    public BaseRouterBean getDhcpUser(String html) {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        List<WifiUserBean> wifiUsers = new ArrayList<WifiUserBean>();
        // 把整个html一行一行分离
        String[] allRowsStrings = html.split("\r\n");
        List<String> needStrings = new ArrayList<String>();
        for (String oneRow : allRowsStrings) {
            // 如果是我们需要的那些行
            // <tr><td>android-a492162
            // </td><td>18:DC:56:7C:24:0C</td><td>192.168.21.2</td><td>19:40:54
            if (oneRow.length() >= oneRow.replace("td", "").length() + 12) {
                String[] a = oneRow.split("\n");
                for (int i = 0; i < a.length; i++) {
                    needStrings.add(a[i].replace(" ", "")
                            .replace("<tr>", "").replace("</td>", ""));
                }

            }
        }
        for (String string : needStrings) {
            String[] atts = string.split("<td>");
            if (atts.length > 4) {
                WifiUserBean userBean = new WifiUserBean();
                userBean.setUserName(atts[1]);
                userBean.setMacAddress(atts[2]);
                userBean.setIpAddress(atts[3]);
                userBean.setValidTime(atts[4]);
                wifiUsers.add(userBean);
            }
        }

        baseRouterBean.setDhcpUsers(wifiUsers);
        return baseRouterBean;
    }

    @Override
    public BaseRouterBean getAllActiveUser(final String html) {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        List<WifiUserBean> wifiUsers = new ArrayList<WifiUserBean>();
        // 老版的盒子不支持该功能，直接返回count为0
        if (html.replace(" ", "").contains("isnot")) {
            baseRouterBean.setActiveUsers(wifiUsers);
            return baseRouterBean;
        }

        String[] array = html.split(";");
        for (String one : array) {
            WifiUserBean bean = new WifiUserBean();
            String[] atts = one.split(",");
            bean.setUserName(atts[0]);
            bean.setMacAddress(atts[1]);
            bean.setIpAddress(atts[2]);

            wifiUsers.add(bean);
        }

        baseRouterBean.setActiveUsers(wifiUsers);
        return baseRouterBean;
    }

    /**
     * 获取盒子的wifi设置页面的所有信息
     */
    public StarWifiConfigBean getStarWifiConfigBean(String html) {
        StarWifiConfigBean configBean = new StarWifiConfigBean();

        try {
            String[] str = html.replace("\n", "\r").split("\r");
            configBean.RadiusServerPort = str[20];
            configBean.RadiusServerSecret = str[21];
            configBean.keyRenewalInterval = str[16];
            configBean.mssid_0 = str[1];
            configBean.mssid_1 = str[26];
            configBean.passphrase = str[14];
            configBean.security_mode = str[28];
            List<MacAddressFilterBean> wifiUsers = new ArrayList<MacAddressFilterBean>();

            if (str.length > 24 && str[24].length() > 1) {
                String allMacString = str[24];
                String[] oneMacStrings = allMacString.split(";");
                if (oneMacStrings.length > 0) {
                    for (String oneMac : oneMacStrings) {
                        wifiUsers.add(parseToBean(oneMac));
                    }
                }
            }
            configBean.macs = wifiUsers;
        } catch (Exception e) {
            Log.e("RouterStarWifiHtmlToBean-getStarWifiConfigBean",
                    e.getMessage());
        }
        return configBean;
    }

    @Override
    public BaseRouterBean getAllMacAddressFilter(final String html) {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        List<MacAddressFilterBean> wifiUsers = new ArrayList<MacAddressFilterBean>();
        String[] str = html.replace("\n", "\r").split("\r");
        if (str.length > 24) {
            String allMacString = str[24];
            String[] oneMacStrings = allMacString.split(";");
            if (oneMacStrings.length > 0) {
                for (String oneMac : oneMacStrings) {
                    wifiUsers.add(parseToBean(oneMac));
                }
            }
        }
        baseRouterBean.setBlackUsers(wifiUsers);
        return baseRouterBean;
    }


    @Override
    protected BaseRouterBean getFilterRule(String html) throws JSONException {
        return null;
    }

    @Override
    public BaseRouterBean getRouterSafeAndPassword(String html) {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        RouterSafeAndPasswordBean bean = new RouterSafeAndPasswordBean();
        String[] str = html.replace("\n", "\r").split("\r");
        bean.setPassword(str[14]);

        baseRouterBean.setRouterSafePassword(bean);
        return baseRouterBean;
    }

}
