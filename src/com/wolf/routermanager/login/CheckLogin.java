package com.wolf.routermanager.login;

import android.content.Context;
import com.wolf.routermanager.bean.AllRouterInfoBean;
import com.wolf.routermanager.bean.BaseRouterBean;
import com.wolf.routermanager.common.RouterUtilFactory;
import com.wolf.routermanager.http.inter.RouterUtilInterface;
import com.wolf.routermanager.inter.ConnInfoCallBack;
import com.wolf.routermanager.inter.ContextCallBack;
import com.wolf.routermanager.inter.RouterInterface;

import java.util.HashMap;

/**
 * 检查路由登陆情况
 *
 * @author wuwf
 */
public class CheckLogin {
    /**
     * 上下文
     */
    private RouterUtilInterface mRouterUtil;

    private RouterUtilFactory mRouterFactory;

    public CheckLogin(Context context) {
        mRouterFactory = new RouterUtilFactory(context);
    }

    /**
     * 默认的登陆
     */
    public void login(ConnInfoCallBack call) {
        login(null, null, call);
    }

    /**
     * 带密码的登陆
     */
    public void login(final String accountString, final String password,
                      final ConnInfoCallBack call) {
        mRouterUtil = mRouterFactory.getRouterUtil(accountString, password);
        // 初始化它
        checkLogin(call);
    }

    /**
     * 检查是否登陆成功
     */
    private void checkLogin(final ConnInfoCallBack call) {
        // 获取所有用户列表，判断账号密码是否正确
        mRouterUtil.getActiveUser(new ContextCallBack() {

            @SuppressWarnings("unchecked")
            @Override
            public void putData(HashMap<String, Object> map) {
                //如果获取失败
                if (!mRouterUtil.isSupport(map)) {
                    call.putData(false);
                    return;
                }
                BaseRouterBean baseRouterBean = (BaseRouterBean) map.get(RouterInterface.BASEDATA);
                //如果活动用户数量小于1，说明还是有错误，活动用户至少有1个
                if (baseRouterBean.getActiveUsers().size() <= 0) {
                    call.putData(false);
                    return;
                }


                // 如果登陆成功，更新数据库里的账号信息
                updateAccountInfoInDB();

                AllRouterInfoBean.routerUtilInterface = mRouterUtil;
                // 将用户数据保存
                AllRouterInfoBean.allActiveWifiUser = baseRouterBean.getActiveUsers();

                // 获取黑名单里的数据
                getAllMacInFilter(call);
                // 设置登陆标记为true
                AllRouterInfoBean.hasLogin = true;
            }

        });

    }

    /**
     * 更新数据库里的账号密码信息
     */
    private void updateAccountInfoInDB() {
        mRouterFactory.saveAccount();
    }

    /**
     * 获取黑名单数据
     *
     * @param call
     */
    private void getAllMacInFilter(final ConnInfoCallBack call) {
        // 获取所有黑名单
        mRouterUtil.getAllUserMacInFilter(new ContextCallBack() {

            @SuppressWarnings("unchecked")
            @Override
            public void putData(HashMap<String, Object> map) {
                // 如果成功
                if (!mRouterUtil.isSupport(map)) {
                    call.putData(false);
                    return;
                }
                BaseRouterBean baseRouterBean = (BaseRouterBean) map.get(RouterInterface.BASEDATA);
                // 将用户数据保存
                AllRouterInfoBean.allMacFilterUser = baseRouterBean.getBlackUsers();
                mRouterUtil.getAllUser(new ContextCallBack() {
                    @Override
                    public void putData(HashMap<String, Object> map) {
                        // 如果成功
                        if (!mRouterUtil.isSupport(map)) {
                            call.putData(false);
                            return;
                        }
                        BaseRouterBean baseRouterBean = (BaseRouterBean) map.get(RouterInterface.BASEDATA);
                        AllRouterInfoBean.allDhcpUser = baseRouterBean.getDhcpUsers();
                        // 登陆成功
                        call.putData(true);
                        // 开始校验过滤规则
                        mRouterUtil.checkRule();
                    }
                });
            }
        });
    }

}
