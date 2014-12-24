package com.wolf.routermanager.bean;

/**
 * 管理员账号和密码
 * 
 * @author wuwf
 * 
 */
public class RouterManagerUserBean {

	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 老密码
	 */
	private String oldPassword;
	/**
	 * 新密码
	 */
	private String newPassword;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
