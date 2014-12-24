package com.wolf.routermanager.htmlparse;


import com.wolf.routermanager.bean.BaseRouterBean;
import com.wolf.routermanager.bean.MacAddressFilterBean;
import com.wolf.routermanager.bean.RouterSafeAndPasswordBean;
import com.wolf.routermanager.bean.WifiUserBean;
import com.wolf.routermanager.htmlparse.inter.HtmlParseInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PaulHsu on 2014/11/6.
 */
public class RouterBilianHtmlToBean extends HtmlParseInterface {
    @Override
    public BaseRouterBean getDhcpUser(String html) {
        return null;
    }

    @Override
    public BaseRouterBean getAllMacAddressFilter(final String html) {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        List<MacAddressFilterBean> macAddressFilter = new ArrayList<MacAddressFilterBean>();
        int index = html.indexOf("var res = ");
        int end = html.indexOf("var enablewireless");
        String cut = html.substring(index, end).replace("\"", "").replace("\n", "").replace(";", "").replace("var res = ", "").replace("\r", "").replace(" ", ",");
        if (cut.length() < 8) {
            baseRouterBean.setBlackUsers(macAddressFilter);
            return baseRouterBean;
        }
        String[] oldArray = cut.split(",");
        // 是真实的用户数据
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
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        int index = html.indexOf("var filter_mode");
        String cut = html.substring(index, index + 30);
        if (cut.contains("deny")) {
            baseRouterBean.setMacFilterOpen(true);
        } else {
            baseRouterBean.setMacFilterOpen(false);
        }
        return baseRouterBean;
    }

    @Override
    public BaseRouterBean getAllActiveUser(final String html) {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        List<WifiUserBean> wifiUsers = new ArrayList<WifiUserBean>();

        int index = html.indexOf("m += '<tr>");
        int end = html.indexOf("m += '</table>'");
        String cut = html.substring(index, end).replace(" ", "")
                .replace("m+='", "").replace("<tr><tdnowrap>", ";")
                .replace("</td><td>", ",").replace("</td></tr>", "").replace("\"", "").replace("\r", "");

        String[] oldArray = cut.split(";");
        for (String oneLine : oldArray) {
            String[] newArray = oneLine.split(",");
            // 是真实的用户数据
            if (newArray.length > 1) {
                WifiUserBean bean = new WifiUserBean();
                bean.setUserName(newArray[0].replace("\n", ""));
                bean.setMacAddress(newArray[1]);
                bean.setIpAddress(newArray[2]);
                if ("过期".equals(newArray[3])) {
                    continue;
                }
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
        int begin = html.indexOf("maxlength=\"64\"");
        int end = html.indexOf("></td>", begin);
        String s = html.substring(begin, end);
        //size=20 maxlength="64" value="12345678"></td>
        String password = s.replace("maxlength=\"64\"", "").replace("value=", "").replace(" ", "").replace("\"", "");
        bean.setPassword(password);

        baseRouterBean.setRouterSafePassword(bean);
        return baseRouterBean;
    }
}
