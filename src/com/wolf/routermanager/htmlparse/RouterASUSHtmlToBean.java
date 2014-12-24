package com.wolf.routermanager.htmlparse;

import com.wolf.routermanager.bean.BaseRouterBean;
import com.wolf.routermanager.bean.MacAddressFilterBean;
import com.wolf.routermanager.bean.RouterSafeAndPasswordBean;
import com.wolf.routermanager.bean.WifiUserBean;
import com.wolf.routermanager.htmlparse.inter.HtmlParseInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PaulHsu on 2014/12/3.
 */
public class RouterASUSHtmlToBean extends HtmlParseInterface {
    @Override
    public BaseRouterBean getDhcpUser(String html) {
        return null;
    }

    @Override
    public BaseRouterBean getAllMacAddressFilter(final String html) {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        List<MacAddressFilterBean> macAddressFilterBeanList = new ArrayList<MacAddressFilterBean>();
        int begin = html.indexOf("array = '");
        int end = html.indexOf(";\n" + "function initial(){");
        String string = html.substring(begin, end);
        string = string.replaceAll("array = '", "").replaceAll("'", "")
                .substring(4);
        String[] strings = string.split("&#60");
        for (String s : strings) {
            if (!"".equals(s) && s != null) {
                macAddressFilterBeanList.add(parseToBean(s));
            }
        }
        baseRouterBean.setBlackUsers(macAddressFilterBeanList);
        return baseRouterBean;
    }

    @Override
    public BaseRouterBean getFilterRule(String html) {
        BaseRouterBean bean = new BaseRouterBean();
        int begin = html.indexOf("name=\"wl_macmode\" value=\"");
        int end = html
                .indexOf("align=\"left\" cellpadding=\"0\" cellspacing=\"0\">");
        String string = html.substring(begin, end);
        if (string.contains("deny")) {
            bean.setMacFilterOpen(true);
        } else {
            bean.setMacFilterOpen(false);
        }

        return bean;
    }

    @Override
    public BaseRouterBean getAllActiveUser(String html) {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        List<WifiUserBean> wifiUsers = new ArrayList<WifiUserBean>();
        int begin = html.indexOf("array = '<");
        int end = html.indexOf("';");
        String string;
        String[] spitArray;
        if (begin == -1) {
            begin = html.indexOf("fromNetworkmapd: '<");
            end = html.indexOf(">0>0>0'.replace(");
            if (end == -1) {
                end = html.indexOf(">0>2>0'.");
            }
            string = html.substring(begin, end);
            string = string.replaceAll("fromNetworkmapd: '", "").replaceAll("<1>", "").replaceAll("<6>", "").trim();
            spitArray = string.split(">0>0>0");

        } else {
            string = html.substring(begin, end);
            string = string.replaceAll("array = '<6>", "").replaceAll("<6>", "").replaceAll("<1>", "").trim();
            spitArray = string.split(",");
        }
        for (String s : spitArray) {
            String[] strings = s.split(">");
            WifiUserBean wifiUserBean = new WifiUserBean();
            wifiUserBean.setUserName(strings[0]);
            wifiUserBean.setIpAddress(strings[1]);
            wifiUserBean.setMacAddress(strings[2].substring(0, 17));
            wifiUsers.add(wifiUserBean);
        }
        baseRouterBean.setActiveUsers(wifiUsers);
        return baseRouterBean;
    }

    @Override
    public BaseRouterBean getRouterSafeAndPassword(String html) {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        RouterSafeAndPasswordBean bean = new RouterSafeAndPasswordBean();
        int pswBegin = html
                .indexOf("wl_wpa_psk.value = decodeURIComponent(");
        int pswEnd = html.indexOf("');\n" + "document.form.wl_key1");
        String pswString = html.substring(pswBegin, pswEnd);
        String password = pswString.replace(
                "wl_wpa_psk.value = decodeURIComponent('", "");
        bean.setPassword(password);
        int typeBegin = html.indexOf("authentication_method_change");
        int typeEnd = html.indexOf("802.1x</option>");
        String typeString = html.substring(typeBegin, typeEnd);
        if (typeString.contains("\"open\" selected")) {
            bean.setType(0);
        } else {
            bean.setType(3);
        }

        baseRouterBean.setRouterSafePassword(bean);
        return baseRouterBean;
    }

}
