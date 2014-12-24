package com.wolf.routermanager.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 路由器管理员账号密码
 * 
 * @author wuwf
 * 
 */
@Table(name = "routeraccount")
public class RouterAccount {

	@Id(column="routeraccount_id")
	private int id;
	@Column(column = "router_mac")
	private String routerMac;
	@Column(column = "account")
	private String account;
	@Column(column = "password")
	private String password;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRouterMac() {
		return routerMac;
	}

	public void setRouterMac(String routerMac) {
		this.routerMac = routerMac;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
