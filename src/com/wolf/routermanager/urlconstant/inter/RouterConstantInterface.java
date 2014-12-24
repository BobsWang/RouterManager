package com.wolf.routermanager.urlconstant.inter;

import com.wolf.routermanager.bean.RouterManagerUserBean;
import com.wolf.routermanager.htmlparse.inter.HtmlParseInterface;

/**
 * 路由器访问地址功能接口
 *
 * @author wuwf
 */
public abstract class RouterConstantInterface {
    protected String BASE_URI;

    public RouterConstantInterface(String baseUri) {
        this.BASE_URI = baseUri;
    }

    public String getBaseUri() {
        return BASE_URI;
    }

    // 解析html文件的解析类
    public abstract HtmlParseInterface getHtmlParser();

    // 检查过滤规则的地址
    public abstract String getCheckFilter();

    // 检查过滤规则的地址referer
    public abstract String getCheckFilterReferer();

    // 获取所有黑名单中的mac列表集合
    public abstract String getAllMacAddressInFilter();

    // 获取所有黑名单中的mac列表集合referer
    public abstract String getAllMacAddressInFilterReferer();

    // 开启无线mac过滤的接口
    public abstract String openStopMacAddressFilter();

    // 开启无线mac过滤的接口的referer
    public abstract String openStopMacAddressFilterReferer();

    // 根据某个mac地址来停掉它，加入黑名单
    public abstract String stopOneMacAddressUri(String macAddress);

    // 停止某个mac地址时的referer
    public abstract String stopOneMacAddressReferer();

    // 从mac地址表中删除某个mac地址
    public abstract String deleteOneMacAddress(String macAddress);

    public abstract String deleteOneMacAddressReferer();

    // 设置为禁止列表中所有的mac地址访问本无线网络
    public abstract String stopAllMacAddress();

    public abstract String stopAllMacAddressReferer();

    // 所有连接的用户
    public abstract String getAllUsers();

    public abstract String getAllUsersReferer();

    // 所有活动用户
    public abstract String getAllActiveUsers(String page);

    public abstract String getAllActiveUsersReferer();

    /**
     * 登录地址
     */
    public String getLogin() {
        return null;
    }

    public String getLoginRefer() {
        return null;
    }

    // 路由器基本信息
    public String getRouterInfo() {
        return null;
    }

    public String getRouterInfoReferer() {
        return null;
    }

    // 检查dns是否安全
    public String getDnsSafeInfo() {
        return null;
    }

    public String getDnsSafeInfoReferer() {
        return null;
    }

    public String setDnsToSafe() {
        return null;
    }

    public String setDnsToSafeReferer() {
        return null;
    }

    // 检查远程WEB管理
    public String getCheckWeb() {
        return null;
    }

    public String getCheckWebReferer() {
        return null;
    }

    // 远程WEB管理设置
    public String getCheckWebSetting() {
        return null;
    }

    public String getCheckWebSettingReferer() {
        return null;
    }

    //重启路由器
    public abstract String rebootRouter();

    public abstract String rebootRouterReferer();

    // 修改管理员账号密码
    public abstract String modifyLoginPassword(RouterManagerUserBean user);

    public abstract String modifyLoginPasswordReferer();

    //修改wifi的ssid，会重启路由
    public abstract String modifySSid(String ssid);

    public abstract String modifySSidReferer();

    //获取wifi密码和加密方式
    public abstract String getWifiSafeInfo();

    public abstract String getWifiSafeInfoReferer();

    // 修改wifi密码
    public abstract String modifyWifiPassword(String password);

    public abstract String modifyWifiPasswordReferer();

    //修改wifi信道
    public abstract String modifyWiFiChannel(int channel);

    public abstract String modifyWiFiChannelReferer();

}
