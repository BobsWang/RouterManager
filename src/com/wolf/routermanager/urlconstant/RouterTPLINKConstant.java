package com.wolf.routermanager.urlconstant;


import com.wolf.routermanager.bean.AllRouterInfoBean;
import com.wolf.routermanager.bean.RouterManagerUserBean;
import com.wolf.routermanager.htmlparse.RouterTPLinkHtmlToBean;
import com.wolf.routermanager.htmlparse.inter.HtmlParseInterface;
import com.wolf.routermanager.urlconstant.inter.RouterConstantInterface;

import java.io.Serializable;

/**
 * 常量类
 * 
 * @author 狼骑兵
 * @version
 */
@SuppressWarnings("serial")
public class RouterTPLINKConstant extends RouterConstantInterface implements
		Serializable {
	public RouterTPLINKConstant(String BASE_URI) {
		super(BASE_URI);
	}

	// 检查过滤规则的地址
	@Override
	public String getCheckFilter() {
		return BASE_URI + "/userRpm/WlanMacFilterRpm.htm";
	}

	// 检查过滤规则的地址
	@Override
	public String getCheckFilterReferer() {
		return BASE_URI + "/userRpm/WlanMacFilterRpm.htm?Page=1";
	}

	// 获取所有黑名单中的mac列表集合
	@Override
	public String getAllMacAddressInFilter() {
		return BASE_URI + "/userRpm/WlanMacFilterRpm.htm";
	}

	// 获取所有黑名单中的mac列表集合referer
	@Override
	public String getAllMacAddressInFilterReferer() {
		return BASE_URI + "/userRpm/MenuRpm.htm";
	}

	// 开启无线mac过滤的接口
	@Override
	public String openStopMacAddressFilter() {
		return BASE_URI + "/userRpm/WlanMacFilterRpm.htm?Page=1&Enfilter=1";
	}

	// 开启无线mac过滤的接口的referer
	@Override
	public String openStopMacAddressFilterReferer() {
		return BASE_URI + "/userRpm/WlanMacFilterRpm.htm?Page=1&Disfilter=1";
	}

	// 根据某个mac地址来停掉它，加入黑名单
	@Override
	public String stopOneMacAddressUri(String macAddress) {
		return BASE_URI
				+ "/userRpm/WlanMacFilterRpm.htm?Desc=&Type=1&entryEnabled=1&Changed=0&SelIndex=0&Page=1&Save=%B1%A3+%B4%E6&Mac="
				+ macAddress;
	}

	// 停止某个mac地址时的referer
	@Override
	public String stopOneMacAddressReferer() {
		return BASE_URI + "/userRpm/WlanMacFilterRpm.htm?Add=Add&Page=1";
	}


	// 设置为禁止列表中所有的mac地址访问本无线网络
	@Override
	public String stopAllMacAddress() {
		return BASE_URI + "/userRpm/WlanMacFilterRpm.htm?Page=1&exclusive=0";
	}

	@Override
	public String stopAllMacAddressReferer() {
		return BASE_URI + "/userRpm/WlanMacFilterRpm.htm";
	}

	// 所有连接的用户
	@Override
	public String getAllUsers() {
		return BASE_URI + "/userRpm/AssignedIpAddrListRpm.htm";
	}

	@Override
	public String getAllUsersReferer() {
		return BASE_URI + "/userRpm/MenuRpm.htm";
	}

	@Override
	public String getAllActiveUsers(String page) {
		if (page == null) {
			page = "1";
		}
		return BASE_URI + "/userRpm/WlanStationRpm.htm?Page=" + page;
	}

	@Override
	public String getAllActiveUsersReferer() {
		return BASE_URI + "/userRpm/WlanStationRpm.htm";
	}

	@Override
	public String deleteOneMacAddress(String macAddress) {
		int del = 0;
		for (int i = 0; i < AllRouterInfoBean.allMacFilterUser.size(); i++) {
			if (macAddress.equals(AllRouterInfoBean.allMacFilterUser.get(i)
					.getMacAddress())) {
				del = i;
				break;
			}
		}
		int page = AllRouterInfoBean.allMacFilterUser.size() / 8 + 1;
		return BASE_URI + "/userRpm/WlanMacFilterRpm.htm?Page=" + page
				+ "&Del=" + del;
	}

	@Override
	public String deleteOneMacAddressReferer() {
		return BASE_URI + "/userRpm/WlanMacFilterRpm.htm";
	}

	@Override
	public HtmlParseInterface getHtmlParser() {
		return new RouterTPLinkHtmlToBean();
	}

	@Override
	public String modifyLoginPassword(RouterManagerUserBean user) {
		return BASE_URI
				+ "/userRpm/ChangeLoginPwdRpm.htm?Save=%B1%A3+%B4%E6&oldname=admin&newname=admin&oldpassword="
				+ user.getOldPassword() + "&newpassword="
				+ user.getNewPassword() + "&newpassword2="
				+ user.getNewPassword();
	}

	// (Request-Line) GET
	// /userRpm/ChangeLoginPwdRpm.htm?oldpassword=12345678&newpassword=123456&newpassword2=123456&Save=%B1%A3+%B4%E6
	@Override
	public String modifyLoginPasswordReferer() {
		return BASE_URI + "/userRpm/ChangeLoginPwdRpm.htm";
	}

	@Override
	public String modifyWifiPassword(String password) {
		return BASE_URI
				+ "/userRpm/WlanSecurityRpm.htm?secType=3&pskSecOpt=3&pskCipher=3&interval=86400&Save=%B1%A3+%B4%E6&pskSecret="
				+ password;
	}

	@Override
	public String modifyWifiPasswordReferer() {
		return BASE_URI + "/userRpm/WlanSecurityRpm.htm";
	}

	@Override
	public String modifyWiFiChannel(int channel) {
		return BASE_URI + "/userRpm/WlanNetworkRpm.htm?wlMode=2&channel="
				+ channel
				+ "&mode=5&chanWidth=2&ap=1&broadcast=2&Save=%B1%A3+%B4%E6";
	}

	@Override
	public String modifyWiFiChannelReferer() {
		return BASE_URI + "/userRpm/WlanNetworkRpm.htm";
	}

	@Override
	public String getDnsSafeInfo() {
		return BASE_URI + "/userRpm/WanDynamicIpCfgRpm.htm";
	}

	@Override
	public String getDnsSafeInfoReferer() {
		return BASE_URI + "/userRpm/WanCfgRpm.htm";
	}

	@Override
	public String getCheckWeb() {
		return BASE_URI + "/userRpm/ManageControlRpm.htm";
	}

	@Override
	public String getCheckWebReferer() {
		return BASE_URI + "/userRpm/MenuRpm.htm";
	}

	@Override
	public String getCheckWebSetting() {
		return BASE_URI
				+ "/userRpm/ManageControlRpm.htm?Save=%C8%B7+%B6%A8&port=80&ip=172.168.1.1&enable=2";
	}

	@Override
	public String getCheckWebSettingReferer() {
		return BASE_URI + "/userRpm/ManageControlRpm.htm";
	}

	@Override
	public String rebootRouter() {
		return BASE_URI
				+ "/userRpm/SysRebootRpm.htm?Reboot=%D6%D8%C6%F4%C2%B7%D3%C9%C6%F7";
	}

	@Override
	public String rebootRouterReferer() {
		return BASE_URI + "/userRpm/SysRebootRpm.htm";
	}

	@Override
	public String modifySSid(String ssid) {
		return BASE_URI
				+ "/userRpm/WlanNetworkRpm.htm?wlMode=2&channel=0&mode=5&chanWidth=2&ap=1&broadcast=2&brlssid=&brlbssid=&detctwds=1&keytype=1&wepindex=1&keytext=&Save=%B1%A3+%B4%E6&ssid1="
				+ ssid;
	}

	@Override
	public String modifySSidReferer() {
		return BASE_URI + "/userRpm/WlanNetworkRpm.htm";
	}

	@Override
	public String getWifiSafeInfo() {
		return BASE_URI + "/userRpm/WlanSecurityRpm.htm";
	}

	@Override
	public String getWifiSafeInfoReferer() {
		return BASE_URI + "/userRpm/MenuRpm.htm";
	}

	@Override
	public String setDnsToSafe() {
		return BASE_URI + "/userRpm/WanDynamicIpCfgRpm.htm?Save=%B1%A3+%B4%E6";
	}

	@Override
	public String setDnsToSafeReferer() {
		return BASE_URI + "/userRpm/WanDynamicIpCfgRpm.htm?wan=0";
	}

}
