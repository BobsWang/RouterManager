package com.wolf.routermanager;

import android.app.Activity;
import android.os.Bundle;
import com.wolf.routermanager.bean.AllRouterInfoBean;
import com.wolf.routermanager.common.RouterUtilFactory;
import com.wolf.routermanager.inter.ConnInfoCallBack;
import com.wolf.routermanager.login.CheckLogin;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if (!RouterUtilFactory.isSupportRouter(this)) {
            return;
        }
        CheckLogin checkLogin = new CheckLogin(this);
        checkLogin.login("admin", "admin", new ConnInfoCallBack() {
            @Override
            public void putData(boolean flag) {
                System.out.print(flag);
                                        //测试提交
                System.out.print(AllRouterInfoBean.allActiveWifiUser);
            }
        });
    }
}
