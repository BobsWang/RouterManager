package com.wolf.routermanager.http;

import android.content.Context;
import com.loopj.android.http.RequestParams;
import com.wolf.routermanager.bean.BaseRouterBean;
import com.wolf.routermanager.bean.MacAddressFilterBean;
import com.wolf.routermanager.bean.RouterManagerUserBean;
import com.wolf.routermanager.bean.RouterSafeAndPasswordBean;
import com.wolf.routermanager.bean.WifiUserBean;
import com.wolf.routermanager.common.WiFiUtil;
import com.wolf.routermanager.http.inter.RouterUtilInterface;
import com.wolf.routermanager.inter.ConnInfoCallBack;
import com.wolf.routermanager.inter.ContextCallBack;
import com.wolf.routermanager.inter.GetDataCallBack;
import com.wolf.routermanager.inter.RouterInterface;
import com.wolf.routermanager.urlconstant.inter.RouterConstantInterface;
import org.apache.http.client.params.ClientPNames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 必连的网络请求类
 *
 * @author wuwf
 */
public class RouterBilianUtil_BL_410R extends RouterUtilInterface {

    private List<WifiUserBean> mActiveUsers = new ArrayList<WifiUserBean>();
    private List<MacAddressFilterBean> mMacAddressFilterBeans = new ArrayList<MacAddressFilterBean>();

    public RouterBilianUtil_BL_410R(Context context,
                                    RouterConstantInterface constant, String username, String password) {
        super(context, constant, username, password);
    }

    @Override
    protected void login(ConnInfoCallBack c) {
        c.putData(true);
    }

    @Override
    public void getAllUser(final ContextCallBack c) {
        BaseRouterBean bean = new BaseRouterBean();
        bean.setDhcpUsers(mActiveUsers);
        c.putData(supportYes(bean));
    }

    private RequestParams biLinkMacFilterParams() {
        RequestParams params = new RequestParams();
        params.put("page", "ssid.asp");
        params.put("action", "Apply");
        params.put("wl_unit", "0");
        params.put("wl_maclist", "10");
        params.put("relocation", "wifi_filter.asp");
        params.put("wl_macmode", "deny");
        params.put("wl_ssid", WiFiUtil.getCurrentWifiName(mContext));
        params.put("first_ssid", "0");
        return params;
    }

    @Override
    public void getAllUserMacInFilter(final ContextCallBack c) {
        super.getAllUserMacInFilter(new ContextCallBack() {
            @Override
            public void putData(HashMap<String, Object> map) {
                //如果取黑名单没报错，则保存为全局变量
                if (isSupport(map)) {
                    BaseRouterBean bean = (BaseRouterBean) map.get(RouterInterface.BASEDATA);
                    mMacAddressFilterBeans = bean.getBlackUsers();
                }
                c.putData(map);
            }
        });
    }

    @Override
    public void deleteOneUserFromMacFilter(final String macAddress, final ConnInfoCallBack c) {
        RequestParams params = biLinkMacFilterParams();
        for (int i = 0; i < mMacAddressFilterBeans.size(); i++) {
            if (macAddress.equals(mMacAddressFilterBeans.get(i).getMacAddress())) {
                mMacAddressFilterBeans.remove(i);
                break;
            }
        }
        for (int j = 0; j < mMacAddressFilterBeans.size(); j++) {
            params.put("wl_maclist" + j, mMacAddressFilterBeans.get(j).getMacAddress());
        }
        for (int j = mMacAddressFilterBeans.size(); j < 10; j++) {
            params.put("wl_maclist" + j, "");
        }
        httpPost(constant.stopOneMacAddressUri(null), constant.stopOneMacAddressReferer(), params, new GetDataCallBack(c) {});
    }

    @Override
    public void stopUserByMacAddress(final String macAddress, final RouterInterface c) {

        RequestParams params = biLinkMacFilterParams();
        int i;
        if (mMacAddressFilterBeans.size() == 10) {
            c.putData(RouterInterface.SUPPORT_FAIL);
            return;
        }
        for (i = 0; i < mMacAddressFilterBeans.size(); i++) {
            params.put("wl_maclist" + i, mMacAddressFilterBeans.get(i).getMacAddress());
        }
        params.put("wl_maclist" + i, macAddress);
        for (int j = mMacAddressFilterBeans.size() + 1; j < 10; j++) {
            params.put("wl_maclist" + j, "");
        }
        httpPost(constant.stopOneMacAddressUri(null), constant.stopOneMacAddressReferer(), params, new GetDataCallBack(c) {});
    }

    private RequestParams modifyWithParam() {
        RequestParams params = new RequestParams();
        params.put("action", "Apply");
        params.put("relocation", "status.asp");
        params.put("wan_proto", "dhcp");
        params.put("wan_unit", "0");
        params.put("wan_desc", "Default Connection");
        params.put("wan_primary", "1");
        params.put("wan_ifname", "vlan2");
        params.put("wl_auth", "0");
        params.put("wl_auth_mode", "none");
        params.put("wl_wep", "disabled");
        params.put("wl_akm", "");
        params.put("wl_wps_mode", "enabled");
        params.put("wl_akm_psk", "disabled");
        params.put("wl_akm_psk2", "enabled");
        params.put("wl_unit", "0");
        params.put("wl_crypto", "tkip+aes");
        params.put("wl_wpa_gtk_rekey", "3600");
        params.put("second_dns", "");
        params.put("first_ssid", "0");
        return params;
    }

    /**
     * 修改wifi的ssid
     */
    @Override
    public void modifyWifiSSid(final String ssid, final RouterInterface c) {
        //先获取当前的WiFi密码，然后带着密码去修改ssid
        getWifiSafeInfo(new ContextCallBack() {

            @Override
            public void putData(HashMap objects) {
                //先判断是否成功取到了wifi密码
                if (!isSupport(objects)) {
                   c.putData(RouterInterface.SUPPORT_FAIL);
                   return;
                }
                BaseRouterBean bean = (BaseRouterBean) objects.get(RouterInterface.BASEDATA);
                RouterSafeAndPasswordBean bean1 = bean.getRouterSafePassword();
                RequestParams params = modifyWithParam();
                params.put("wl_ssid", ssid);
                params.put("wl_wpa_psk", bean1.getPassword());
                httpPost(constant.modifySSid(ssid), constant.modifySSidReferer(), params,
                        new GetDataCallBack() {

                            @Override
                            public void start() {
                                c.putData(RouterInterface.SUPPORT_YES);
                            }
                        });
            }
        });
    }

    @Override
    public void modifyWifiPassword(String password, final RouterInterface c) {
        // 和修改WiFi名字走一样的地址
        RequestParams params = modifyWithParam();
        params.put("wl_ssid", WiFiUtil.getCurrentWifiName(mContext));
        params.put("wl_wpa_psk", password);
        httpPost(constant.modifyWifiPassword(password),
                constant.modifyWifiPasswordReferer(), params,
                new GetDataCallBack() {

                    @Override
                    public void start() {
                        c.putData(RouterInterface.SUPPORT_YES);
                    }
                });
    }

    @Override
    public void modifyManagerPassword(RouterManagerUserBean user,
                                      final RouterInterface c) {
        RequestParams params = new RequestParams();
        params.put("page", "index.asp");
        params.put("relocation", "admpassword.asp");
        params.put("action", "Apply");
        params.put("http_username", user.getUserName());
        params.put("SYSOPS", user.getOldPassword());
        params.put("SYSPS", user.getNewPassword());
        params.put("http_passwd", user.getNewPassword());
        httpPost(constant.modifyLoginPassword(null), constant.modifyLoginPasswordReferer(), params, new GetDataCallBack() {

            @Override
            public void start() {
                c.putData(RouterInterface.SUPPORT_YES);
            }
        });
    }

    @Override
    public void modifyWiFiChannel(int channel, final RouterInterface c) {
        RequestParams params = new RequestParams();
        params.put("page", "radio.asp");
        params.put("action", "Apply");
        params.put("relocation", "basic.asp");
        params.put("wl_unit", "0");
        params.put("wl_obss_coex", "0");
        params.put("wl_nband", "2");
        params.put("wl_nmode_protection", "auto");
        params.put("wl_tpc_db", "0");
        params.put("wl_txchain", "3");
        params.put("wl_rxchain", "3");
        params.put("wl_mode_changed", "0");
        params.put("wl_ure_changed", "0");
        params.put("wl_bss_enabled", "1");
        params.put("wl_mode", "ap");
        params.put("filter_tmp", "");
        params.put("wl_nctrlsb", "lower");
        params.put("enablewirelessEx", "");
        params.put("wl_radio", "1");
        params.put("first_ssid", "0");
        params.put("wl_gmode", "0");
        params.put("wl_nmode", "-1");
        params.put("wl_ssid", WiFiUtil.getCurrentWifiName(mContext));
        params.put("wl_closed", "0");
        params.put("wl_ap_isolate", "0");
        params.put("wl_channel", channel);
        params.put("wl_nbw_cap", "1");
        params.put("wl_wme", "on");
        params.put("wl_wme_apsd", "on");
        httpPost(constant.modifyWiFiChannel(channel), constant.modifyWiFiChannelReferer(), params, new GetDataCallBack(c) {});
    }

    @Override
    public void reboot(final ConnInfoCallBack c) {
        RequestParams params = new RequestParams();
        params.put("page", "index.asp");
        params.put("relocation", "admreboot.asp");
        params.put("action", "Reboot");
        httpPost(constant.rebootRouter(), constant.rebootRouterReferer(), params, new GetDataCallBack() {

            @Override
            public void start() {
                c.putData(true);
            }
        });
    }

    @Override
    protected void addOtherRefer() {
        client.getHttpClient().getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        client.addHeader("Cookie", "user=" + mUserName + "+" + mPassword);
        client.addHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:31.0) Gecko/20100101 Firefox/31.0");
        client.addHeader("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    }

    /**
     * 获取所有已连接用户
     */
    @Override
    public void getAllActiveUser(final ContextCallBack c) {
        // 所有连接到本路由器的设备集合
        // http://192.168.16.1/dhcpcliinfo.asp
        // httpGet(constant.getAllActiveUsers(null),
        httpGet(constant.getAllActiveUsers(null),
                constant.getAllUsersReferer(), new GetDataCallBack(c) {

                    @SuppressWarnings("unchecked")
                    @Override
                    public void success(String content) {
                        HashMap<String, Object> map = constant.getHtmlParser().getRouterData(RouterInterface.UTIL_GET_ACTIVE_USERS, content);
                        c.putData(map);
                        //如果没有活动用户
                        if(!isSupport(map)) {
                            return;
                        }
                        BaseRouterBean bean = (BaseRouterBean) map.get(RouterInterface.BASEDATA);
                        mActiveUsers = bean.getActiveUsers();
                    }
                });

    }

    @Override
    public void openMacFilter(ConnInfoCallBack c) {
        RequestParams params = new RequestParams();
        params.add("page", "ssid.asp");
        params.add("action", "Apply");
        params.add("wl_unit", "0");
        params.add("wl_maclist", "10");
        params.add("relocation", "wifi_filter.asp");
        params.add("wl_macmode", "deny");
        params.add("wl_ssid", WiFiUtil.getCurrentWifiName(mContext));
        params.add("first_ssid", "0");

        httpPost(constant.openStopMacAddressFilter(), constant.openStopMacAddressFilterReferer(), params, new GetDataCallBack() {
        });
    }

	@Override
    public void getRouterDeviceName(RouterNameCallBack c) {
        c.putName("必联路由器");
	}
}
