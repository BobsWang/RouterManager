package com.wolf.routermanager.urlconstant;


import com.wolf.routermanager.bean.RouterManagerUserBean;
import com.wolf.routermanager.htmlparse.RouterDLinkHtmlToBean_612;
import com.wolf.routermanager.htmlparse.inter.HtmlParseInterface;
import com.wolf.routermanager.urlconstant.inter.RouterConstantInterface;

/**
 * Dlink路由器的访问constant
 *
 * @author wuwf
 */
public class RouterDlinkConstant_612 extends RouterConstantInterface {
    public RouterDlinkConstant_612(String BASE_URI) {
        super(BASE_URI);
    }

    @Override
    public HtmlParseInterface getHtmlParser() {
        return new RouterDLinkHtmlToBean_612();
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

    @Override
    public String getAllMacAddressInFilter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getAllMacAddressInFilterReferer() {
        // TODO Auto-generated method stub
        return null;
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

    @Override
    public String stopOneMacAddressUri(String macAddress) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String stopOneMacAddressReferer() {
        // TODO Auto-generated method stub
        return null;
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
        return BASE_URI + "/cgi-bin/webproc?getpage=html/index.html&var:menu=statue&var:page=dhcpclients";
    }

    @Override
    public String getAllUsersReferer() {
        return BASE_URI + "/cgi-bin/webproc?getpage=html/index.html&var:menu=statue&var:page=deviceinfo";
    }

    @Override
    public String getAllActiveUsers(String page) {
        return getAllUsers();
    }

    @Override
    public String getAllActiveUsersReferer() {
        return getAllUsersReferer();
    }

    @Override
    public String rebootRouter() {
        return BASE_URI + "/cgi-bin/webproc?getpage=html/page.html&var:language=zh_cn&var:menu=setup&var:page=wizard";
    }

    @Override
    public String rebootRouterReferer() {
        return BASE_URI + "/cgi-bin/webproc";
    }

    @Override
    public String modifyLoginPassword(RouterManagerUserBean user) {
        return BASE_URI + "/cgi-bin/webproc";
    }

    @Override
    public String modifyLoginPasswordReferer() {
        return BASE_URI + "/cgi-bin/webproc?getpage=html/index.html&errorpage=html/main" +
                ".html&var:language=zh_cn&var:menu=tools&var:page=accountpsd&var:login=true";
    }

    @Override
    public String modifySSid(String ssid) {
        return BASE_URI + "/cgi-bin/webproc";
    }

    @Override
    public String modifySSidReferer() {
        return BASE_URI + "/cgi-bin/webproc?getpage=html/index.html&var:menu=wireless&var:page=wireless_basic";
    }

    @Override
    public String getWifiSafeInfo() {
        return BASE_URI + "/cgi-bin/webproc?getpage=html/index.html&var:menu=wireless&var:page=wireless_basic";
    }

    @Override
    public String getWifiSafeInfoReferer() {
        return BASE_URI + "/cgi-bin/webproc?getpage=html/index.html&errorpage=html/main" +
                ".html&var:language=zh_cn&var:menu=wireless&var:page=wireless_basic&var:login=true";
    }

    @Override
    public String modifyWifiPassword(String password) {
        return BASE_URI + "/cgi-bin/webproc";
    }

    @Override
    public String modifyWifiPasswordReferer() {
        return BASE_URI + "/cgi-bin/webproc?getpage=html/index.html&errorpage=html/index" +
                ".html&var:language=zh_cn&var:page=wireless_basic&var:errorpage=wireless_basic&var:menu=wireless";
    }

    @Override
    public String modifyWiFiChannel(int channel) {
        return null;
    }

    @Override
    public String modifyWiFiChannelReferer() {
        return null;
    }

    @Override
    public String getLoginRefer() {
        return BASE_URI + "/cgi-bin/webproc";
    }

    @Override
    public String getLogin() {
        return BASE_URI + "/cgi-bin/webproc";
    }
}
