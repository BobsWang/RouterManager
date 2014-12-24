package com.wolf.routermanager.urlconstant;

import com.wolf.routermanager.bean.RouterManagerUserBean;
import com.wolf.routermanager.htmlparse.RouterPolarHtmlToBean;
import com.wolf.routermanager.htmlparse.inter.HtmlParseInterface;
import com.wolf.routermanager.http.RouterPolarUtil;
import com.wolf.routermanager.urlconstant.inter.RouterConstantInterface;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RouterPolarConstant extends RouterConstantInterface implements
		Serializable {

	public RouterPolarConstant(String BASE_URI) {
		super(BASE_URI);
	}

	@Override
	public HtmlParseInterface getHtmlParser() {
		return new RouterPolarHtmlToBean();
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
		return BASE_URI + "/cgi-bin/turbo/;stok=" + RouterPolarUtil.stokStr
				+ "/api/wifi/get_mac_filter_list?device=radio0.network1";
	}

	@Override
	public String getAllMacAddressInFilterReferer() {
		return BASE_URI + "/cgi-bin/turbo/;stok=" + RouterPolarUtil.stokStr
				+ "/admin_web/wifi/setup/mac_filter";
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
		return BASE_URI + "/cgi-bin/turbo/;stok=" + RouterPolarUtil.stokStr
				+ "/api/wifi/set_mac_filter?" + macAddress;
	}

	@Override
	public String stopOneMacAddressReferer() {
		return BASE_URI + "/cgi-bin/turbo/;stok=" + RouterPolarUtil.stokStr
				+ "/admin_web/wifi/setup/mac_filter";
	}

	@Override
	public String deleteOneMacAddress(String macAddress) {
		return BASE_URI + "/cgi-bin/turbo/;stok=" + RouterPolarUtil.stokStr
				+ "/api/wifi/set_mac_filter?" + macAddress;
	}

	@Override
	public String deleteOneMacAddressReferer() {
		return BASE_URI + "/cgi-bin/turbo/;stok=" + RouterPolarUtil.stokStr
				+ "/admin_web/wifi/setup/mac_filter";
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

	/**
	 * 所有连接的用户
	 */
	@Override
	public String getAllUsers() {
		return BASE_URI + "/cgi-bin/turbo/;stok=_stok/api/network/device_list";
	}

	@Override
	public String getAllUsersReferer() {
		return BASE_URI
				+ "/cgi-bin/turbo/;stok=_stok/admin_web/network/devices_list";
	}

	@Override
	public String getAllActiveUsers(String page) {
		return BASE_URI + "/cgi-bin/turbo/;stok=" + page
				+ "/api/network/device_list";
	}

	@Override
	public String getAllActiveUsersReferer() {
		return BASE_URI + "/cgi-bin/turbo/;stok=" + RouterPolarUtil.stokStr
				+ "/admin_web/network/devices_list";
	}

	@Override
	public String rebootRouter() {
		return BASE_URI + "/cgi-bin/turbo/;stok=" + RouterPolarUtil.stokStr
				+ "/api/system/reboot";
	}

	@Override
	public String rebootRouterReferer() {
		return BASE_URI + "/cgi-bin/turbo/admin_web";
	}

	@Override
	public String modifyLoginPassword(RouterManagerUserBean user) {
		return BASE_URI + "/cgi-bin/turbo/;stok=" + RouterPolarUtil.stokStr
				+ "/api/system/set_sys_password";
	}

	@Override
	public String modifyLoginPasswordReferer() {
		return BASE_URI + "/cgi-bin/turbo/;stok=" + RouterPolarUtil.stokStr
				+ "/admin_web/system";
	}

	@Override
	public String modifySSid(String ssid) {
		return BASE_URI + "/cgi-bin/turbo/;stok=" + RouterPolarUtil.stokStr
				+ "/api/wifi/set_base?" + ssid;

	}

	@Override
	public String modifySSidReferer() {
		return BASE_URI + "/cgi-bin/turbo/;stok=" + RouterPolarUtil.stokStr
				+ "/admin_web/wifi?guide_mode=0";

	}

	@Override
	public String getWifiSafeInfo() {
		return BASE_URI + "/cgi-bin/turbo/;stok=" + RouterPolarUtil.stokStr
				+ "/api/wifi/view_detail?device=radio0.network1";
	}

	@Override
	public String getWifiSafeInfoReferer() {
		return BASE_URI + "/cgi-bin/turbo/;stok=" + RouterPolarUtil.stokStr
				+ "/admin_web/wifi?guide_mode=0";
	}

	@Override
	public String modifyWifiPassword(String password) {
		return BASE_URI + "/cgi-bin/turbo/;stok=" + RouterPolarUtil.stokStr
				+ "/api/wifi/set_base?" + password;
	}

	@Override
	public String modifyWifiPasswordReferer() {
		return BASE_URI + "/cgi-bin/turbo/;stok=" + RouterPolarUtil.stokStr
				+ "/admin_web/wifi?guide_mode=0";
	}

	@Override
	public String modifyWiFiChannel(int channel) {
		return BASE_URI + "/cgi-bin/turbo/;stok=" + RouterPolarUtil.stokStr
				+ "/api/wifi/set_channel?channel=" + channel
				+ "&txpwr=max&old_txpwr=max&device=radio0.network1";
	}

	@Override
	public String modifyWiFiChannelReferer() {
		return BASE_URI + "/cgi-bin/turbo/;stok=" + RouterPolarUtil.stokStr
				+ "/admin_web/wifi/setup/channel";
	}

	@Override
	public String getLogin() {
		return BASE_URI + "/cgi-bin/turbo/admin_web";
	}

	@Override
	public String getLoginRefer() {
		return BASE_URI + "/login_web.html";
	}
}
