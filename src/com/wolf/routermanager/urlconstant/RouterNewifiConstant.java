package com.wolf.routermanager.urlconstant;


import com.wolf.routermanager.bean.RouterManagerUserBean;
import com.wolf.routermanager.htmlparse.RouterNewifiHtmlToBean;
import com.wolf.routermanager.htmlparse.inter.HtmlParseInterface;
import com.wolf.routermanager.http.RouterNewifiUtil;
import com.wolf.routermanager.urlconstant.inter.RouterConstantInterface;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RouterNewifiConstant extends RouterConstantInterface implements Serializable {

	public RouterNewifiConstant(String BASE_URI) {
		super(BASE_URI);
	}

    @Override
    public HtmlParseInterface getHtmlParser() {
        return new RouterNewifiHtmlToBean();
    }

    @Override
    public String getRouterInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getRouterInfoReferer() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getCheckFilter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getCheckFilterReferer() {
        // TODO Auto-generated method stub
        return null;
    }

    // 黑名单列表
    @Override
    public String getAllMacAddressInFilter() {
        return BASE_URI
                + "/cgi-bin/luci/;stok=9a744dff81ce78424e3e0e0e36917799/admin/private/new_get_lan_device_list";
    }

    // 黑名单列表Referer
    @Override
    public String getAllMacAddressInFilterReferer() {
        return BASE_URI
                + "/cgi-bin/luci/;stok=9a744dff81ce78424e3e0e0e36917799/admin/private/new_get_lan_device_list";

    }

    @Override
    public String openStopMacAddressFilter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String openStopMacAddressFilterReferer() {
        // TODO Auto-generated method stub
        return null;
    }

    // 加入黑名单
    @Override
    public String stopOneMacAddressUri(String macAddress) {
        return BASE_URI
                + "/cgi-bin/luci/;stok=" + RouterNewifiUtil.stokStr + "/admin/private/new_set_macfilter?enable=1&action=1&mac="
                + macAddress;
    }

    // 加入黑名单Referer
    @Override
    public String stopOneMacAddressReferer() {
        return BASE_URI
                + "/cgi-bin/luci/;stok=" + RouterNewifiUtil.stokStr + "/admin/device";
    }

    @Override
    public String deleteOneMacAddress(String macAddress) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String deleteOneMacAddressReferer() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String stopAllMacAddress() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String stopAllMacAddressReferer() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public String getAllUsers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getAllUsersReferer() {
        // TODO Auto-generated method stub
        return null;
    }

    // 获取活动用户列表
    @Override
    public String getAllActiveUsers(String page) {
        return BASE_URI
                + "/cgi-bin/luci/;stok=" + RouterNewifiUtil.stokStr + "/admin/private/new_get_lan_device_list";
    }

    @Override
    public String getAllActiveUsersReferer() {
        return BASE_URI
                + "/cgi-bin/luci/;stok=" + RouterNewifiUtil.stokStr + "/admin/device";
    }

    @Override
    public String rebootRouter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String rebootRouterReferer() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String modifyLoginPassword(RouterManagerUserBean user) {
        return BASE_URI + "/cgi-bin/luci/;stok=" + RouterNewifiUtil.stokStr + "/admin/setting/setpasswd/change";
    }

    @Override
    public String modifyLoginPasswordReferer() {
        return BASE_URI + "/cgi-bin/luci/;stok=" + RouterNewifiUtil.stokStr + "/admin/password";
    }

    // 2.4G修改名称
    @Override
    public String modifySSid(String ssid) {
        return BASE_URI
                + "/cgi-bin/luci/;stok=" + RouterNewifiUtil.stokStr + "/admin/private/set_wifi";
    }

    // 修改名称Referer
    @Override
    public String modifySSidReferer() {
        return BASE_URI
                + "/cgi-bin/luci/;stok=" + RouterNewifiUtil.stokStr + "/admin/wifi_n_setting";
    }

    @Override
    public String getWifiSafeInfo() {
        return BASE_URI + "/cgi-bin/luci/;stok=" + RouterNewifiUtil.stokStr + "/admin/private/get_wifi_info?wifi_type=2";
    }

    @Override
    public String getWifiSafeInfoReferer() {
        return BASE_URI + "/cgi-bin/luci/;stok=" + RouterNewifiUtil.stokStr + "/admin/wifi_n_setting";
    }

    @Override
    public String modifyWifiPassword(String password) {
        return BASE_URI
                + "/cgi-bin/luci/;stok=" + RouterNewifiUtil.stokStr + "/admin/private/set_wifi";
    }

    @Override
    public String modifyWifiPasswordReferer() {
        return BASE_URI
                + "/cgi-bin/luci/;stok=" + RouterNewifiUtil.stokStr + "/admin/wifi_n_setting";
    }

    @Override
    public String modifyWiFiChannel(int channel) {
        return BASE_URI + "/cgi-bin/luci/;stok=" + RouterNewifiUtil.stokStr + "/admin/private/set_wifi";
    }

    @Override
    public String modifyWiFiChannelReferer() {
        return BASE_URI + "/cgi-bin/luci/;stok=" + RouterNewifiUtil.stokStr + "/admin/wifi_n_setting";
    }

    @Override
    public String getLogin() {
        return BASE_URI + "/cgi-bin/luci";
    }

    @Override
    public String getLoginRefer() {
        return BASE_URI + "/cgi-bin/luci";
    }
}
