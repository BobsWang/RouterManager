package com.wolf.routermanager.bean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * 存储已连接的用户信息的javabean
 * 
 * @author 狼骑兵
 * @date 2014-06-25
 */
@SuppressWarnings("serial")
public class WifiUserBean implements Serializable{

	private int id;
	/**
	 * 机器名，如android-2223643254353
	 */
	private String userName;
	/**
	 * mac地址
	 */
	private String macAddress;
	/**
	 * ip地址
	 */
	private String ipAddress;
	/**
	 * 已经持续使用的时间
	 */
	private String validTime;
	/**
	 * 接受数据包数
	 */
	private String receiveData;
	/**
	 * 发送数据包数
	 */
	private String outputData;
	/**
	 * 信任时的别名
	 */
	private boolean otherName;
	/**
	 * 厂商
	 */
	private String company;

	
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		if(userName!=null){
			try {
				return new String(userName.getBytes("ISO-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	
		return "未知";
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public boolean getOtherName() {
		return otherName;
	}

	public void setOtherName(boolean otherName) {
		this.otherName = otherName;
	}

	public String getValidTime() {
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public String getReceiveData() {
		return receiveData;
	}

	public void setReceiveData(String receiveData) {
		this.receiveData = receiveData;
	}

	public String getOutputData() {
		return outputData;
	}

	public void setOutputData(String outputData) {
		this.outputData = outputData;
	}

}
