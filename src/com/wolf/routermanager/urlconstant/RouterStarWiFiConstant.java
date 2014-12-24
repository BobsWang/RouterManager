package com.wolf.routermanager.urlconstant;


import com.wolf.routermanager.bean.RouterManagerUserBean;
import com.wolf.routermanager.htmlparse.RouterStarWifiHtmlToBean;
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
public class RouterStarWiFiConstant extends RouterConstantInterface implements Serializable  {
	
	public RouterStarWiFiConstant(String BASE_URI) {
		super(BASE_URI);
	}
	
	//检查过滤规则的地址
	@Override
	public String getCheckFilter() {
		return null;
	}
	//检查过滤规则的地址
	@Override
	public String getCheckFilterReferer() {
		return null;
	}

	@Override
	public String getLogin() {
		return BASE_URI + "/home.asp?passwd=HEIMI747";
	}

	//获取所有黑名单中的mac列表集合
	@Override
	public String getAllMacAddressInFilter() {
		return BASE_URI + "/goform/wirelessGetSecurity";
	}
	//获取所有黑名单中的mac列表集合referer
	@Override
	public String getAllMacAddressInFilterReferer() {
		return BASE_URI + "/wireless/wifi.asp";
	}
	
	// 开启无线mac过滤的接口
	@Override
	public String openStopMacAddressFilter() {
		return null;
	}
	//开启无线mac过滤的接口的referer
	@Override
	public String openStopMacAddressFilterReferer() {
		return null;
	}
	
	//根据某个mac地址来停掉它，加入黑名单
	@Override
	public String stopOneMacAddressUri(String macAddress) {
		return BASE_URI + "/goform/wirelessBasic";
	}
//	这个版本升级了拉黑的接口
//	http://192.168.21.1/goform/AddAccessPolicyList?apselect_0=2&newap_text_0=11:22:33:44:55:88
	//停止某个mac地址时的referer
	@Override
	public String stopOneMacAddressReferer() {
		return BASE_URI + "/wireless/wifi.asp";
	}
	

	//设置为禁止列表中所有的mac地址访问本无线网络
	@Override
	public String stopAllMacAddress() {
		return BASE_URI + "/userRpm/WlanMacFilterRpm.htm?Page=1&exclusive=0";
	}
	
	@Override
	public String stopAllMacAddressReferer() {
		return BASE_URI + "/userRpm/WlanMacFilterRpm.htm";
	}

	//所有dhcp连接的用户
	@Override
	public String getAllUsers() {
		return BASE_URI + "/internet/dhcpcliinfo.asp";
	}

	@Override
	public String getAllUsersReferer() {
		return BASE_URI + "/main.asp";
	}

	@Override
	public String getAllActiveUsers(String page) {
		return BASE_URI + "/goform/GetWifiList";
	}

	@Override
	public String getAllActiveUsersReferer() {
		return "";
	}

	@Override
	public String deleteOneMacAddress(String macAddress) {
//		int del = 0;
//		for(int i = 0; i < AllRouterInfoBean.allMacFilterUser.size(); i++) {
//			if(macAddress.equals(AllRouterInfoBean.allMacFilterUser.get(i).getMacAddress())) {
//				del = i;
//				break;
//			}
//		}
//		int page = AllRouterInfoBean.allMacFilterUser.size() / 8 + 1;
		return BASE_URI + "/goform/APDeleteAccessPolicyList";
	}

	@Override
	public String deleteOneMacAddressReferer() {
		return BASE_URI + "/wireless/wifi.asp";
	}

	@Override
	public HtmlParseInterface getHtmlParser() {
		return new RouterStarWifiHtmlToBean();
	}

	@Override
	public String modifyLoginPassword(RouterManagerUserBean user) {
		return BASE_URI + "/goform/wirelessPass";
	}

	@Override
	public String modifyLoginPasswordReferer() {
		return BASE_URI + "/change.asp";
	}

	@Override
	public String modifyWifiPassword(String password) {
		return BASE_URI + "/goform/wirelessPass?passphrase=" + password;
	}

	@Override
	public String modifyWifiPasswordReferer() {
		return BASE_URI + "/change.asp";
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
	public String rebootRouter() {
		return BASE_URI + "/goform/rebootcmd";
	}

	@Override
	public String rebootRouterReferer() {
		return null;
	}

	@Override
	public String modifySSid(String ssid) {
		return BASE_URI + "/goform/wirelessName?mssid_0=" + ssid;
	}

	@Override
	public String modifySSidReferer() {
		return BASE_URI + "/change.asp";
	}

	@Override
	public String getWifiSafeInfo() {
		return BASE_URI + "/goform/wirelessGetSecurity";
	}

	@Override
	public String getWifiSafeInfoReferer() {
		return BASE_URI + "/wireless/wifi.asp";
	}

}
