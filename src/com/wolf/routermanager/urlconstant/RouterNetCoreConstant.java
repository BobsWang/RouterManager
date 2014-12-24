package com.wolf.routermanager.urlconstant;


import com.wolf.routermanager.bean.RouterManagerUserBean;
import com.wolf.routermanager.htmlparse.RouterNetCoreHtmlToBean;
import com.wolf.routermanager.htmlparse.inter.HtmlParseInterface;
import com.wolf.routermanager.urlconstant.inter.RouterConstantInterface;

import java.io.Serializable;

public class RouterNetCoreConstant extends RouterConstantInterface implements Serializable {
	private static final long serialVersionUID = 1L;

	private String ADDRESS = "/cgi-bin-igd/netcore_get.cgi";

	private String SETADDRESS = "/cgi-bin-igd/netcore_set.cgi";

	public RouterNetCoreConstant(String BASE_URI) {
		super(BASE_URI);
	}

	@Override
	public HtmlParseInterface getHtmlParser() {
		return new RouterNetCoreHtmlToBean();
	}

	@Override
	public String getRouterInfo() {
		return BASE_URI + ADDRESS;
	}

	@Override
	public String getRouterInfoReferer() {
		return BASE_URI + "/index.htm";
	}

	@Override
	public String getCheckFilter() {
		return BASE_URI + ADDRESS;
	}

	@Override
	public String getCheckFilterReferer() {
		return BASE_URI + "/index.htm";
	}

	@Override
	public String openStopMacAddressFilter() {
		return BASE_URI
				+ SETADDRESS
				+ "?mode_name=netcore_set&wl_mac_filter_enable=1&wl_mac_filter_rule=0&save_wl_mac_filter=save&save=save";
	}

	@Override
	public String openStopMacAddressFilterReferer() {
		return BASE_URI + "/index.htm";
	}

	@Override
	public String stopOneMacAddressUri(String macAddress) {
		return BASE_URI + SETADDRESS + "?mode_name=netcore_set&macaddr="
				+ macAddress + "&wl_mac_filter_rule=0&add_wl_mac=add&save=save";
	}

	@Override
	public String stopOneMacAddressReferer() {
		return BASE_URI + "/index.htm";
	}

	@Override
	public String deleteOneMacAddress(String macAddress) {
		return BASE_URI + SETADDRESS
				+ "?mode_name=netcore_set&save=del&id=1&macaddr=" + macAddress;
	}

	@Override
	public String deleteOneMacAddressReferer() {
		return BASE_URI + "/index.htm";
	}

	@Override
	public String getWifiSafeInfo() {
		return BASE_URI + ADDRESS;
	}

	@Override
	public String getWifiSafeInfoReferer() {
		return BASE_URI + "/index.htm";
	}

	@Override
	public String modifySSid(String ssid) {
		return BASE_URI + SETADDRESS + "?mode_name=netcore_set&wl_enable=1&ssid=" + ssid
				+ "&save_wl_base=save";
	}

	@Override
	public String modifySSidReferer() {
		return BASE_URI + "/index.htm";
	}

	@Override
	public String modifyWifiPassword(String password) {
		return BASE_URI
				+ SETADDRESS
				+ "?mode_name=netcore_set&sec_mode=4&key_type=3&key_mode_wpa=1&key_wpa="
				+ password + "&key_time=86400&save_wl_security=save";
	}

	@Override
	public String modifyWifiPasswordReferer() {
		return BASE_URI + "/index.htm";
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
	public String rebootRouter() {
		return BASE_URI + SETADDRESS + "?mode_name=netcore_set&reboot=1";
	}

	@Override
	public String rebootRouterReferer() {
		return BASE_URI + "/index.htm";
	}

	@Override
	public String modifyLoginPassword(RouterManagerUserBean user) {
		return BASE_URI + SETADDRESS
				+ "?mode_name=netcore_set&new_user=admin&new_pwd="
				+ user.getNewPassword() + "&new_pwd_confirm="
				+ user.getNewPassword() + "&save_passwd=save";
	}

	@Override
	public String modifyLoginPasswordReferer() {
		return BASE_URI + "/index.htm";
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAllActiveUsersReferer() {
		// TODO Auto-generated method stub
		return null;
	}

}
