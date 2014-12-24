package com.wolf.routermanager.http;

import android.content.Context;
import android.os.Looper;
import com.loopj.android.http.RequestParams;
import com.wolf.routermanager.bean.BaseRouterBean;
import com.wolf.routermanager.bean.MacAddressFilterBean;
import com.wolf.routermanager.bean.RouterManagerUserBean;
import com.wolf.routermanager.bean.StarWifiConfigBean;
import com.wolf.routermanager.htmlparse.RouterStarWifiHtmlToBean;
import com.wolf.routermanager.http.inter.RouterUtilInterface;
import com.wolf.routermanager.inter.ConnInfoCallBack;
import com.wolf.routermanager.inter.ContextCallBack;
import com.wolf.routermanager.inter.GetDataCallBack;
import com.wolf.routermanager.inter.RouterInterface;
import com.wolf.routermanager.urlconstant.inter.RouterConstantInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * starwifi路由器各接口功能
 *
 * @author wuwf
 */
public class RouterStarWifiUtil extends RouterUtilInterface {

    /**
     * 从黑名单中删除某个mac地址
     */
    private int del;
    private StarWifiConfigBean mStarWifiConfigBean;
    private List<MacAddressFilterBean> mMacAddressFilterBeans = new ArrayList<MacAddressFilterBean>();
    /**
     * 设置constant和ip地址
     */
    public RouterStarWifiUtil(Context context,
                              RouterConstantInterface constant, String username, String password) {
        super(context, constant, username, password);
    }

    /**
     * 拉黑操作
     */
    public void stopUserByMacAddress(String macAddress, final RouterInterface c) {
        final RequestParams params = new RequestParams();
        if (mStarWifiConfigBean != null) {
            params.put("PreAuthentication", "0");
            params.put("RadiusServerIP", "0");
            params.put("RadiusServerIdleTimeout", "");
            params.put("RadiusServerPort", mStarWifiConfigBean.RadiusServerPort);
            params.put("RadiusServerSecret", mStarWifiConfigBean.RadiusServerSecret);
            params.put("RadiusServerSessionTimeout", "0");
            params.put("WEP1Select", "0");
            params.put("WEP2Select", "0");
            params.put("WEP3Select", "0");
            params.put("WEP4Select", "0");
            params.put("apisolated", "0");
            params.put("apselect_0", "2");
            params.put("apselect_1", "0");
            params.put("apselect_2", "0");
            params.put("apselect_3", "0");
            params.put("apselect_4", "0");
            params.put("apselect_5", "0");
            params.put("apselect_6", "0");
            params.put("apselect_7", "0");
            params.put("broadcastssid", "1");
            params.put("bssid_num", "1");
            params.put("cipher", "2");
            params.put("keyRenewalInterval", mStarWifiConfigBean.keyRenewalInterval);
            params.put("mbssidapisolated", "0");
            params.put("mssid_0", mStarWifiConfigBean.mssid_0);
            params.put("mssid_1", mStarWifiConfigBean.mssid_1);
            params.put("mssid_10", "");
            params.put("mssid_11", "");
            params.put("mssid_12", "");
            params.put("mssid_13", "");
            params.put("mssid_14", "");
            params.put("mssid_15", "");
            params.put("mssid_2", "");
            params.put("mssid_3", "");
            params.put("mssid_4", "");
            params.put("mssid_5", "");
            params.put("mssid_6", "");
            params.put("mssid_8", "");
            params.put("mssid_9", "");
            params.put("n_2040_coexit", "0");
            params.put("n_amsdu", "0");
            params.put("n_autoba", "1");
            params.put("n_badecline", "0");
            params.put("n_bandwidth", "1");
            params.put("n_disallow_tkip", "0");
            params.put("n_gi", "1");
            params.put("n_mcs", "33");
            params.put("n_mode", "0");
            params.put("n_rdg	1", "");
            params.put("n_stbc", "1");
            params.put("newap_text_0", macAddress);
            params.put("newap_text_1", "");
            params.put("newap_text_2", "");
            params.put("newap_text_3", "");
            params.put("newap_text_4", "");
            params.put("newap_text_5", "");
            params.put("newap_text_6", "");
            params.put("newap_text_7", "");
            params.put("passphrase", mStarWifiConfigBean.passphrase);
            params.put("radiohiddenButton", "2");
            params.put("rx_stream", "1");
            params.put("security_mode", mStarWifiConfigBean.security_mode);
            params.put("ssidIndex", "0");
            params.put("sz11gChannel", "0");
            params.put("tx_stream	1", "");
            params.put("wep_default_key", "2");
            params.put("wep_key_1", "");
            params.put("wep_key_2", "");
            params.put("wep_key_3", "");
            params.put("wep_key_4", "");
            params.put("wifihiddenButton2", "");
            params.put("wirelessmode", "9");
        }
        // 拉黑操作
        httpPost(constant.stopOneMacAddressUri(macAddress),
                constant.stopOneMacAddressReferer(), params,
                new GetDataCallBack(c) {
                    // 默认点击后既是添加名单成功

                    @Override
                    public void start() {
                        c.putData(RouterInterface.SUPPORT_YES);
                    }
                });

    }

    /**
     * 登录盒子后才能进行操作
     */
    @Override
    public void login(final ConnInfoCallBack call) {
        httpGet(constant.getLogin(), null, new GetDataCallBack(call) {});
    }

    /**
     * 获取所有的连接用户
     */
    @Override
    public void getAllUser(final ContextCallBack c) {
        login(new ConnInfoCallBack() {

            @Override
            public void putData(boolean flag) {
                // 如果登录成功
                if (flag) {
                    // 所有连接到本路由器的设备集合
                    s(c);
                }
            }
        });
    }

    private void s(ContextCallBack c) {
        super.getAllUser(c);
    }

    /**
     * 获取所有黑名单还有其他的一堆参数
     */
    private void getAllMessage(final ContextCallBack c) {
        final RequestParams params = new RequestParams();
        params.put("", "");
        // 获取所有在mac地址过滤功能列表里的数据
        httpPost(constant.getAllMacAddressInFilter(),
                constant.getAllMacAddressInFilterReferer(), params,
                new GetDataCallBack(c) {
                    @Override
                    public void success(String content) {
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        StarWifiConfigBean config = ((RouterStarWifiHtmlToBean) constant
                                .getHtmlParser())
                                .getStarWifiConfigBean(content);
                        map.put("config", config);
                        c.putData(map);
                    }
                });
    }

    /**
     * 获取所有在黑名单中的mac地址集合
     */
    @Override
    public void getAllUserMacInFilter(final ContextCallBack c) {
        // 获取所有在mac地址过滤功能列表里的数据
        getAllMessage(new ContextCallBack() {

            @Override
            public void putData(HashMap<String, Object> map) {
                StarWifiConfigBean bean = (StarWifiConfigBean) map.get("config");
                // 将信息保存为临时变量
                mStarWifiConfigBean = bean;
                mMacAddressFilterBeans = bean.macs;

                BaseRouterBean baseRouterBean = new BaseRouterBean();
                baseRouterBean.setBlackUsers(bean.macs);
                c.putData(supportYes(baseRouterBean));
            }

        });

    }

    @Override
    public void deleteOneUserFromMacFilter(final String macAddress,
                                           final ConnInfoCallBack c) {
        for (int i = 0; i < mMacAddressFilterBeans.size(); i++) {
            if (macAddress.equals(mMacAddressFilterBeans.get(i).getMacAddress())) {
                del = i;
                break;
            }
        }
        new Thread(new Runnable() {

            @Override
            public void run() {
                Looper.prepare();
                try {
                    URL url = new URL(
                            "http://192.168.21.1/goform/APDeleteAccessPolicyList");
                    HttpURLConnection connection = (HttpURLConnection) url
                            .openConnection();
                    connection.setRequestProperty("Referer",
                            "http://192.168.21.1/wireless/wifi.asp");
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");

                    OutputStreamWriter wr = new OutputStreamWriter(connection
                            .getOutputStream());
                    // this is were we're adding post data to the request
                    wr.write("0, 1".replace("1", "" + del));
                    wr.flush();

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    if (in != null) {
                        c.putData(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    c.putData(false);
                }
            }
        }).start();

    }

    /**
     * 修改wifi连接密码
     */
    @Override
    public void modifyWifiPassword(String password, final RouterInterface c) {
        httpGet(constant.modifyWifiPassword(password),
                constant.modifyWifiPasswordReferer(),
                new GetDataCallBack(c) {

                    @Override
                    public void start() {
                        c.putData(RouterInterface.SUPPORT_YES);
                    }

                });
    }

    /**
     * 修改管理员账号密码
     */
    @Override
    public void modifyManagerPassword(final RouterManagerUserBean user,
                                      final RouterInterface c) {
        c.putData(RouterInterface.SUPPORT_NO);
    }

    /**
     * 修改wifi的ssid
     */
    @Override
    public void modifyWifiSSid(String ssid, final RouterInterface c) {
        httpGet(constant.modifySSid(ssid), constant.modifySSidReferer(),
                new GetDataCallBack() {

                    @Override
                    public void start() {
                        c.putData(RouterInterface.SUPPORT_YES);
                    }
                });
    }

    @Override
    public void modifyWiFiChannel(int channel, RouterInterface c) {
        c.putData(RouterInterface.SUPPORT_NO);
    }

    @Override
    public void getRouterDeviceName(RouterNameCallBack c) {
        c.putName("StarWifi S1");
    }
}
