package com.wolf.routermanager.urlconstant;


import com.wolf.routermanager.bean.RouterManagerUserBean;
import com.wolf.routermanager.urlconstant.inter.RouterConstantInterface;
import com.wolf.routermanager.htmlparse.RouterBilianHtmlToBean;
import com.wolf.routermanager.htmlparse.inter.HtmlParseInterface;

/**
 * 必联的网络请求地址类
 * @author wuwf
 */
public class RouterBilianBL410RConstant extends RouterConstantInterface {

    public RouterBilianBL410RConstant(String BASE_URI) {
        super(BASE_URI);
    }

    @Override
    public HtmlParseInterface getHtmlParser() {
        return new RouterBilianHtmlToBean();
    }

    @Override
    public String getRouterInfo() {
        return null;
    }

    @Override
    public String getRouterInfoReferer() {
        return null;
    }

    @Override
    public String getCheckFilter() {
        return BASE_URI + "/wifi_filter.asp";
    }

    @Override
    public String getCheckFilterReferer() {
        return BASE_URI + "/index.asp";
    }

    @Override
    public String getAllMacAddressInFilter() {
        return BASE_URI + "/wifi_filter.asp";
    }

    @Override
    public String getAllMacAddressInFilterReferer() {
        return BASE_URI + "/index.asp";
    }

    @Override
    public String openStopMacAddressFilter() {
        return BASE_URI + "/ssid.asp";
    }

    @Override
    public String openStopMacAddressFilterReferer() {
        return BASE_URI + "/wifi_filter.asp";
    }

    @Override
    public String stopOneMacAddressUri(String macAddress) {
        return BASE_URI + "/ssid.asp";
    }

    @Override
    public String stopOneMacAddressReferer() {
        return BASE_URI + "/wifi_filter.asp";
    }

    @Override
    public String deleteOneMacAddress(String macAddress) {
        return BASE_URI + "/ssid.asp";
    }

    @Override
    public String deleteOneMacAddressReferer() {
        return BASE_URI + "/wifi_filter.asp";
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

    @Override
    public String getAllActiveUsers(String page) {
        return BASE_URI + "/dhcpcliinfo.asp";
    }

    @Override
    public String getAllActiveUsersReferer() {
        return BASE_URI + "/index.asp";
    }

    @Override
    public String rebootRouter() {
        return BASE_URI + "/apply.cgi";
    }

    @Override
    public String rebootRouterReferer() {
        return BASE_URI + "/admreboot.asp";
    }

    @Override
    public String modifyLoginPassword(RouterManagerUserBean user) {
        return BASE_URI + "/apply.cgi";
    }

    @Override
    public String modifyLoginPasswordReferer() {
        return BASE_URI + "/admpassword.asp";
    }

    @Override
    public String getLogin() {
        return BASE_URI + "/LoginForm";
    }

    @Override
    public String getLoginRefer() {
        return BASE_URI + "/login.html";
    }

    @Override
    public String modifySSid(String ssid) {
        return BASE_URI + "/wan.asp";
    }

    @Override
    public String modifySSidReferer() {
        return BASE_URI + "/setup.asp";
    }

    @Override
    public String getWifiSafeInfo() {
        return BASE_URI + "/setup.asp";
    }

    @Override
    public String getWifiSafeInfoReferer() {
    	return BASE_URI + "/index.asp";
    }

    @Override
    public String modifyWifiPassword(String password) {
    	return BASE_URI + "/wan.asp";
    }

    @Override
    public String modifyWifiPasswordReferer() {
    	return BASE_URI + "/setup.asp";
    }

    @Override
    public String modifyWiFiChannel(int channel) {
        return BASE_URI + "/radio.asp";
    }

    @Override
    public String modifyWiFiChannelReferer() {
        return BASE_URI + "/basic.asp";
    }

}
