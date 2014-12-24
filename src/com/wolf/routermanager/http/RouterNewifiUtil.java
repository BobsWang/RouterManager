package com.wolf.routermanager.http;

import android.content.Context;
import com.loopj.android.http.RequestParams;
import com.wolf.routermanager.bean.BaseRouterBean;
import com.wolf.routermanager.bean.NewifiRouterBean;
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

public class RouterNewifiUtil extends RouterUtilInterface {
    /**
     * newifi所有操作必须带的参数Stok
     */
    public static String stokStr;
    public static String bd_id;
    public static String sign;
    private RouterSafeAndPasswordBean mRouterSafeAndPasswordBean;
    private List<WifiUserBean> mWifiUserBean = new ArrayList<WifiUserBean>();
    private NewifiRouterBean mNewifiRouterBean;

    public RouterNewifiUtil(Context context, RouterConstantInterface constant,
                            String username, String password) {
        super(context, constant, username, password);
    }

    @Override
    public void getWifiSafeInfo(final ContextCallBack c) {
        super.getWifiSafeInfo(new ContextCallBack() {
            @Override
            public void putData(HashMap map) {
                if (isSupport(map)) {
                    BaseRouterBean bean = (BaseRouterBean) map.get(RouterInterface.BASEDATA);
                    mRouterSafeAndPasswordBean = bean.getRouterSafePassword();
                }
                c.putData(map);
            }
        });
    }

    public void login(final ConnInfoCallBack call) {
        RequestParams params = new RequestParams();
        params.put("username", "root");
        params.put("bd_web", "1");
        params.put("password", mPassword);
        // "http://192.168.99.1/cgi-bin/luci",
        httpPost(constant.getLogin(), constant.getLoginRefer(), params, new GetDataCallBack(call) {

            @Override
            public void success(String content) {
                if (content.contains("用户名或者密码错误")) {
                    call.putData(false);
                } else {
                    int index = content.indexOf("stok=");
                    int end = content.indexOf("/admin", index);
                    String s = content.substring(index, end);
                    stokStr = s.replace("stok=", "");
                    call.putData(true);
                }

            }
        });

    }

    // /**
    // * 获取百度账号和签名
    // */
    // private void getBDidAndSign(final ConnInfoCallBack call) {
    // httpGet(constant.getBaseUri() + "/cgi-bin/luci/;stok=" + stokStr
    // + "/admin/private/get_id_and_sign", constant.getBaseUri()
    // + "/cgi-bin/luci", new SuccessCallBack() {
    //
    // @Override
    // public void doStart() {
    // }
    //
    // @Override
    // public void doFailure() {
    // call.putData(false);
    // }
    //
    // @Override
    // public void callBack(final String content) {
    // JSONObject object = JSONObject.parseObject(content);
    // bd_id = object.getString("id");
    // sign = object.getString("sign");
    // call.putData(true);
    // }
    // });
    // }

    @Override
    public void getAllUser(final ContextCallBack c) {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        baseRouterBean.setDhcpUsers(mWifiUserBean);
        c.putData(supportYes(baseRouterBean));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getAllUserMacInFilter(final ContextCallBack c) {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        baseRouterBean.setBlackUsers(mNewifiRouterBean.getMacdisplay());
        c.putData(supportYes(baseRouterBean));
    }

    @Override
    public void getAllActiveUser(final ContextCallBack c) {
        httpGet(constant.getAllActiveUsers(null),
                constant.getAllActiveUsersReferer(),
                new GetDataCallBack(c) {

                    @SuppressWarnings("unchecked")
                    @Override
                    public void success(final String content) {
                        HashMap<String, Object> map = constant
                                .getHtmlParser()
                                .getRouterData(RouterInterface.UTIL_GET_ACTIVE_USERS, content);
                        if (isSupport(map)) {
                            //取一次活动用户，就能取到所有的信息，保存到NewifiRouterBean里
                            mNewifiRouterBean = (NewifiRouterBean) map.get(RouterInterface.BASEDATA);
                            mWifiUserBean = mNewifiRouterBean.getActiveUsers();
                        }
                        c.putData(map);
                    }
                });

    }

    @Override
    protected void checkFilterAndRule(ConnInfoCallBack c) {
        c.putData(true);
    }

    @Override
    public void stopUserByMacAddress(final String macAddress,
                                     final RouterInterface c) {
        String ip = null;
        String name = null;
        for (int i = 0; i < mWifiUserBean.size(); i++) {
            WifiUserBean bean = mWifiUserBean.get(i);
            if (macAddress.equals(bean.getMacAddress())) {
                ip = bean.getIpAddress();
                name = bean.getUserName();
            }
        }
        String string = macAddress + "&name=" + name + "&ip=" + ip;
        string = string.replaceAll("-", ":");
        httpGet(constant.stopOneMacAddressUri(string),
                constant.stopOneMacAddressReferer(), new GetDataCallBack(c) {
                    @Override
                    public void success(String content) {
                        if ("{ \"status\": \"0\" }".equals(content)) {
                            c.putData(RouterInterface.SUPPORT_YES);
                            return;
                        }
                        c.putData(RouterInterface.SUPPORT_FAIL);
                    }
                });

    }

    @Override
    public void deleteOneUserFromMacFilter(final String macAddress,
                                           final ConnInfoCallBack c) {
        String ip = null;
        String name = null;
        @SuppressWarnings("unchecked")
        List<WifiUserBean> list = mNewifiRouterBean.getMac();
        for (int i = 0; i < list.size(); i++) {
            WifiUserBean bean = list.get(i);
            if (macAddress.equals(bean.getMacAddress())) {
                ip = bean.getIpAddress();
                name = bean.getUserName();
                break;
            }
        }
        String string = macAddress + "&name=" + name + "&ip=" + ip;
        string = string.replaceAll("-", ":");
        string = string.replaceAll(":", "%3A");
        httpGet(constant.stopOneMacAddressUri(string),
                constant.stopOneMacAddressReferer(), new GetDataCallBack(c) {
                    @Override
                    public void success(String content) {
                        if ("{ \"status\": \"0\" }".equals(content)) {
                            c.putData(true);
                            return;
                        }
                        c.putData(false);
                    }
                });
    }


    @Override
    protected void addOtherRefer() {
        client.addHeader("X-Requested-With", "XMLHttpRequest");
        client.addHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:31.0) Gecko/20100101 Firefox/31.0");
    }

    private RequestParams getParam() {
        RequestParams params = new RequestParams();
        if (mRouterSafeAndPasswordBean.getType() == 3) {
            params.put("w_encryption", "wpa/wpa2");
            params.put("w_passwd", mRouterSafeAndPasswordBean.getPassword());
        } else if (mRouterSafeAndPasswordBean.getType() == 1) {
            params.put("w_encryption", "wpa2");
            params.put("w_passwd", mRouterSafeAndPasswordBean.getPassword());
        }
        params.put("w_status", "1");
        params.put("w_intensity", "1");
        params.put("ssid_hide", "0");

        return params;
    }

    @Override
    public void modifyWifiSSid(String ssid, final RouterInterface c) {
        RequestParams params = getParam();
        params.put("wifi_type", "0");
        params.put("w_ssid", ssid);
        RequestParams params_5G = getParam();
        params_5G.put("wifi_type", "1");
        params_5G.put("w_ssid", ssid + "_5G");

        httpPost(constant.modifySSid(ssid), constant.modifySSidReferer(),
                params, new GetDataCallBack() {

                    @Override
                    public void start() {
                        c.putData(RouterInterface.SUPPORT_YES);
                    }
                });
        httpPost(constant.modifySSid(ssid), constant.modifySSidReferer(),
                params_5G, new GetDataCallBack() {
                    @Override
                    public void start() {
                        c.putData(RouterInterface.SUPPORT_YES);
                    }
                });
    }

    @Override
    public void modifyWifiPassword(String password, final RouterInterface c) {
        RequestParams params = new RequestParams();
        params.put("w_encryption", "wpa/wpa2");
        params.put("w_passwd", password);
        if (WiFiUtil.getCurrentWifiName(mContext).contains("5G")) {
            params.put("wifi_type", "1");
        } else {
            params.put("wifi_type", "0");
        }
        params.put("w_status", "1");
        params.put("w_ssid", WiFiUtil.getCurrentWifiName(mContext));
        // params.put("w_channel", channel);
        params.put("w_intensity", "1");
        params.put("ssid_hide", "0");

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
        params.put("oldPasswd", user.getOldPassword());
        params.put("newPasswd", user.getNewPassword());
        httpPost(constant.modifyLoginPassword(user),
                constant.modifyLoginPasswordReferer(), params,
                new GetDataCallBack() {});
    }

    @Override
    public void modifyWiFiChannel(final int channel, final RouterInterface c) {
        RequestParams params = getParam();
        params.put("wifi_type", "0");
        params.put("w_ssid", WiFiUtil.getCurrentWifiName(mContext));
        params.put("w_channel", channel);

        httpPost(constant.modifyWiFiChannel(channel),
                constant.modifyWiFiChannelReferer(), params,
                new GetDataCallBack() {

                    @Override
                    public void start() {
                        c.putData(RouterInterface.SUPPORT_YES);
                    }
                });
    }

    @Override
    public void reboot(final ConnInfoCallBack c) {
        c.putData(false);
    }

    @Override
    public void getRouterDeviceName(RouterNameCallBack c) {
        c.putName("新路由");
    }
}
