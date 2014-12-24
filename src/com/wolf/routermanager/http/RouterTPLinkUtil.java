package com.wolf.routermanager.http;

import android.content.Context;
import com.wolf.routermanager.bean.BaseRouterBean;
import com.wolf.routermanager.bean.MacAddressFilterBean;
import com.wolf.routermanager.bean.TpLinkRouterBean;
import com.wolf.routermanager.bean.WifiUserBean;
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
 * 路由器各接口功能
 *
 * @author wuwf
 */
public class RouterTPLinkUtil extends RouterUtilInterface {

    private List<WifiUserBean> mActiveUsers = new ArrayList<WifiUserBean>();
    private List<MacAddressFilterBean> allMacFilterList = new ArrayList<MacAddressFilterBean>();

    public RouterTPLinkUtil(Context context, RouterConstantInterface constant,
                            String username, String password) {
        super(context, constant, username, password);
    }

    @Override
    protected void login(ConnInfoCallBack c) {
        c.putData(true);
    }

    /**
     * 获取所有连接的活动用户
     */
    public void getAllActiveUser(final ContextCallBack c) {
        mActiveUsers = new ArrayList<WifiUserBean>();
        // 所有连接到本路由器的设备集合
        httpGet(constant.getAllActiveUsers("1"),
                constant.getAllActiveUsersReferer(), new GetDataCallBack(c) {

                    @SuppressWarnings("unchecked")
                    @Override
                    public void success(String content) {
                        // 看看一共多少活动的用户，判断一共多少页
                        final HashMap<String, Object> map = constant.getHtmlParser()
                                .getRouterData(RouterInterface.UTIL_GET_ACTIVE_USERS, content);
                        //如果解析出了异常，就直接返回
                        if (!isSupport(map)) {
                            c.putData(map);
                            return;
                        }
                        TpLinkRouterBean bean = (TpLinkRouterBean) map.get(RouterInterface.BASEDATA);

                        final int count = Integer.valueOf(bean.getCount());
                        mActiveUsers = bean.getActiveUsers();
                        // 如果只有一页，直接返回所有的用户就OK
                        if (mActiveUsers.size() < 8) {
                            c.putData(map);
                            return;
                        }
                        // 每8个占一页
                        for (int i = 2; i <= 1 + (count - 1) / 8; i++) {
                            httpGet(constant.getAllActiveUsers(i + ""),
                                    constant.getAllActiveUsersReferer(),
                                    new GetDataCallBack() {

                                        @Override
                                        public void success(String content) {
                                            // 将该页所有的用户都添加到总的集合列表里
                                            HashMap<String, Object> map1 = constant.getHtmlParser()
                                                    .getRouterData(RouterInterface.UTIL_GET_ACTIVE_USERS, content);
                                            TpLinkRouterBean baseRouterBean = (TpLinkRouterBean) map1.get(RouterInterface.BASEDATA);
                                            List<WifiUserBean> activeUsers = baseRouterBean.getActiveUsers();
                                            mActiveUsers.addAll(activeUsers);

                                            // 如果全部取完了
                                            if (mActiveUsers.size() == count) {
                                                BaseRouterBean bean = new BaseRouterBean();
                                                bean.setActiveUsers(mActiveUsers);
                                                c.putData(supportYes(bean));
                                            }
                                        }

                                        @Override
                                        public void failure() {
                                            c.putData(map);
                                        }

                                    });
                        }

                    }
                });
    }

    /**
     * 获取所有在黑名单中的mac地址集合
     */
    public void getAllUserMacInFilter(final ContextCallBack c) {
        // 获取所有在mac地址过滤功能列表里的数据
        httpGet(constant.getAllMacAddressInFilter(),
                constant.getAllMacAddressInFilterReferer(),
                new GetDataCallBack(c) {
                    @Override
                    public void success(String content) {
                        final HashMap<String, Object> map = constant.getHtmlParser()
                                .getRouterData(RouterInterface.UTIL_GET_BLACK_USERS, content);
                        if (!isSupport(map)) {
                            c.putData(map);
                            return;
                        }
                        BaseRouterBean bean = (BaseRouterBean) map.get(RouterInterface.BASEDATA);
                        allMacFilterList = bean.getBlackUsers();
                        // 第一页装满了才去看第二页
                        if (allMacFilterList.size() < 8) {
                            c.putData(map);
                            return;
                        }
                        httpGet(constant.getAllMacAddressInFilter()
                                        + "?Page=2",
                                constant.getAllMacAddressInFilterReferer(),
                                new GetDataCallBack() {

                                    @Override
                                    public void success(String content) {
                                        final HashMap<String, Object> secondMap = constant.getHtmlParser()
                                                .getRouterData(RouterInterface.UTIL_GET_BLACK_USERS, content);
                                        final List<MacAddressFilterBean> second = ((BaseRouterBean) secondMap.get(RouterInterface.BASEDATA)).getBlackUsers();
                                        // 如果第二页没东西，或者第二页取的和第一页一样，则直接返回不再取值
                                        if (second
                                                .get(0)
                                                .getMacAddress()
                                                .equals(allMacFilterList
                                                        .get(0)
                                                        .getMacAddress())) {
                                            c.putData(map);
                                            return;
                                        }
                                        // 如果第二页没满
                                        if (second.size() != 8) {
                                            allMacFilterList.addAll(second);
                                            BaseRouterBean baseRouterBean = new BaseRouterBean();
                                            baseRouterBean.setBlackUsers(allMacFilterList);
                                            final HashMap<String, Object> secondFinalMap = supportYes(baseRouterBean);
                                            c.putData(secondFinalMap);
                                            return;
                                        }
                                        //如果第二页也满了
                                        httpGet(constant
                                                        .getAllMacAddressInFilter()
                                                        + "?Page=3",
                                                constant.getAllMacAddressInFilterReferer(),
                                                new GetDataCallBack() {

                                                    @Override
                                                    public void success(
                                                            String content) {
                                                        final HashMap<String, Object> threeMap = constant
                                                                .getHtmlParser()
                                                                .getRouterData(RouterInterface.UTIL_GET_BLACK_USERS, content);
                                                        List<MacAddressFilterBean> three = ((BaseRouterBean) threeMap.get(RouterInterface.BASEDATA)).getBlackUsers();
                                                        // 第三页有东西
                                                        if (!three
                                                                .get(0)
                                                                .getMacAddress()
                                                                .equals(second
                                                                        .get(0)
                                                                        .getMacAddress())) {
                                                            allMacFilterList
                                                                    .addAll(three);
                                                        }

                                                        BaseRouterBean baseRouterBean = new BaseRouterBean();
                                                        baseRouterBean.setBlackUsers(allMacFilterList);
                                                        c.putData(supportYes(baseRouterBean));
                                                    }

                                                    @Override
                                                    public void failure() {
                                                        BaseRouterBean baseRouterBean = new BaseRouterBean();
                                                        baseRouterBean.setBlackUsers(allMacFilterList);
                                                        c.putData(supportYes(baseRouterBean));
                                                    }

                                                });
                                    }


                                    @Override
                                    public void failure() {
                                        c.putData(map);
                                    }


                                });
                    }
                });
    }

    /**
     * <p>
     * 根据mac地址屏蔽联网功能，停该mac的网
     * </p>
     * <p/>
     * 该功能添加进来的用户将进入黑名单
     */
    public void stopUserByMacAddress(String macAddress, final RouterInterface c) {
        // 要停掉某个mac地址，需要要mac添加到列表，让过滤规则为禁止，然后开启mac地址过滤功能
        httpGet(constant.stopOneMacAddressUri(macAddress),
                constant.stopOneMacAddressReferer(), new GetDataCallBack(c) {});
    }

    @Override
    public void getDNSSafe(final ConnInfoCallBack call) {
        httpGet(constant.getDnsSafeInfo(), constant.getDnsSafeInfoReferer(),
                new GetDataCallBack() {

                    @Override
                    public void success(String content) {
                        HashMap<String, Object> result = constant.getHtmlParser()
                                .getRouterData(RouterInterface.UTIL_GET_DNSCHECK, content);
                        if (!isSupport(result)) {
                            call.putData(true);
                            return;
                        }
                        TpLinkRouterBean bean = (TpLinkRouterBean) result.get(RouterInterface.UTIL_GET_WEBCHECK);
                        call.putData(bean.isDnsSafe());
                    }

                    @Override
                    public void failure() {
                        call.putData(true);
                    }
                });
    }

    @Override
    public void setDNSToSafe(final ConnInfoCallBack c) {
        httpGet(constant.setDnsToSafe(), constant.setDnsToSafeReferer(),
                new GetDataCallBack(c) {});
    }

    @Override
    public void getWeb(final ConnInfoCallBack c) {
        httpGet(constant.getCheckWeb(), constant.getCheckWebReferer(),
                new GetDataCallBack(c) {

                    @Override
                    public void success(String content) {
                        HashMap<String, Object> result = constant.getHtmlParser()
                                .getRouterData(RouterInterface.UTIL_GET_WEBCHECK, content);
                        if (!isSupport(result)) {
                            c.putData(true);
                            return;
                        }
                        TpLinkRouterBean bean = (TpLinkRouterBean) result.get(RouterInterface.UTIL_GET_WEBCHECK);
                        c.putData(bean.isWebSafe());
                    }

                    @Override
                    public void failure() {
                        c.putData(true);
                    }

                });
    }

    @Override
    public void getWebSetting(final ConnInfoCallBack c) {
        httpGet(constant.getCheckWebSetting(), constant.getCheckWebReferer(),
                new GetDataCallBack(c) {});
    }

    @Override
    public void getRouterDeviceName(final RouterNameCallBack c) {
        c.putName("TP-Link");
//        httpGet(WiFiUtil.getWifiLYIP(mContext), "", new SuccessCallBack() {
//
//            @Override
//            public void callBack(String arg1) {
//                try {
//                    int begin = arg1.indexOf("<title>");
//                    int end = arg1.indexOf("</title>");
//                    String title = arg1.substring(begin, end);
//                    String name = title.replace("<title>", "").replace(
//                            "TL-", "");
//                    c.putName("TP-Link " + name);
//                } catch (Exception e) {
//                    c.putName("TP-Link");
//                }
//            }
//
//            @Override
//            public void doFailure() {
//                c.putName("TP-Link");
//            }
//
//            @Override
//            public void doStart() {
//
//            }
//
//
//        });
    }

}
