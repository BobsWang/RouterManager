package com.wolf.routermanager.http;

import android.content.Context;
import com.loopj.android.http.RequestParams;
import com.wolf.routermanager.common.WiFiUtil;
import com.wolf.routermanager.inter.GetDataCallBack;
import com.wolf.routermanager.inter.RouterInterface;
import com.wolf.routermanager.urlconstant.inter.RouterConstantInterface;

/**
 * Created by PaulHsu on 2014/12/4.
 */
public class RouterBuffaloUtil extends RouterTenDaUtil {
	public RouterBuffaloUtil(Context context, RouterConstantInterface constant,
			String username, String password) {
		super(context, constant, username, password);
	}

	@Override
	public void modifyWiFiChannel(int channel, final RouterInterface c) {
		RequestParams params = new RequestParams();
		params.put("GO", "wireless_basic.asp");
		params.put("enablewireless", "1");
		params.put("enablewirelessEx", "1");
		params.put("WirelessT", "0");
		params.put("wirelessmode", "9");
		params.put("bssid_num", "1");
		params.put("ssid", WiFiUtil.getCurrentWifiName(mContext));
		params.put("broadcastssid", "0");
		params.put("ap_isolate", "0");
		params.put("sz11gChannel", channel);
		params.put("n_bandwidth", "0");
		params.put("wmm_capable", "off");
		params.put("apsd_capable", "off");
		params.put("wds_list", "1");
		params.put("mssid_1", "");
		params.put("wds_1", "");
		params.put("ssid_1", "");
		params.put("schannel_1", "");
		params.put("wds_2", "");
		params.put("ssid_2", "");
		params.put("schannel_2", "");
		params.put("wds_3", "");
		params.put("ssid_3", "");
		params.put("schannel_3", "");
		params.put("wds_4", "");
		params.put("ssid_4", "");
		params.put("schannel_4", "");
		httpPost(constant.modifyWiFiChannel(channel),
				constant.modifyWiFiChannelReferer(), params,
				new GetDataCallBack(c) {});
	}
	
	@Override
	public void getRouterDeviceName(final RouterNameCallBack c) {
		c.putName("Buffalo路由器");
	}
}
