package com.wolf.routermanager.bean;


import com.wolf.routermanager.http.inter.RouterUtilInterface;

import java.util.List;

/**
 * 保存路由器信息的临时类
 * @author wuwf
 *
 */
public class AllRouterInfoBean {
	public static boolean hasLogin = false;

	public static List<WifiUserBean> allDhcpUser;
	public static List<WifiUserBean> allActiveWifiUser;
	
	public static List<MacAddressFilterBean> allMacFilterUser;
	
	public static RouterUtilInterface routerUtilInterface;
}
