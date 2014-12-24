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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by PaulHsu on 2014/12/3.
 */
public class RouterASUSUtil extends RouterUtilInterface {
    /**
     * 活动用户对象列表
     */
    private List<WifiUserBean> mActiveUsers;
    private String mHTML;
    /**
     * Mac地址黑名单列表
     */
    private List<MacAddressFilterBean> mMacAddressFilter = new ArrayList<MacAddressFilterBean>();
    /**
     * 路由器安全对象
     */
    private RouterSafeAndPasswordBean mRouterSafeAndPasswordBean = new RouterSafeAndPasswordBean();

    public RouterASUSUtil(Context context, RouterConstantInterface constant,
                          String username, String password) {
        super(context, constant, username, password);
    }


    @Override
    public void getWifiSafeInfo(final ContextCallBack c) {
        super.getWifiSafeInfo(new ContextCallBack() {
            @Override
            public void putData(HashMap map) {
                //如果是支持的，成功取到的
                if (isSupport(map)) {
                    BaseRouterBean bean = (BaseRouterBean) map.get(RouterInterface.BASEDATA);
                    mRouterSafeAndPasswordBean = bean.getRouterSafePassword();
                }
                c.putData(map);
            }
        });
    }

    @Override
    public void getAllUserMacInFilter(final ContextCallBack c) {
        httpGet(constant.getAllMacAddressInFilter(),
                constant.getAllMacAddressInFilterReferer(),
                new GetDataCallBack(c) {
                    @Override
                    public void success(String content) {
                        HashMap<String, Object> map = constant.getHtmlParser().getRouterData(RouterInterface.UTIL_GET_BLACK_USERS, content);
                        c.putData(map);
                        //如果不是解析成功的，则直接返回
                        if (!isSupport(map)) {
                            return;
                        }
                        mHTML = content;
                        mMacAddressFilter = ((BaseRouterBean) map.get(RouterInterface.BASEDATA)).getBlackUsers();
                    }

                });
    }

    @Override
    public void checkFilterAndRule(final ConnInfoCallBack c) {
        HashMap<String, Object> map = constant.getHtmlParser().getRouterData(RouterInterface.UTIL_GET_FILTERTYPE, mHTML);
        //如果解析网页失败了，则认为未开启
        if (!isSupport(map)) {
            c.putData(false);
            return;
        }
        BaseRouterBean bean = (BaseRouterBean) map.get(RouterInterface.BASEDATA);
        c.putData(bean.isMacFilterOpen());
    }

    @Override
    public void getAllUser(final ContextCallBack c) {
        BaseRouterBean bean = new BaseRouterBean();
        bean.setDhcpUsers(mActiveUsers);
        c.putData(supportYes(bean));
    }

    @Override
    public void openMacFilter(final ConnInfoCallBack c) {
        RequestParams params = new RequestParams();
        params.put("current_page", "Advanced_ACL_Content.asp");
        params.put("next_page", "Advanced_ACL_Content.asp");
        params.put("modified", "0");
        params.put("action_mode", "apply_new");
        params.put("action_wait", "3");
        params.put("action_script", "restart_wireless");
        params.put("first_time", "");
        params.put("preferred_lang", "CN");
        params.put("wl_ssid", WiFiUtil.getCurrentWifiName(mContext));
        params.put("wl_subunit", "-1");
        params.put("wl_macmode", "deny");
        params.put("wl_unit", "0");
        params.put("enable_mac", "0");
        params.put("wl_macmode_show", "deny");
        httpPost(constant.openStopMacAddressFilter(),
                constant.openStopMacAddressFilterReferer(), params,
                new GetDataCallBack(c) {});
    }

    @Override
    public void getAllActiveUser(final ContextCallBack c) {
        // GET /update_clients.asp?_=1417682597015 HTTP/1.1
        mActiveUsers = new ArrayList<WifiUserBean>();
        httpGet(constant.getAllActiveUsers("1"),
                constant.getAllActiveUsersReferer(), new GetDataCallBack(c) {
                    @Override
                    public void success(String content) {
                        HashMap<String, Object> map = constant
                                .getHtmlParser()
                                .getRouterData(RouterInterface.UTIL_GET_ACTIVE_USERS, content);
                        c.putData(map);

                        if (!isSupport(map)) {
                            return;
                        }
                        BaseRouterBean bean = (BaseRouterBean) map.get(RouterInterface.BASEDATA);
                        mActiveUsers = bean.getActiveUsers();
                    }

                });

    }

    @Override
    public void stopUserByMacAddress(String macAddress,
                                     final RouterInterface c) {
        RequestParams params = new RequestParams();
        String mac = "";
        for (int i = 0; i < mMacAddressFilter.size(); i++) {
            mac = mac + "<" + mMacAddressFilter.get(i).getMacAddress();
        }
        mac = mac + "<" + macAddress;
        params.put("current_page", "Advanced_ACL_Content.asp");
        params.put("next_page", "Advanced_ACL_Content.asp");
        params.put("modified", "0");
        params.put("action_mode", "apply_new");
        params.put("action_wait", "3");
        params.put("action_script", "restart_wireless");
        params.put("first_time", "");
        params.put("preferred_lang", "CN");
        params.put("wl_ssid", WiFiUtil.getCurrentWifiName(mContext));
        params.put("wl_maclist_x", mac);
        params.put("wl_subunit", "-1");
        params.put("wl_macmode", "deny");
        params.put("wl_unit", "0");
        params.put("enable_mac", "0");
        params.put("wl_macmode_show", "deny");
        httpPost(constant.stopOneMacAddressUri(macAddress),
                constant.stopOneMacAddressReferer(), params,
                new GetDataCallBack(c) {});

    }

    @Override
    public void getRouterDeviceName(final RouterNameCallBack c) {
        c.putName("华硕路由器");
    }

    @Override
    protected void login(ConnInfoCallBack c) {
        c.putData(true);
    }

    @Override
    public void modifyWifiPassword(String password, final RouterInterface c) {
        RequestParams params = new RequestParams();
        params.put("current_page", "/index.asp");
        params.put("next_page", "/index.asp");
        params.put("action_mode", "apply_new");
        params.put("action_script", "restart_wireless");
        params.put("wl_ssid_org", WiFiUtil.getCurrentWifiName(mContext));
        params.put("wl_wpa_psk_org", mRouterSafeAndPasswordBean.getPassword());
        params.put("wl_ssid", WiFiUtil.getCurrentWifiName(mContext));
        params.put("wl_auth_mode_x", "pskpsk2");
        params.put("wps_enable", "1");
        params.put("wsc_config_state", "1");
        params.put("wl_wep_x_orig", "0");
        params.put("wl_key_org", "1");
        params.put("wl_nmode_x", "0");
        params.put("wps_band", "0");
        params.put("wl_unit", "0");
        params.put("wl_subunit", "-1");
        params.put("wl_radio", "1");
        params.put("wl_crypto", "aes");
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
    public void modifyWifiSSid(String ssid, final RouterInterface c) {
        RequestParams params = new RequestParams();
        params.put("current_page", "/index.asp");
        params.put("next_page", "/index.asp");
        params.put("action_mode", "apply_new");
        params.put("action_script", "restart_wireless");
        params.put("wl_ssid_org", WiFiUtil.getCurrentWifiName(mContext));
        params.put("wl_wpa_psk_org", mRouterSafeAndPasswordBean.getPassword());
        params.put("wl_ssid", ssid);
        params.put("wl_auth_mode_x", "pskpsk2");
        params.put("wps_enable", "1");
        params.put("wsc_config_state", "1");
        params.put("wl_wep_x_orig", "0");
        params.put("wl_key_org", "1");
        params.put("wl_nmode_x", "0");
        params.put("wps_band", "0");
        params.put("wl_unit", "0");
        params.put("wl_subunit", "-1");
        params.put("wl_radio", "1");
        params.put("wl_crypto", "aes");
        params.put("wl_wpa_psk", mRouterSafeAndPasswordBean.getPassword());
        httpPost(constant.modifyWifiPassword(ssid),
                constant.modifyWifiPasswordReferer(), params,
                new GetDataCallBack() {

                    @Override
                    public void start() {
                        c.putData(RouterInterface.SUPPORT_YES);
                    }
                });
    }

    @Override
    public void deleteOneUserFromMacFilter(String macAddress,
                                           final ConnInfoCallBack c) {
        RequestParams params = new RequestParams();
        String mac = "";
        for (int i = 0; i < mMacAddressFilter.size(); i++) {
            if (mMacAddressFilter.get(i).getMacAddress().equals(macAddress)) {
                mac = mac + "<" + mMacAddressFilter.get(i).getMacAddress();
            }
        }
        params.put("current_page", "Advanced_ACL_Content.asp");
        params.put("next_page", "Advanced_ACL_Content.asp");
        params.put("modified", "0");
        params.put("action_mode", "apply_new");
        params.put("action_wait", "3");
        params.put("action_script", "restart_wireless");
        params.put("first_time", "");
        params.put("preferred_lang", "CN");
        params.put("wl_ssid", WiFiUtil.getCurrentWifiName(mContext));
        params.put("wl_maclist_x", mac);
        params.put("wl_subunit", "-1");
        params.put("wl_macmode", "deny");
        params.put("wl_unit", "0");
        params.put("enable_mac", "0");
        params.put("wl_macmode_show", "deny");
        httpPost(constant.stopOneMacAddressUri(macAddress),
                constant.stopOneMacAddressReferer(), params,
                new GetDataCallBack(c) {});
    }


    @Override
    public void modifyWiFiChannel(int channel, final RouterInterface c) {
        RequestParams params = new RequestParams();
        params.put("wl_channel", channel);
        params.put("current_page", "Advanced_Wireless_Content.asp");
        params.put("next_page", "Advanced_Wireless_Content.asp");
        params.put("modified", "0");
        params.put("wan_nat_x", "1");
        params.put("action_mode", "apply_new");
        params.put("wl_gmode_protection", "auto");
        params.put("action_script", "restart_wireless");
        params.put("wl_wme", "on");
        params.put("action_wait", "5");
        params.put("wl_wpa_psk_org", mRouterSafeAndPasswordBean.getPassword());
        params.put("wl_ssid", WiFiUtil.getCurrentWifiName(mContext));
        params.put("wl_auth_mode_x", "pskpsk2");
        params.put("wl_crypto", "aes");
        params.put("AUTO_CHANNEL", "0");
        params.put("wl_wep_x_orig", "0");
        params.put("wl_optimizexbox", "0");
        params.put("wl_subunit", "-1");
        params.put("wl_unit", "0");
        params.put("wl_closed", "0");
        params.put("wl_nmode_x", "0");
        params.put("wl_bw", "1");
        params.put("wl_wpa_gtk_rekey", "3600");
        params.put("wl_wpa_psk", mRouterSafeAndPasswordBean.getPassword());

        httpPost(constant.modifyWiFiChannel(channel),
                constant.modifyWiFiChannelReferer(), params,
                new GetDataCallBack(c) {});
    }

    @Override
    public void modifyManagerPassword(RouterManagerUserBean user,
                                      final RouterInterface c) {
        RequestParams params = new RequestParams();
        params.put("current_page", "Advanced_System_Content.asp");
        params.put("next_page", "Advanced_System_Content.asp");
        params.put("modified", "0");
        params.put("action_mode", "apply");
        params.put("action_wait", "5");
        params.put("action_script", "restart_time");
        params.put("http_passwd", user.getNewPassword());
        params.put("http_username", "admin");
        params.put("http_passwd2", user.getNewPassword());
        params.put("v_password2", user.getNewPassword());
        params.put("btn_ez_radiotoggle", "0");
        httpPost(constant.modifyLoginPassword(user),
                constant.modifyLoginPasswordReferer(), params,
                new GetDataCallBack(c) {});
    }

    @Override
    public void reboot(final ConnInfoCallBack c) {
        RequestParams params = new RequestParams();
        params.put("current_page", "index.asp");
        params.put("wl_auth_mode_x", "pskpsk2");
        params.put("wl_wep_x", "0");
        params.put("action_mode", "reboot");
        params.put("action_wait", "70");
        params.put("wan_unit", "0");
        httpPost(constant.rebootRouter(), constant.rebootRouterReferer(),
                params, new GetDataCallBack() {
                    @Override
                    public void start() {
                        c.putData(true);
                    }
                });
    }
}
