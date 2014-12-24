package com.wolf.routermanager.http;

import android.content.Context;
import com.loopj.android.http.RequestParams;
import com.wolf.routermanager.bean.RouterManagerUserBean;
import com.wolf.routermanager.http.inter.RouterUtilInterface;
import com.wolf.routermanager.inter.ConnInfoCallBack;
import com.wolf.routermanager.inter.ContextCallBack;
import com.wolf.routermanager.inter.GetDataCallBack;
import com.wolf.routermanager.inter.RouterInterface;
import com.wolf.routermanager.urlconstant.inter.RouterConstantInterface;

import java.util.HashMap;

/**
 * starwifi路由器各接口功能
 * 
 * @author wuwf
 * 
 */
public class RouterNetCoreUtil extends RouterUtilInterface {

	private final int CONNECT_LIST_FLAG = 1;
	private final int DHCP_LIST_FLAG = 2;
	private final int MAC_FILTER_LIST_FLAG = 3;

	public RouterNetCoreUtil(Context context, RouterConstantInterface constant,
							 String username, String password) {
		super(context, constant, username, password);
	}

	/**
	 * 请求dhcp数据操作
	 */
	public void getData(final ContextCallBack c, final int flag) {
		final RequestParams params = new RequestParams();
		params.put("mode_name", "netcore_get");
		params.put("no", "no");

		httpPost(constant.getRouterInfo(), constant.getRouterInfoReferer(),
				params, new GetDataCallBack(c) {
					@SuppressWarnings("unchecked")
					@Override
					public void success(final String content) {
						if (flag == DHCP_LIST_FLAG) {
							c.putData(constant.getHtmlParser().getRouterData(RouterInterface.UTIL_GET_DHCP_USERS, content));
						} else if (flag == CONNECT_LIST_FLAG) {
							c.putData(constant.getHtmlParser().getRouterData(RouterInterface.UTIL_GET_ACTIVE_USERS, content));
						} else if (flag == MAC_FILTER_LIST_FLAG) {
							c.putData(constant.getHtmlParser().getRouterData(RouterInterface.UTIL_GET_BLACK_USERS, content));
						}

					}
				});

	}

	/**
	 * 修改管理员账号密码
	 */
	public void modifyManagerPassword(final RouterManagerUserBean user,
			final RouterInterface c) {
		c.putData(RouterInterface.SUPPORT_YES);
	}

	@Override
	public void getAllUserMacInFilter(final ContextCallBack c) {
		getData(new ContextCallBack() {
			@Override
			public void putData(HashMap objects) {
				c.putData(objects);
			}
		}, MAC_FILTER_LIST_FLAG);
	}

	@Override
	public void getAllActiveUser(final ContextCallBack c) {
		getData(new ContextCallBack() {

			@Override
			public void putData(HashMap objects) {
				c.putData(objects);
			}
		}, CONNECT_LIST_FLAG);
	}

	@Override
	public void getAllUser(final ContextCallBack c) {
		getData(new ContextCallBack() {
			@Override
			public void putData(HashMap objects) {
				c.putData(objects);
			}
		}, DHCP_LIST_FLAG);
	}

	@Override
	public void stopUserByMacAddress(String macAddress,
			final RouterInterface c) {
		// 要停掉某个mac地址，需要要mac添加到列表，让过滤规则为禁止，然后开启mac地址过滤功能
		httpGet(constant.stopOneMacAddressUri(macAddress),
				constant.stopOneMacAddressReferer(), new GetDataCallBack(c) {});
	}

	@Override
	public void modifyWiFiChannel(int channel, RouterInterface c) {
		c.putData(RouterInterface.SUPPORT_NO);
	}

	@Override
	public void getRouterDeviceName(RouterNameCallBack c) {
			c.putName("磊科路由器");
	}

	@Override
	protected void login(ConnInfoCallBack c) {
		c.putData(true);
	}
}
