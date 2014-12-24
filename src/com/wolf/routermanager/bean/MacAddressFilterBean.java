package com.wolf.routermanager.bean;

/**
 * mac地址过滤的集合
 * 
 * @author wuwf
 * 
 */
public class MacAddressFilterBean {
	/**
	 * mac地址
	 */
	private String macAddress;
	/**
	 * 过滤标记，是否生效，生效为1，失效为0
	 */
	private String filterFlag;

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getFilterFlag() {
		return filterFlag;
	}

	public void setFilterFlag(String filterFlag) {
		this.filterFlag = filterFlag;
	}

}
