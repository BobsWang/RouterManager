package com.wolf.routermanager.htmlparse;


import com.wolf.routermanager.bean.BaseRouterBean;
import com.wolf.routermanager.bean.RouterSafeAndPasswordBean;
import com.wolf.routermanager.bean.WifiUserBean;
import com.wolf.routermanager.common.tool.Base64;
import com.wolf.routermanager.htmlparse.inter.HtmlParseInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Dlink路由器的网页解析类
 *
 * @author wuwf
 */
public class RouterDLinkHtmlToBean_612 extends HtmlParseInterface {

    @Override
    public BaseRouterBean getDhcpUser(String html) {
        return null;
    }

    @Override
    public BaseRouterBean getFilterRule(String html) {
        return null;
    }

    @Override
    public BaseRouterBean getAllMacAddressFilter(String html) {
        return null;
    }

    @Override
    public BaseRouterBean getAllActiveUser(final String html) {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        List<WifiUserBean> wifiUsers = new ArrayList<WifiUserBean>();
        int index = html.indexOf("LanHosts[m]");
        int end = html.indexOf("var G_DHCP_WhiteList");
        String cut = html
                .substring(index, end)
                .replace("\n", "")
                .replace("\r", "")
                .replace("\t", "")
                .replace(" ", "")
                .replace("LanHosts[m]=[m+1,", "")
                .replace(
                        "];LanSelect[m]=\"InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.\";++m;",
                        "").replace("//IPAddress", "")
                .replace("//MACAddress", "");

        // "192.168.0.100",
        // "28:cf:e9:04:80:fd".toUpperCase(),
        // strAnsi2Unicode(Base64.Decode("TWluaWRlTWluaQ=="))
        String[] oldArray = cut.split("//HostName");
        for (String oneLine : oldArray) {
            String[] newArray = oneLine.split(",");
            // 是真实的用户数据
            if (newArray.length > 1) {
                WifiUserBean bean = new WifiUserBean();
                bean.setIpAddress(newArray[0].replace("\"", ""));
                bean.setMacAddress(newArray[1].replace("\"", "").replace(
                        ".toUpperCase()", ""));
                String name = newArray[2].replace("strAnsi2Unicode(Base64.Decode(", "").replace(")",
                        "").replace("\"", "");
                bean.setUserName(Base64.decode(name));
                wifiUsers.add(bean);
            }

        }
        baseRouterBean.setActiveUsers(wifiUsers);
        return baseRouterBean;
    }

    @Override
    public BaseRouterBean getRouterSafeAndPassword(String html) {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        RouterSafeAndPasswordBean bean = new RouterSafeAndPasswordBean();
        int begin = html.indexOf("W_BeaconType");
        int end = html.indexOf("var W_BasicEncryptionModes");
        String newString = html.substring(begin, end);
        if (newString.toLowerCase().contains("none")) {
            //不加密
            bean.setType(0);
        }

        begin = html.indexOf("W_PreSharedKey=");
        end = html.indexOf("var WDS_Enable");
        String yuanpassword = html.substring(begin, end);

        String password = yuanpassword.replace("W_PreSharedKey=", "").replace("\"", "").replace("\n",
                "").replace("\r", "").replace(";", "");
        bean.setPassword(password);

        baseRouterBean.setRouterSafePassword(bean);
        return baseRouterBean;
    }

}
