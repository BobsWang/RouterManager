package com.wolf.routermanager.common;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.Inet4Address;

/**
 * Created by wuwf on 2014/12/22.
 */
public class WiFiUtil {
    /**
     * 获取当前wifi名称
     */
    public static String getCurrentWifiName(Context context) {
        WifiManager mWifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = mWifiManager.getConnectionInfo();
        String name = null;
        if (info != null && info.getSSID() != null) {
            name = info.getSSID().replace("\"", "");
            return name;
        }

        return "";
    }

    /**
     * 查看WIFI的MAC地址
     */
    public static String getWifiLYMAC(Context context) {
        WifiManager mWifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiinfo = mWifiManager.getConnectionInfo();
        if (wifiinfo != null && wifiinfo.getBSSID() != null
                && !wifiinfo.getBSSID().equals("")) {
            return wifiinfo.getBSSID().replace(":", "-");
        }
        return "00-00-00-00-00-00";
    }

    /**
     * 查看自己连接WIFI路由器的IP
     */
    @SuppressWarnings("deprecation")
    public static String getWiFiLYIP(Context context) {
        WifiManager mWifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        String ip = ipIntToString(mWifiManager.getDhcpInfo().gateway);
        return "http://" + ip;
    }

    /**
     * Function: 将int类型的IP转换成字符串形式的IP<br>
     *
     * @param ip
     * @author ZYT DateTime 2014-5-14 下午12:28:16<br>
     * @return<br>
     */
    private static String ipIntToString(int ip) {
        try {
            byte[] bytes = new byte[4];
            bytes[0] = (byte) (0xff & ip);
            bytes[1] = (byte) ((0xff00 & ip) >> 8);
            bytes[2] = (byte) ((0xff0000 & ip) >> 16);
            bytes[3] = (byte) ((0xff000000 & ip) >> 24);
            return Inet4Address.getByAddress(bytes).getHostAddress();
        } catch (Exception e) {
            return "";
        }
    }
}
