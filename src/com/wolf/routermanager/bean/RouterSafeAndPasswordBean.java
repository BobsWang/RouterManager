package com.wolf.routermanager.bean;

/**
 * 路由器加密方式和
 * 
 * @author wuwf
 * 
 */
public class RouterSafeAndPasswordBean {
	/**
	 * 加密方式（取第三个数字，0是不加密，3是wpa-psk加密）
	 */
	private int type = 3;
	/**
	 * wifi连接密码
	 */
	private String password;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
