package com.wolf.routermanager.http;

import android.content.Context;
import com.loopj.android.http.RequestParams;
import com.wolf.routermanager.bean.BaseRouterBean;
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

public class RouterDLinkUtil_612 extends RouterUtilInterface {

    private List<WifiUserBean> wifiUsers = new ArrayList<WifiUserBean>();

    public RouterDLinkUtil_612(Context context, RouterConstantInterface constant,
                               String username, String password) {
        super(context, constant, username, password);
    }

    @Override
    public void login(final ConnInfoCallBack call) {
        RequestParams params = new RequestParams();
        params.put("getpage", "html/index.html");
        params.put("errorpage", "html/main.html");
        params.put("var:menu", "setup");
        params.put("var:page", "wizard");
        params.put("var:login", "true");
        params.put("obj-action", "auth");
        params.put(":username", mUserName);
        params.put(":password", mPassword);
        params.put(":action", "login");
        params.put(":sessionid", "32c7079a");

        httpPost(constant.getLogin(),
                constant.getLoginRefer(), params,
                new GetDataCallBack(call) {});
    }

    @Override
    public void getAllUserMacInFilter(ContextCallBack c) {
        // dlink不支持黑名单功能
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(RouterInterface.SUPPORT, RouterInterface.SUPPORT_NO);
        c.putData(map);
    }

    @Override
    public void getAllUser(final ContextCallBack c) {
        BaseRouterBean bean = new BaseRouterBean();
        bean.setDhcpUsers(wifiUsers);
        c.putData(supportYes(bean));
    }

    @Override
    public void getAllActiveUser(final ContextCallBack c) {
        httpGet(constant.getAllActiveUsers(null),
                constant.getAllActiveUsersReferer(),
                new GetDataCallBack(c) {

                    @SuppressWarnings({"unchecked"})
                    @Override
                    public void success(final String content) {
                        HashMap<String, Object> map = constant
                                .getHtmlParser().getRouterData(RouterInterface.UTIL_GET_ACTIVE_USERS, content);
                        //如果取黑名单没报错，则保存为全局变量
                        if (isSupport(map)) {
                            BaseRouterBean bean = (BaseRouterBean) map.get(RouterInterface.BASEDATA);
                            wifiUsers = bean.getActiveUsers();
                        }
                        c.putData(map);
                    }

                });


    }

    @Override
    public void stopUserByMacAddress(String macAddress, RouterInterface c) {
        c.putData(RouterInterface.SUPPORT_NO);
    }

    @Override
    public void modifyManagerPassword(RouterManagerUserBean user,
                                      final RouterInterface c) {
        RequestParams params = new RequestParams();
        params.put(":InternetGatewayDevice.X_TWSZ-COM_Authentication.UserList.1.Password",
                user.getOldPassword() + "#TW#TW#" + user.getNewPassword());
        params.put("obj-action", "set");
        params.put("var:nodeIndex", 1 + "");
        params.put("var:page", "accountpsd");
        params.put("var:errorpage", "accountpsd");
        params.put("getpage", "html/index.html");
        params.put("errorpage", "html/index.html");
        params.put("var:menu", "tools");
        params.put("var:CacheLastData", "SU5QVVRfU2Vzc2lvblRpbWVPdXQ9MzA=");
        httpPost(constant.modifyLoginPassword(user), constant.modifyLoginPasswordReferer(), params,
                new GetDataCallBack() {

                    @Override
                    public void start() {
                        c.putData(RouterInterface.SUPPORT_YES);
                    }
                });
    }

    @Override
    public void modifyWifiSSid(final String ssid, final RouterInterface c) {
        getWifiSafeInfo(new ContextCallBack() {
            @Override
            public void putData(HashMap<String, Object> map) {
                if (!isSupport(map)) {
                    c.putData(RouterInterface.SUPPORT_FAIL);
                    return;
                }
                BaseRouterBean bean = (BaseRouterBean) map.get(RouterInterface.BASEDATA);
                RouterSafeAndPasswordBean routerSafeAndPasswordBean = bean.getRouterSafePassword();
                RequestParams params = getCommonParams();
                params.put(":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.SSID", ssid);
                params.put(":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.PreSharedKey.1.KeyPassphrase",
                        routerSafeAndPasswordBean.getPassword());
                //无密码时
                if (routerSafeAndPasswordBean.getType() == 0) {
                    params.put(":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.BeaconType", "None");
                } else {
                    params.put(":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.BeaconType", "WPAand11i");
                }

                httpPost(constant.modifySSid(ssid), constant.modifySSidReferer(), params, new GetDataCallBack() {

                    @Override
                    public void start() {
                        c.putData(RouterInterface.SUPPORT_YES);
                    }
                });
            }
        });
    }

    @Override
    public void modifyWifiPassword(final String password, final RouterInterface c) {
        RequestParams params = getCommonParams();
        params.put(
                ":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.SSID",
                WiFiUtil.getCurrentWifiName(mContext));
        params.put(
                ":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.PreSharedKey.1.KeyPassphrase",
                password);
        // 无密码时
        params.put(
                ":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.BeaconType",
                "WPAand11i");

        httpPost(constant.modifyWifiPassword(password),
                constant.modifyWifiPasswordReferer(), params,
                new GetDataCallBack() {

                    @Override
                    public void start() {
                        c.putData(RouterInterface.SUPPORT_YES);
                    }
                });

    }


    private RequestParams getCommonParams() {
        RequestParams params = new RequestParams();
        params.put(":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.WPS.Enable", "1");
        params.put(":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.WPAEncryptionModes", "TKIPandAESEncryption");
        params.put(":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.WPAAuthenticationMode", "PSKAuthentication");
        params.put("var:menu", "wireless");
        params.put("obj-action", "set");
        params.put("var:page", "wireless_basic");
        params.put("var:errorpage", "wireless_basic");
        params.put("getpage", "html/index.html");
        params.put("errorpage", "html/index.html");
        params.put(":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.IEEE11iEncryptionModes",
                "TKIPandAESEncryption");
        params.put(":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.IEEE11iAuthenticationMode",
                "PSKAuthentication");
        params.put(":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.SSIDAdvertisementEnabled", "1");
        params.put(":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.X_TWSZ-COM_APIsolate", "0");
        params.put(":InternetGatewayDevice.X_TWSZ-COM_Radio.1.Channel", "0");
        params.put(":InternetGatewayDevice.X_TWSZ-COM_Radio.1.AutoChannelEnable", "1");
        params.put(":InternetGatewayDevice.X_TWSZ-COM_Radio.1.RegulatoryDomain", "CN");
        params.put(":InternetGatewayDevice.X_TWSZ-COM_Radio.1.Standard", "bgn");
        params.put(":InternetGatewayDevice.X_TWSZ-COM_Radio.1.OperatingChannelBandwidth", "40");
        params.put(":InternetGatewayDevice.X_TWSZ-COM_Radio.1.MaxBitRate", "Auto");
        return params;
    }

    /**
     * 查看过滤规则和是否已开启过滤
     */
    @Override
    protected void checkFilterAndRule(ConnInfoCallBack c) {
        c.putData(true);
    }

    @Override
    public void addOtherRefer() {
        client.getHttpClient().getParams()
                .setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        client.addHeader(
                "User-Agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 1.7; .NET CLR 1.1.4322; CIBA; " +
                        ".NET CLR 2.0.50727)");
        client.addHeader(
                "Cookie",
                "sessionid=32c7079a; auth=ok; expires=Sun, 15-May-2112 01:45:46 GMT; langmanulset=yes; " +
                        "Lan_IPAddress=192.168.0.1; language=zh_cn; sys_UserName=admin; expires=Mon, 31-Jan-2112 16:00:00 GMT");
    }

    @Override
    public void modifyWiFiChannel(int channel, RouterInterface c) {
        c.putData(RouterInterface.SUPPORT_NO);
    }

    @Override
    public void getRouterDeviceName(final RouterNameCallBack c) {
        c.putName("D-Link");
//		httpGet(WifiUtil.getWifiLYIP(mContext) + "/cgi-bin/webproc", "", new SuccessCallBack() {
//			
//			@Override
//			public void doStart() {
//				
//			}
//			
//			@Override
//			public void doFailure() {
//				c.putName("D-Link 612");
//			}
//			
//			@Override
//			public void callBack(String arg1) {
//				try {
//					int begin = arg1.indexOf("target=_blank>");
//					int a = arg1.indexOf("</a>", begin);
//
//					String name = arg1.substring(begin, a).replace(
//							"target=_blank>", "");
//
//					c.putName("D-Link " + name);
//				} catch (Exception e) {
//					c.putName("D-Link");
//				}
//			}
//		});
    }
}
