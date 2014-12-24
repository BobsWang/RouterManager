package com.wolf.routermanager.common.db;

import android.content.Context;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.wolf.routermanager.bean.RouterAccount;
import com.wolf.routermanager.common.WiFiUtil;

/**
 * 用于保存路由器管理员账号密码的数据库
 * @author wuwf
 *
 */
public class RouterManagerDB {
	private Context mContext;
	private DbUtils mDBUtils;
	
	public RouterManagerDB(Context context) {
		this.mContext = context;
		mDBUtils = DbUtils.create(context);
	}
	
	/**
	 * 保存新的路由器账号密码
	 */
	public void saveNewAccount(RouterAccount router) {
		try {
			mDBUtils.save(router);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新保存的路由器账号密码
	 */
	public void updateAccount(RouterAccount newRouterAccount) {
		RouterAccount oldRouterAccount = getRouterAccountAndPass();
		if (oldRouterAccount != null) {
			try {
				mDBUtils.delete(oldRouterAccount);
			} catch (DbException e) {
				e.printStackTrace();
			}
		}
		try {
			mDBUtils.save(newRouterAccount);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 只修改管理员密码，更新数据库
	 */
	public void updateAccount(String password) {
		String oldNameString = "admin";
		RouterAccount oldRouterAccount = getRouterAccountAndPass();
		if (oldRouterAccount != null) {
			oldNameString = oldRouterAccount.getAccount();
			
			try {
				mDBUtils.delete(oldRouterAccount);
			} catch (DbException e) {
				e.printStackTrace();
			}
		}
		
		RouterAccount account = new RouterAccount();
		account.setAccount(oldNameString);
		account.setPassword(password);
		account.setRouterMac(WiFiUtil.getWifiLYMAC(mContext));
		try {
			mDBUtils.save(account);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取数据库里已保存的路由器管理员账号密码
	 */
	public RouterAccount getRouterAccountAndPass() {
		RouterAccount routerAccount = null;
		try {
			routerAccount = mDBUtils.findFirst(Selector.from(RouterAccount.class)
					.where("router_mac", "=", WiFiUtil.getWifiLYMAC(mContext)));
		} catch (DbException e) {
			e.printStackTrace();
		}
		return routerAccount;
	}
}
