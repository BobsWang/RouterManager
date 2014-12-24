package com.wolf.routermanager.http;

import android.content.Context;
import com.loopj.android.http.RequestParams;
import com.wolf.routermanager.bean.BaseRouterBean;
import com.wolf.routermanager.bean.MacAddressFilterBean;
import com.wolf.routermanager.bean.RouterManagerUserBean;
import com.wolf.routermanager.common.WiFiUtil;
import com.wolf.routermanager.http.inter.RedirectHttpClient;
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
 * 腾达路由器各接口功能
 *
 * @author wuwf
 */
public class RouterTenDaUtil extends RouterUtilInterface {
    private List<MacAddressFilterBean> mMacAddressFilterBeans = new ArrayList<MacAddressFilterBean>();

    public RouterTenDaUtil(Context context, RouterConstantInterface constant,
                           String username, String password) {
        super(context, constant, username, password);
        client = new RedirectHttpClient();
    }

    /**
     * 修改管理员账号密码
     */
    @Override
    public void modifyManagerPassword(final RouterManagerUserBean user,
                                      final RouterInterface c) {
        c.putData(RouterInterface.SUPPORT_YES);
    }

    @Override
    public void login(final ConnInfoCallBack call) {
        RequestParams params = new RequestParams();
        params.put("Username", "admin");
        params.put("checkEn", "0");
        params.put("Password", mPassword);
        httpPost(constant.modifyLoginPassword(null),
                constant.modifyLoginPasswordReferer(), params,
                new GetDataCallBack(call) {

                    @Override
                    public void success(String content) {
                        if (content == null
                                || content.contains("TENDA 11N无线路由器登录界面")) {
                            call.putData(false);
                        } else {
                            call.putData(true);
                        }

                    }
                });

    }


    @Override
    public void modifyWifiPassword(String password, final RouterInterface c) {
        RequestParams params = new RequestParams();
        params.put("MACC", "");
        params.put("GO", "advance.asp");
        params.put("v12_time", "1410946597.232");
        params.put("WANT1", "2");
        params.put("net_type", "0");
        params.put("wirelesspassword", password);
        httpPost(constant.modifyWifiPassword(null),
                constant.modifyWifiPasswordReferer(), params,
                new GetDataCallBack() {

                    @Override
                    public void start() {
                        c.putData(RouterInterface.SUPPORT_YES);
                        reboot(new ConnInfoCallBack() {

                            @Override
                            public void putData(boolean flag) {

                            }
                        });
                    }
                });

    }

    /**
     * 修改wifi的ssid
     */
    public void modifyWifiSSid(String ssid, final RouterInterface c) {
        RequestParams params = new RequestParams();
        params.put("GO", "wireless_basic.asp");
        params.put("ssid", ssid);
        httpPost(constant.modifySSid(ssid), constant.modifySSidReferer(),
                params, new GetDataCallBack() {

                    @Override
                    public void start() {
                        c.putData(RouterInterface.SUPPORT_YES);
                        // 重启路由
                        reboot(new ConnInfoCallBack() {

                            @Override
                            public void putData(boolean flag) {

                            }
                        });
                    }
                });
    }

    /**
     * 修改路由器信道
     */
    public void modifyWiFiChannel(int channel, final RouterInterface c) {
        RequestParams params = new RequestParams();
        // params.put("GO", "wireless_basic.asp");
        params.put("ssid", WiFiUtil.getCurrentWifiName(mContext));
        params.put("sz11bChannel", channel);
        httpPost(constant.modifyWiFiChannel(channel),
                constant.modifyWiFiChannelReferer(), params,
                new GetDataCallBack(c) {});
    }

    @Override
    public void getAllUserMacInFilter(final ContextCallBack c) {
        super.getAllUserMacInFilter(new ContextCallBack() {
            @Override
            public void putData(HashMap<String, Object> map) {
                if (isSupport(map)) {
                    BaseRouterBean bean = (BaseRouterBean) map.get(RouterInterface.BASEDATA);
                    mMacAddressFilterBeans = bean.getBlackUsers();
                }
                c.putData(map);
            }
        });
    }

    /**
     * 开启mac地址过滤功能
     */
    public void openMacFilter(final ConnInfoCallBack c) {
        // 此操作执行后会短暂关闭wifi，并且没有返回值，会走doFailure
        RequestParams params = new RequestParams();
        params.put("GO", "wireless_filter.asp");
        List<MacAddressFilterBean> list = mMacAddressFilterBeans;
        if (list != null) {
            String content = "";
            for (int i = 0; i < list.size(); i++) {
                content = content + list.get(i).getMacAddress() + " ";
            }
            params.put("maclist", content.trim());
        }
        params.put("FilterMode", "deny");
        httpPost(constant.openStopMacAddressFilter(),
                constant.openStopMacAddressFilterReferer(), params,
                new GetDataCallBack() {
                    // 默认点击后就已经开启
                    @Override
                    public void start() {
                        c.putData(true);
                    }
                });
    }

    @Override
    public void deleteOneUserFromMacFilter(String macAddress,
                                           final ConnInfoCallBack c) {
        // 要停掉某个mac地址，需要要mac添加到列表，让过滤规则为禁止，然后开启mac地址过滤功能
        RequestParams params = new RequestParams();
        params.put("GO", "wireless_filter.asp");
        List<MacAddressFilterBean> list = mMacAddressFilterBeans;
        if (list != null) {
            String content = "";
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getMacAddress().endsWith(macAddress)) {
                    continue;
                }
                content = content + list.get(i).getMacAddress() + " ";
            }
            params.put("maclist", content.trim());
        }
        params.put("FilterMode", "deny");
        httpPost(constant.stopOneMacAddressUri(macAddress),
                constant.stopOneMacAddressReferer(), params,
                new GetDataCallBack(c) {});

    }

    @Override
    public void stopUserByMacAddress(String macAddress,
                                     final RouterInterface c) {
        // 要停掉某个mac地址，需要要mac添加到列表，让过滤规则为禁止，然后开启mac地址过滤功能
        RequestParams params = new RequestParams();
        params.put("GO", "wireless_filter.asp");
        List<MacAddressFilterBean> list = mMacAddressFilterBeans;
        if (list != null) {
            String content = "";
            for (int i = 0; i < list.size(); i++) {
                content = content + list.get(i).getMacAddress() + " ";
            }
            content = content + macAddress;
            params.put("maclist", content.trim());
        }
        params.put("FilterMode", "deny");
        httpPost(constant.stopOneMacAddressUri(macAddress),
                constant.stopOneMacAddressReferer(), params,
                new GetDataCallBack(c) {});
    }

    @Override
    protected void addOtherRefer() {
        // client.getHttpClient().getParams()
        // .setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);
        // client.addHeader(
        // "User-Agent",
        // "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 1.7; .NET CLR 1.1.4322; CIBA; .NET CLR 2.0.50727)");
        client.addHeader("Cookie", "admin:language=cn");
        client.setEnableRedirects(true);
        client.getHttpClient().getParams()
                .setParameter(ClientPNames.MAX_REDIRECTS, 3);
        client.getHttpClient().getParams()
                .setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        // client.addHeader("Content-Type",
        // "application/x-www-form-urlencoded");
    }

    @Override
    public void getRouterDeviceName(final RouterNameCallBack c) {
        c.putName("腾达路由器");
    }
}
