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

public class RouterPolarUtil extends RouterUtilInterface {
    /**
     * HiWiFi所有操作必须带的参数Stok
     */
    public static String stokStr;
    /**
     * 活动用户对象列表
     */
    private List<WifiUserBean> mActiveUsers = new ArrayList<WifiUserBean>();
    /**
     * Mac地址黑名单列表
     */
    private List<MacAddressFilterBean> mMacAddressFilter = new ArrayList<MacAddressFilterBean>();
    /**
     * 路由器安全对象
     */
    private RouterSafeAndPasswordBean mRouterSafeAndPasswordBean = new RouterSafeAndPasswordBean();

    public RouterPolarUtil(Context context, RouterConstantInterface constant,
                           String username, String password) {
        super(context, constant, username, password);
    }

    @Override
    public void getAllUserMacInFilter(final ContextCallBack c) {
        super.getAllUserMacInFilter(new ContextCallBack() {
            @Override
            public void putData(HashMap<String, Object> map) {
                if (isSupport(map)) {
                    BaseRouterBean baseRouterBean = (BaseRouterBean) map.get(RouterInterface.BASEDATA);
                    mMacAddressFilter = baseRouterBean.getBlackUsers();
                }
                c.putData(map);
            }
        });
    }

    /**
     * 登录
     */
    public void login(final ConnInfoCallBack call) {
        RequestParams params = new RequestParams();
        params.put("username", "admin");
        params.put("password", mPassword);
        httpPost(constant.getLogin(), constant.getLoginRefer(), params,
                new GetDataCallBack() {

                    @Override
                    public void success(String content) {
                        if (content == null || content.contains("密码错误！请重试")) {
                            call.putData(false);
                        } else {
                            stokStr = getStok(content);
                            call.putData(true);
                        }

                    }
                });

    }

    /**
     * 获取路由器安全信息
     *
     * @param c 回调
     */
    @Override
    public void getWifiSafeInfo(final ContextCallBack c) {
        super.getWifiSafeInfo(new ContextCallBack() {
            @Override
            public void putData(HashMap<String, Object> map) {
                if (isSupport(map)) {
                    BaseRouterBean baseRouterBean = (BaseRouterBean) map.get(RouterInterface.BASEDATA);
                    mRouterSafeAndPasswordBean = baseRouterBean.getRouterSafePassword();
                }
                c.putData(map);
            }
        });

    }

    /**
     * 修改路由器ssid
     *
     * @param ssid ssid
     * @param c    回调
     */
    @Override
    public void modifyWifiSSid(String ssid, final RouterInterface c) {
        String string = "ssid=" + ssid + "&encryption=mixed-psk&key=" + mRouterSafeAndPasswordBean.getPassword() + "&key_show=" + mRouterSafeAndPasswordBean.getPassword() + "&key_cache=" + mRouterSafeAndPasswordBean.getPassword() + "&old_ssid=" + WiFiUtil.getCurrentWifiName(mContext) + "&device=radio0.network1&old_password=" + mRouterSafeAndPasswordBean.getPassword();
        httpGet(constant.modifySSid(string), constant.modifySSidReferer(), new GetDataCallBack(c) {

            @Override
            public void start() {
                c.putData(RouterInterface.SUPPORT_YES);
            }
        });
    }

    /**
     * 修改管理员密码
     *
     * @param user 管理员密码对象
     * @param c    回调
     */
    @Override
    public void modifyManagerPassword(RouterManagerUserBean user, final RouterInterface c) {
        RequestParams params = new RequestParams();
        params.put("old_password", user.getOldPassword());
        params.put("password", user.getNewPassword());
        params.put("password2", user.getNewPassword());
        httpPost(constant.modifyLoginPassword(user), constant.modifyLoginPasswordReferer(), params, new GetDataCallBack(c) {
            @Override
            public void success(String content) {
                if ("{ \"msg\": \"\", \"code\": 0 }".equals(content)) {
                    c.putData(RouterInterface.SUPPORT_YES);
                } else {
                    c.putData(RouterInterface.SUPPORT_FAIL);
                }
            }
        });
    }

    /**
     * 修改WiFi连接密码
     *
     * @param password 新密码
     * @param c        回调
     */
    @Override
    public void modifyWifiPassword(String password, final RouterInterface c) {
        String string = "ssid=" + WiFiUtil.getCurrentWifiName(mContext) + "&encryption=mixed-psk&key=" + password + "&key_show=" + password + "&key_cache=" + mRouterSafeAndPasswordBean.getPassword() + "&old_ssid=" + WiFiUtil.getCurrentWifiName(mContext) + "&device=radio0.network1&old_password=" + mRouterSafeAndPasswordBean.getPassword();
        httpGet(constant.modifyWifiPassword(string), constant.modifyWifiPasswordReferer(), new GetDataCallBack() {

            @Override
            public void start() {
                c.putData(RouterInterface.SUPPORT_YES);
            }
        });
    }

    /**
     * 获取stok字符串
     *
     * @param html 返回页面
     * @return string类型Stok
     */
    private String getStok(final String html) {
        String cut = null;
        try {
            int index = html.indexOf("stok=");
            int end = html.indexOf("/", index);
            cut = html.substring(index, end).replace("stok=", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cut;
    }

    /**
     * 获取所有活动用户
     *
     * @param c 回调
     */
    @Override
    public void getAllActiveUser(final ContextCallBack c) {
        mActiveUsers = new ArrayList<WifiUserBean>();
        httpGet(constant.getAllActiveUsers(stokStr), constant.getAllActiveUsersReferer(), new GetDataCallBack(c) {
            @SuppressWarnings("unchecked")
            @Override
            public void success(String content) {
                if ("".equals(content) || null == content || content.contains("not auth")) {
                    c.putData(null);
                    return;
                }
                HashMap<String, Object> map = constant.getHtmlParser().getRouterData(RouterInterface.UTIL_GET_ACTIVE_USERS, content);
                if (isSupport(map)) {
                    BaseRouterBean bean = (BaseRouterBean) map.get(RouterInterface.BASEDATA);
                    mActiveUsers = bean.getActiveUsers();
                }
                c.putData(map);
            }
        });
    }

    /**
     * 拉黑
     *
     * @param macAddress 需要拉黑的mac地址
     * @param c          回调
     */
    @Override
    public void stopUserByMacAddress(String macAddress, final RouterInterface c) {
        // %5B%5D 为一对中括号([])  %3A为冒号(:)
        String str = "status=deny";
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < mMacAddressFilter.size(); i++) {
            str += "&macs%5B%5D=" + mMacAddressFilter.get(i).getMacAddress();
        }
        str += "&macs%5B%5D=";
        str += "&macs%5B%5D=" + macAddress;
        str += "&device=radio0.network1";
        str = str.replaceAll(":", "%3A").replaceAll("-", "%3A");
        if (mMacAddressFilter.size() == 64) {
            c.putData(RouterInterface.SUPPORT_FAIL);
            return;
        }
        httpGet(constant.stopOneMacAddressUri(str), constant.stopOneMacAddressReferer(), new GetDataCallBack(c) {
            @Override
            public void success(String content) {
                if (content.contains("{ \"msg\": \"\", \"code\": 0 }")) {
                    c.putData(RouterInterface.SUPPORT_YES);
                } else {
                    c.putData(RouterInterface.SUPPORT_FAIL);
                }
            }
        });
    }

    /**
     * 删除拉黑
     *
     * @param macAddress Mac地址
     * @param c          回调
     */
    @Override
    public void deleteOneUserFromMacFilter(String macAddress, final ConnInfoCallBack c) {
        // %5B%5D 为一对中括号([])  %3A为冒号(:)
        String str = "status=deny";
        if (mMacAddressFilter.size() != 1) {
            for (int i = 0; i < mMacAddressFilter.size(); i++) {
                if (macAddress.equals(mMacAddressFilter.get(i).getMacAddress())) {
                    mMacAddressFilter.remove(i);
                    i--;
                    continue;
                }
                str += "&macs%5B%5D=" + mMacAddressFilter.get(i).getMacAddress();
            }
        } else {
            str += "&macs%5B%5D=&macs%5B%5D=&macs%5B%5D=&macs%5B%5D=&macs%5B%5D=&macs%5B%5D=&macs%5B%5D=";
        }
        str += "&macs%5B%5D=";
        str += "&device=radio0.network1";
        str = str.replaceAll(":", "%3A");

        httpGet(constant.deleteOneMacAddress(str), constant.deleteOneMacAddressReferer(), new GetDataCallBack(c) {
            @Override
            public void success(String content) {
                if (content.contains("{ \"msg\": \"\", \"code\": 0 }")) {
                    c.putData(true);
                } else {
                    c.putData(false);
                }
            }
        });
    }

    /**
     * 修改wifi信道
     *
     * @param channel 信道
     * @param c       回调
     */
    @Override
    public void modifyWiFiChannel(int channel, final RouterInterface c) {
        httpGet(constant.modifyWiFiChannel(channel), constant.modifyWiFiChannelReferer(), new GetDataCallBack(c) {
            @Override
            public void success(String content) {
                if (content.contains("{ \"msg\": \"\", \"code\": 0 }")) {
                    c.putData(RouterInterface.SUPPORT_YES);
                } else {
                    c.putData(RouterInterface.SUPPORT_FAIL);
                }
            }
        });

    }

    /**
     * 重启路由
     *
     * @param c 回调
     */
    @Override
    public void reboot(final ConnInfoCallBack c) {
        httpGet(constant.rebootRouter(), constant.rebootRouterReferer(), new GetDataCallBack(c) {

            @Override
            public void start() {
                c.putData(true);
            }
        });
    }

    /**
     * 获取所有用户
     */
    @Override
    public void getAllUser(final ContextCallBack c) {
        BaseRouterBean bean = new BaseRouterBean();
        bean.setDhcpUsers(mActiveUsers);
        c.putData(supportYes(bean));
    }

    @Override
    public void getRouterDeviceName(final RouterNameCallBack c) {
        c.putName("极路由");
//        httpGet(WiFiUtil.getWiFiLYIP(mContext) + "/login_web.html", "",
//                new SuccessCallBack() {
//
//                    @Override
//                    public void doStart() {
//                    }
//
//                    @Override
//                    public void doFailure() {
//                        c.putName("极路由");
//                    }
//
//                    @Override
//                    public void callBack(String arg1) {
//                        try {
//                            int index = arg1.indexOf("系统版本 : ");
//                            if (index == -1) {
//                                c.putName("极路由");
//                                return;
//                            }
//                            int end = arg1.indexOf(" - ");
//                            String cut = arg1.substring(index, end).replace(
//                                    "系统版本 : ", "");
//                            c.putName(cut);
//                        } catch (Exception e) {
//                            c.putName("极路由");
//                        }
//                    }
//                });
    }

    /**
     * 添加其他信息
     */
    @Override
    protected void addOtherRefer() {
        client.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 1.7; .NET CLR 1.1.4322; CIBA; .NET CLR 2.0.50727)");
    }

}
