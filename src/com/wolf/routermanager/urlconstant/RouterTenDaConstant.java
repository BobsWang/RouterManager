package com.wolf.routermanager.urlconstant;


import com.wolf.routermanager.bean.RouterManagerUserBean;
import com.wolf.routermanager.htmlparse.RouterTenDaHtmlToBean;
import com.wolf.routermanager.htmlparse.inter.HtmlParseInterface;
import com.wolf.routermanager.urlconstant.inter.RouterConstantInterface;

import java.io.Serializable;

/**
 * 腾达常量类
 * 
 * @author wuwf
 * 
 */
public class RouterTenDaConstant extends RouterConstantInterface implements
		Serializable {

	private static final long serialVersionUID = 1L;

	public RouterTenDaConstant(String BASE_URI) {
		super(BASE_URI);
	}

	@Override
	public HtmlParseInterface getHtmlParser() {
		return new RouterTenDaHtmlToBean();
	}

	@Override
	public String getCheckFilter() {
		return BASE_URI + "/wireless_filter.asp";
	}

	@Override
	public String getCheckFilterReferer() {
		return BASE_URI + "/advance.asp";
	}

	@Override
	public String getAllMacAddressInFilter() {
		return BASE_URI + "/wireless_filter.asp";
	}

	@Override
	public String getAllMacAddressInFilterReferer() {
		return BASE_URI + "/advance.asp";
	}

	@Override
	public String openStopMacAddressFilter() {
		return BASE_URI + "/goform/WlanMacFilter";
	}

	@Override
	public String openStopMacAddressFilterReferer() {
		return BASE_URI + "/wireless_filter.asp";
	}

	@Override
	public String stopOneMacAddressUri(String macAddress) {
		return BASE_URI + "/goform/WlanMacFilter";
	}

	@Override
	public String stopOneMacAddressReferer() {
		return BASE_URI + "/wireless_filter.asp";
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
	public String modifyWifiPassword(String password) {
		return BASE_URI + "/goform/WizardHandle";
	}

	@Override
	public String modifyWifiPasswordReferer() {
		return BASE_URI + "/index.asp";
	}

	@Override
	public String modifyWiFiChannel(int channel) {
		return BASE_URI + "/goform/wirelessBasic";
	}

	@Override
	public String modifyWiFiChannelReferer() {
		return BASE_URI + "/wireless_basic.asp";
	}

	@Override
	public String getAllUsers() {
		return BASE_URI + "/lan_dhcp_clients.asp";
	}

	@Override
	public String getAllUsersReferer() {
		return BASE_URI + "/advance.asp";
	}

	@Override
	public String getAllActiveUsers(String page) {
		return BASE_URI + "/wireless_state.asp";
	}

	@Override
	public String getAllActiveUsersReferer() {
		return BASE_URI + "/advance.asp";
	}

	@Override
	public String getWifiSafeInfo() {
		return BASE_URI + "/goform/wirelessGetSecurity";
	}

	@Override
	public String getWifiSafeInfoReferer() {
		return BASE_URI + "/wireless_security.asp";
	}

	@Override
	public String modifyLoginPassword(RouterManagerUserBean user) {
		return BASE_URI + "/LoginCheck";
	}

	@Override
	public String modifyLoginPasswordReferer() {
		return BASE_URI + "/login.asp";
	}

	@Override
	public String getRouterInfo() {
		return BASE_URI + "/system_status.asp";
	}

	@Override
	public String getRouterInfoReferer() {
		return null;
	}

	@Override
	public String rebootRouter() {
		return BASE_URI + "/goform/SysToolReboot";
	}

	@Override
	public String rebootRouterReferer() {
		return BASE_URI + "/system_reboot.asp";
	}

	@Override
	public String modifySSid(String ssid) {
		return BASE_URI + "/goform/wirelessBasic";
	}

	@Override
	public String modifySSidReferer() {
		return BASE_URI + "wireless_basic.asp";
	}
}
