package com.wolf.routermanager.http.inter;

import android.content.Context;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wolf.routermanager.bean.BaseRouterBean;
import com.wolf.routermanager.bean.RouterManagerUserBean;
import com.wolf.routermanager.common.tool.Base64;
import com.wolf.routermanager.inter.ConnInfoCallBack;
import com.wolf.routermanager.inter.ContextCallBack;
import com.wolf.routermanager.inter.GetDataCallBack;
import com.wolf.routermanager.inter.RouterInterface;
import com.wolf.routermanager.urlconstant.inter.RouterConstantInterface;
import org.apache.http.Header;

import java.util.HashMap;

/**
 * 路由器网络请求功能的接口
 *
 * @author wuwf
 */
public abstract class RouterUtilInterface {
    /**
     * 实例化对象
     */
    protected static AsyncHttpClient client = new AsyncHttpClient();
    protected Context mContext;
    /**
     * ip应该是由调用者传入
     */
    protected RouterConstantInterface constant;
    protected String basic = "admin:admin";
    protected String mUserName;
    protected String mPassword;

    static {
        client.setTimeout(11000); // 设置链接超时，如果不设置，默认为10s
        client.addHeader("Connection", "Keep-Alive");
    }

    public RouterUtilInterface(Context context,
                               RouterConstantInterface constant, String username, String password) {
        this.mContext = context;
        this.constant = constant;
        this.mUserName = username;
        this.mPassword = password;
        this.basic = username + ":" + password;
    }

    /**
     * 获取所有黑名单中的Mac地址
     *
     * @param c 回调
     */
    public void getAllUserMacInFilter(final ContextCallBack c) {
        httpGet(constant.getAllMacAddressInFilter(), constant.getAllMacAddressInFilterReferer(), new GetDataCallBack(c) {
            @Override
            public void success(String content) {
                HashMap<String, Object> map = constant.getHtmlParser().getRouterData(RouterInterface.UTIL_GET_BLACK_USERS, content);
                c.putData(map);
            }
        });
    }

    /**
     * 获取当前活动用户的具体操作
     *
     * @param c 回调
     */
    protected void getAllActiveUser(final ContextCallBack c) {
        // 所有连接到本路由器的设备集合
        httpGet(constant.getAllActiveUsers(null),
                constant.getAllActiveUsersReferer(),
                new GetDataCallBack(c) {

                    @Override
                    public void success(String content) {
                        c.putData(constant
                                .getHtmlParser()
                                .getRouterData(RouterInterface.UTIL_GET_ACTIVE_USERS, content));
                    }
                });

    }

    /**
     * 获取当前连接的所有用户，供外界调用
     *
     * @param c
     */
    public final void getActiveUser(final ContextCallBack c) {
        login(new ConnInfoCallBack() {
            @Override
            public void putData(boolean flag) {
                //如果登录失败，则返回null
                if (!flag) {
                    c.putData(null);
                    return;
                }
                getAllActiveUser(c);
            }
        });

    }

    public abstract void stopUserByMacAddress(String macAddress,
                                              final RouterInterface c);

    public abstract void getRouterDeviceName(final RouterNameCallBack c);

    protected abstract void login(final ConnInfoCallBack c);

    /**
     * 修改wifi密码（加密方式等等）
     */
    public void modifyWifiPassword(String password, final RouterInterface c) {
        httpGet(constant.modifyWifiPassword(password),
                constant.modifyWifiPasswordReferer(),
                new GetDataCallBack(c) {

                    @Override
                    public void success(String content) {
                        c.putData(RouterInterface.SUPPORT_YES);
                        reboot(new ConnInfoCallBack() {

                            @Override
                            public void putData(boolean flag) {
                                // TODO Auto-generated method stub

                            }
                        });
                    }
                });
    }

    /**
     * 获取路由器wifi的加密方式和密码
     */
    public void getWifiSafeInfo(final ContextCallBack c) {
        httpGet(constant.getWifiSafeInfo(), constant.getWifiSafeInfoReferer(),
                new GetDataCallBack(c) {

                    @Override
                    public void success(String content) {
                        HashMap map = constant.getHtmlParser().getRouterData(RouterInterface.UTIL_GET_ROUTERSAFE, content);
                        c.putData(map);
                    }
                });
    }

    /**
     * 修改wifi的ssid
     */
    public void modifyWifiSSid(String ssid, final RouterInterface c) {
        httpGet(constant.modifySSid(ssid), constant.modifySSidReferer(),
                new GetDataCallBack(c) {

                    @Override
                    public void success(String content) {
                        c.putData(RouterInterface.SUPPORT_YES);
                        // 重启路由
                        reboot(new ConnInfoCallBack() {

                            @Override
                            public void putData(boolean flag) {
                                // TODO Auto-generated method stub

                            }
                        });
                    }
                });
    }


    /**
     * 修改wifi信道
     *
     * @param channel 信道
     */
    public void modifyWiFiChannel(int channel, final RouterInterface c) {
        httpGet(constant.modifyWiFiChannel(channel), constant.modifyWiFiChannelReferer(), new GetDataCallBack(c) {
            @Override
            public void success(String content) {
                c.putData(RouterInterface.SUPPORT_YES);
            }
        });
    }

    /**
     * 重启路由器(默认成功)
     *
     * @param c
     */
    public void reboot(final ConnInfoCallBack c) {
        httpGet(constant.rebootRouter(), constant.rebootRouterReferer(),
                new GetDataCallBack() {

                    @Override
                    public void start() {
                        c.putData(true);
                    }

                });
    }


    /**
     * 获取所有的连接用户
     */
    public void getAllUser(final ContextCallBack c) {
        // 所有连接到本路由器的设备集合
        httpGet(constant.getAllUsers(), constant.getAllUsersReferer(),
                new GetDataCallBack(c) {

                    @Override
                    public void success(String content) {
                        c.putData(constant.getHtmlParser().getRouterData(RouterInterface.UTIL_GET_DHCP_USERS, content));
                    }
                });
    }

    /**
     * 开启mac地址过滤功能
     */
    public void openMacFilter(final ConnInfoCallBack c) {
        // 此操作执行后会短暂关闭wifi，并且没有返回值，会走failure
        httpGet(constant.openStopMacAddressFilter(),
                constant.openStopMacAddressFilterReferer(),
                new GetDataCallBack() {

                    // 默认点击后就已经开启
                    @Override
                    public void start() {
                        c.putData(true);
                    }
                });
    }


    /**
     * 设置过滤规则为禁止列表mac地址访问网络
     */
    public void setMacFilterRuleForbidden(final ConnInfoCallBack c) {
        // 设置过滤规则为禁止列表中生效的mac地址访问本网络
        httpGet(constant.stopAllMacAddress(),
                constant.stopAllMacAddressReferer(), new GetDataCallBack(c) {

                    // 点击后禁止列表mac地址访问网络
                    @Override
                    public void success(String content) {
                        c.putData(true);
                    }
                });

    }


    /**
     * 从黑名单中删除某个mac地址
     */
    public void deleteOneUserFromMacFilter(final String macAddress,
                                           final ConnInfoCallBack c) {
        httpGet(constant.deleteOneMacAddress(macAddress),
                constant.deleteOneMacAddressReferer(), new GetDataCallBack(c) {

                    @Override
                    public void success(String content) {
                        c.putData(true);
                    }
                });
    }

    /**
     * 查看过滤规则和是否已开启过滤
     */
    protected void checkFilterAndRule(final ConnInfoCallBack c) {
        // 检查规则
        httpGet(constant.getCheckFilter(), constant.getCheckFilterReferer(),
                new GetDataCallBack(c) {

                    // 点击后禁止列表mac地址访问网络
                    @Override
                    public void success(String content) {
                        //获取系统的mac过滤开启状态
                        HashMap map = constant.getHtmlParser().getRouterData(RouterInterface.UTIL_GET_FILTERTYPE, content);
                        //如果是成功取得了状态，并且已经开启了过滤
                        if (isSupport(map)) {
                            //查看是否已经开启了
                            boolean hasOpen = ((BaseRouterBean) map.get(RouterInterface.BASEDATA)).isMacFilterOpen();
                            if (hasOpen) {
                                c.putData(true);
                                return;
                            }
                            c.putData(false);
                            return;
                        }
                        c.putData(false);
                    }
                });

    }

    /**
     * 修改管理员账号密码
     */
    public void modifyManagerPassword(final RouterManagerUserBean user,
                                      final RouterInterface c) {
        httpGet(constant.modifyLoginPassword(user),
                constant.modifyLoginPasswordReferer(), new GetDataCallBack(c) {

                    @Override
                    public void success(String content) {
                        c.putData(RouterInterface.SUPPORT_YES);
                    }
                });
    }

    /**
     * 校验路由规则，通过后走action
     */
    public final void checkRule() {
        checkFilterAndRule(new ConnInfoCallBack() {

            @Override
            public void putData(boolean filter) {
                // 如果都已经开启了，则跳转就行
                if (filter == true) {
                    return;
                }
                //开启过滤，有的路由器只需要开启一个，所以捕获一下异常
                try {
                    setMacFilterRuleForbidden(new ConnInfoCallBack() {

                        @Override
                        public void putData(boolean f) {
                        }
                    });
                    openMacFilter(new ConnInfoCallBack() {

                        @Override
                        public void putData(boolean f) {
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    /**
     * 判断DNS是否正常
     */
    public void getDNSSafe(final ConnInfoCallBack call) {
        call.putData(true);
    }

    /**
     * 设置让dns为安全
     */
    public void setDNSToSafe(final ConnInfoCallBack c) {
        c.putData(true);
    }

    /**
     * 远程web管理
     */
    public void getWeb(final ConnInfoCallBack c) {
        c.putData(false);
    }

    /**
     * 远程web管理设置
     *
     * @param c
     */
    public void getWebSetting(final ConnInfoCallBack c) {
        c.putData(true);
    }

    /**
     * 判断是否是支持
     */
    public boolean isSupport(HashMap<String, Object> map) {
        int i = (Integer) map.get(RouterInterface.SUPPORT);
        if(RouterInterface.SUPPORT_YES == i) {
            return true;
        }
        return false;
    }

    /**
     * 支持的功能
     */
    protected HashMap<String, Object> supportYes(BaseRouterBean bean) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(RouterInterface.SUPPORT, RouterInterface.SUPPORT_YES);
        map.put(RouterInterface.BASEDATA, bean);

        return map;
    }

    /**
     * 从服务器取数据，只get数据
     *
     * @param url             地址
     * @param getDataCallBack 回调
     */
    protected void httpGet(String url, String referer,
                           final GetDataCallBack getDataCallBack) {
        get(url, referer, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                getDataCallBack.start();
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {
                getDataCallBack.failure();
            }

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                getDataCallBack.success(new String(arg2));
            }

        });
    }

    protected void httpPost(String url, String referer, RequestParams params,
                            final GetDataCallBack getDataCallBack) {
        post(url, referer, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                getDataCallBack.start();
            }

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                // 回调
                getDataCallBack.success(new String(arg2));
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {
                getDataCallBack.failure();
            }
        });
    }

    private void get(String urlString, String referer,
                     AsyncHttpResponseHandler res) {
        addRefer(referer);
        client.setURLEncodingEnabled(false);
        client.get(urlString, res);
    }

    private void post(String urlString, String referer, RequestParams params,
                      AsyncHttpResponseHandler res) {
        addRefer(referer);
        client.post(urlString, params, res);
    }

    private void addRefer(String referer) {
        client.addHeader("Referer", referer);
        addOtherRefer();
    }

    /**
     * 添加一些其他的head信息
     */
    protected void addOtherRefer() {
        client.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 1.7; .NET CLR 1.1.4322; CIBA; .NET CLR 2.0.50727)");
        client.addHeader("Cookie",
                "Authorization=Basic " + Base64.encode(basic));
        client.addHeader("Authorization", "Basic " + Base64.encode(basic));
    }

    public interface RouterNameCallBack {
        void putName(String name);
    }

}
