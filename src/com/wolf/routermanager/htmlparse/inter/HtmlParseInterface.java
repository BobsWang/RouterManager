package com.wolf.routermanager.htmlparse.inter;

import com.wolf.routermanager.bean.BaseRouterBean;
import com.wolf.routermanager.bean.MacAddressFilterBean;
import com.wolf.routermanager.bean.TpLinkRouterBean;
import com.wolf.routermanager.inter.RouterInterface;
import org.json.JSONException;

import java.util.HashMap;

public abstract class HtmlParseInterface {

    public final HashMap<String, Object> getRouterData(String type, String html) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        BaseRouterBean baseBean = new BaseRouterBean();
        try {
            if (RouterInterface.UTIL_GET_ACTIVE_USERS.equals(type)) {
                baseBean = getAllActiveUser(html);
            } else if (RouterInterface.UTIL_GET_DHCP_USERS.equals(type)) {
                baseBean = getDhcpUser(html);
            } else if(RouterInterface.UTIL_GET_BLACK_USERS.equals(type)) {
                baseBean = getAllMacAddressFilter(html);
            } else if(RouterInterface.UTIL_GET_FILTERTYPE.equals(type)) {
                baseBean = getFilterRule(html);
            } else if(RouterInterface.UTIL_GET_ROUTERSAFE.equals(type)) {
                baseBean = getRouterSafeAndPassword(html);
            } else if (RouterInterface.UTIL_GET_WEBCHECK.equals(type)) {
                baseBean = getWebSafeResult(html);
            } else if (RouterInterface.UTIL_GET_DNSCHECK.equals(type)) {
                baseBean = getDnsSafe(html);
            }

            map.put(RouterInterface.SUPPORT, RouterInterface.SUPPORT_YES);
        } catch (Exception e) {
            map.put(RouterInterface.SUPPORT, RouterInterface.SUPPORT_EXCEPTION);
        }
        map.put(RouterInterface.BASEDATA, baseBean);

        return map;
    }


    /**
     * 获取dhcp列表
     */
    protected abstract BaseRouterBean getDhcpUser(String html) throws JSONException;

    /**
     * 获取黑名单
     */
    protected abstract BaseRouterBean getAllMacAddressFilter(String html) throws JSONException;

    /**
     * 获取是否开启了mac过滤功能
     */
    protected abstract BaseRouterBean getFilterRule(String html) throws JSONException;

    /**
     * 获取活动用户
     */
    protected abstract BaseRouterBean getAllActiveUser(String html) throws JSONException;

    /**
     * 获取路由器加密方式和密码
     */
    protected abstract BaseRouterBean getRouterSafeAndPassword(String html) throws JSONException;

    /**
     * 获取远程web安全（默认安全）
     */
    protected BaseRouterBean getWebSafeResult(String html) {
        TpLinkRouterBean bean = new TpLinkRouterBean();
        bean.setWebSafe(true);
        return bean;
    }

    /**
     * dns是否安全 （true为安全）
     */
    protected BaseRouterBean getDnsSafe(String html) {
        TpLinkRouterBean bean = new TpLinkRouterBean();
        bean.setDnsSafe(true);
        return bean;
    }


    protected MacAddressFilterBean parseToBean(String macAddress) {
        MacAddressFilterBean bean = new MacAddressFilterBean();
        bean.setMacAddress(macAddress);
        return bean;
    }
}
