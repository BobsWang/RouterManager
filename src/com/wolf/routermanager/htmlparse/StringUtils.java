package com.wolf.routermanager.htmlparse;


import com.wolf.routermanager.bean.MacAddressFilterBean;
import com.wolf.routermanager.bean.WifiUserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuwf
 * 
 * @date 2014-6-25
 * 
 **/

public class StringUtils {
	/*
	 * <script type="text/javascript"> var DHCPDynList=new Array(
	 * "android-bcce10aa0073e087", "5C-0A-5B-42-C7-77", "192.168.1.100",
	 * "00:02:55", "android-ceacd43fe3b169b1", "D4-97-0B-B2-A4-E3",
	 * "192.168.1.106", "01:03:21", "wuwf-PC", "08-57-00-3E-97-6A",
	 * "192.168.1.107", "01:51:44", 0,0 ); </script>
	 */
	// 干掉前两行，干掉后两行
	private static List<String> getStr(String script) {
		List<String> list = new ArrayList<String>();

		String[] a = script.split("\n");
		String[] s = new String[a.length - 4];
		//有的路由器只有四行
		if(a.length == 4) {
			s = new String[a.length - 3];
			s[0] = a[2];
		} else {
			for (int i = 2; i < a.length - 2; i++) {
				s[i - 2] = a[i];
			}
		}

		for (int j = 0; j < s.length; j++) {
			String[] b = s[j].split(",");
			// 是四个一行的那种
			if (b.length > 2) {
				list.add(b[0].replace("\"", "").replace(" ", "").trim());
				list.add(b[1].replace("\"", "").replace(" ", "").trim());
				list.add(b[2].replace("\"", "").replace(" ", "").trim());
				list.add(b[3].replace("\"", "").replace(" ", "").trim());
			} else {
				list.add(b[0].replace("\"", "").replace(" ", "").trim());
			}
		}

		return list;
	}

	/**
	 * 将字符串转换为对象结果集
	 * 
	 * @param script
	 * @return
	 */
	public static List<WifiUserBean> parseWifiUserBean(String script) {
		List<String> strList = getStr(script);
		List<WifiUserBean> userBeans = new ArrayList<WifiUserBean>();

		int j = 0;
		for (int i = 0; i < strList.size() / 4; i++) {
			WifiUserBean wifiuserbean = new WifiUserBean();

			wifiuserbean.setUserName(strList.get(4 * j));
			wifiuserbean.setMacAddress(strList.get(1 + 4 * j));
			wifiuserbean.setIpAddress(strList.get(2 + 4 * j));
			userBeans.add(wifiuserbean);
			j++;
		}

		return userBeans;
	}
	
	/**
	 * 获得活动的用户集合
	 * 
	 * @param script
	 * @return
	 */
	public static List<WifiUserBean> parseWifiActiveUserBean(String script) {
		List<String> strList = getStr(script);
		List<WifiUserBean> userBeans = new ArrayList<WifiUserBean>();

		for (int i = 0; i < strList.size(); i++) {
			//是mac地址
			if(strList.get(i).indexOf("-") > 0) {
				WifiUserBean wifiuserbean = new WifiUserBean();
				wifiuserbean.setMacAddress(strList.get(i));
				userBeans.add(wifiuserbean);
			}
		}
		
		return userBeans;
	}

	/**
	 * 
	 * @param script
	 * @return
	 */
	public static String getActiveUserCount(String script) {
		List<String> strList = getStr(script);
		// 是否已开启过滤
		String count = strList.get(0);
		
		return count;
	}
	
	/**
	 * 将字符串转为mac地址过滤集合
	 * 
	 * @param script
	 * @return
	 */
	public static List<MacAddressFilterBean> parseMacAddress(String script) {
		List<String> strList = getStr(script);
		List<MacAddressFilterBean> userBeans = new ArrayList<MacAddressFilterBean>();
		int j = 0;
		for (int i = 0; i < strList.size() / 4; i++) {
			MacAddressFilterBean macAddressFilterBean = new MacAddressFilterBean();
			
			macAddressFilterBean.setMacAddress(strList.get(4 * j));
			macAddressFilterBean.setFilterFlag(strList.get(1 + 4 * j));
			userBeans.add(macAddressFilterBean);
			j++;
		}
		
		return userBeans;
	}

	/**
	 * 检查过滤规则
	 * 
	 * @param script
	 * @return
	 */
	public static String checkMacFilter(String script) {
		List<String> strList = getStr(script);
		// 是否已开启过滤
		String result1 = strList.get(0);
		// 规则为禁止或允许
		String result2 = strList.get(1);

		return result1 + result2;
	}

}
