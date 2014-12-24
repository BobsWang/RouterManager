package com.wolf.routermanager.htmlparse;

import com.wolf.routermanager.bean.BaseRouterBean;
import com.wolf.routermanager.bean.MacAddressFilterBean;
import com.wolf.routermanager.bean.RouterSafeAndPasswordBean;
import com.wolf.routermanager.bean.WifiUserBean;
import com.wolf.routermanager.htmlparse.inter.HtmlParseInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * 腾达：将返回的html内容转换为javabean
 *
 * @author wuwf
 */
public class RouterTenDaHtmlToBean extends HtmlParseInterface {

    @Override
    public BaseRouterBean getDhcpUser(String html) {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        List<WifiUserBean> wifiUsers = new ArrayList<WifiUserBean>();
        int tbodyBegin = html.indexOf("dhcpList=new Array");
        int tbodyEnd = html.indexOf("//", tbodyBegin);
        String wantString = html.substring(tbodyBegin, tbodyEnd);
        // 继续截取
        int wantStringBegin = wantString.indexOf("('");
        int wantStringEnd = wantString.indexOf("')");
        String myString = wantString.substring(wantStringBegin,
                wantStringEnd);
        String needString = myString.substring(2, myString.length());
        // 分隔开
        String[] strArray = needString.split("','");
        for (int i = 0; i < strArray.length; i++) {
            WifiUserBean oneUser = new WifiUserBean();
            String[] needArray = strArray[i].split(";");
            oneUser.setUserName(needArray[0]);
            oneUser.setIpAddress(needArray[1]);
            oneUser.setMacAddress(needArray[2]);
            oneUser.setValidTime(needArray[4]);

            wifiUsers.add(oneUser);
        }

        baseRouterBean.setDhcpUsers(wifiUsers);
        return baseRouterBean;
    }

    @Override
    public BaseRouterBean getAllMacAddressFilter(final String html) {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        ArrayList<MacAddressFilterBean> wifiUsers = new ArrayList<MacAddressFilterBean>();
        int begin = html.indexOf("res");
        int firstMao = html.indexOf("\"", begin);
        int twoMao = html.indexOf("\"", firstMao + 1);
        String content = html.substring(firstMao + 1, twoMao);
        String[] shuzu = content.split(" ");
        for (int i = 0; i < shuzu.length; i++) {
            if (shuzu[i].length() > 5) {
                wifiUsers.add(parseToBean(shuzu[i]));
            }
        }
        baseRouterBean.setBlackUsers(wifiUsers);
        return baseRouterBean;
    }

    @Override
    public BaseRouterBean getFilterRule(String html) {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        int begin = html.indexOf("filter_mode");
        int firstMao = html.indexOf("\"", begin);
        int twoMao = html.indexOf("\"", firstMao + 1);
        String content = html.substring(firstMao + 1, twoMao);
        if (content.equals("deny")) {
            baseRouterBean.setMacFilterOpen(true);
        } else {
            baseRouterBean.setMacFilterOpen(false);
        }

        return baseRouterBean;
    }

    @Override
    public BaseRouterBean getAllActiveUser(String html) {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        List<WifiUserBean> wifiUsers = new ArrayList<WifiUserBean>();
            /*
             * int tbodyBegin = html.indexOf("<tbody>"); int tbodyEnd =
			 * html.indexOf("</tbody>"); if (tbodyBegin == -1) { tbodyBegin =
			 * html.indexOf("<TBODY>"); tbodyEnd = html.indexOf("</TBODY>"); }
			 */
        int tbodyBegin = html.indexOf("width=30%");
        int tbodyEnd = html.indexOf("</TBODY>");
        if (tbodyEnd == -1) {
            tbodyEnd = html.indexOf("</tbody>");
        }
        String wantString = html.substring(tbodyBegin, tbodyEnd);
        String[] spitArray = wantString.split("</td>");

        for (String oneRow : spitArray) {
            if (oneRow.contains(":")) {
                WifiUserBean oneUser = new WifiUserBean();
                String oneMacAddress = oneRow.substring(
                        oneRow.length() - 17, oneRow.length());
                oneUser.setMacAddress(oneMacAddress);
                wifiUsers.add(oneUser);
            }
        }
        baseRouterBean.setActiveUsers(wifiUsers);
        return baseRouterBean;
    }

    @Override
    public BaseRouterBean getRouterSafeAndPassword(String html) {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        RouterSafeAndPasswordBean bean = new RouterSafeAndPasswordBean();
        if (html != null) {
            int two = html.lastIndexOf("ASCII");
            int end = html.indexOf("disabled");
            String password = html.substring(two + 5, end);
            bean.setPassword(password);
            if (password.equals("")) {
                bean.setType(0);
            }
        }
        baseRouterBean.setRouterSafePassword(bean);
        return baseRouterBean;
    }

}
