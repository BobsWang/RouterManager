package com.wolf.routermanager.urlconstant;


import com.wolf.routermanager.bean.RouterManagerUserBean;
import com.wolf.routermanager.urlconstant.inter.RouterConstantInterface;
import com.wolf.routermanager.htmlparse.RouterASUSHtmlToBean;
import com.wolf.routermanager.htmlparse.inter.HtmlParseInterface;

/**
 * Created by PaulHsu on 2014/12/3.
 */
public class RouterASUSConstant extends RouterConstantInterface {

	public RouterASUSConstant(String BASE_URI) {
		super(BASE_URI);
	}

	@Override
	public HtmlParseInterface getHtmlParser() {
		return new RouterASUSHtmlToBean();
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
		return null;
	}

	@Override
	public String getCheckFilterReferer() {
		return null;
	}

	@Override
	public String getAllMacAddressInFilter() {
		return BASE_URI + "/Advanced_ACL_Content.asp";
	}

	@Override
	public String getAllMacAddressInFilterReferer() {
		return BASE_URI + "/Advanced_ACL_Content.asp";
	}

	@Override
	public String openStopMacAddressFilter() {
		return BASE_URI + "/start_apply.htm";
	}

	@Override
	public String openStopMacAddressFilterReferer() {
		return BASE_URI + "/Advanced_ACL_Content.asp";
	}

	@Override
	public String stopOneMacAddressUri(String macAddress) {
		return BASE_URI + "/start_apply.htm";
	}

	@Override
	public String stopOneMacAddressReferer() {
		return BASE_URI + "/Advanced_ACL_Content.asp";
	}


	@Override
	public String deleteOneMacAddress(String macAddress) {
		return null;
	}

	@Override
	public String deleteOneMacAddressReferer() {
		return null;
	}

	@Override
	public String stopAllMacAddress() {
		return null;
	}

	@Override
	public String stopAllMacAddressReferer() {
		return null;
	}

	@Override
	public String getAllUsers() {
		return null;
	}

	@Override
	public String getAllUsersReferer() {
		return null;
	}

	@Override
	public String getAllActiveUsers(String page) {
		return BASE_URI + "/update_clients.asp?_=1417683742789";
	}

	@Override
	public String getAllActiveUsersReferer() {
		return BASE_URI + "/device-map/clients.asp";
	}

	@Override
	public String rebootRouter() {
		return BASE_URI + "/apply.cgi";
	}

	@Override
	public String rebootRouterReferer() {
		return BASE_URI + "/";
	}

	@Override
	public String modifyLoginPassword(RouterManagerUserBean user) {
		return BASE_URI + "/start_apply.htm";
	}

	@Override
	public String modifyLoginPasswordReferer() {
		return BASE_URI + "/Advanced_System_Content.asp";
	}

	@Override
	public String modifySSid(String ssid) {
		return null;
	}

	@Override
	public String modifySSidReferer() {
		return null;
	}

	@Override
	public String getWifiSafeInfo() {
		return BASE_URI + "/Advanced_Wireless_Content.asp";
	}

	@Override
	public String getWifiSafeInfoReferer() {
		return BASE_URI + "/Advanced_WWPS_Content.asp";
	}

	@Override
	public String modifyWifiPassword(String password) {
		return BASE_URI + "/start_apply2.htm";
	}

	@Override
	public String modifyWifiPasswordReferer() {
		return BASE_URI + "/device-map/router.asp";
	}

	@Override
	public String modifyWiFiChannel(int channel) {
		return BASE_URI + "/start_apply2.htm";
	}

	@Override
	public String modifyWiFiChannelReferer() {
		return BASE_URI + "/Advanced_Wireless_Content.asp";
	}
}
