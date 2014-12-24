package com.wolf.routermanager.common.tool;

public class CutMacAddress {

	/**
	 * 有的厂商实在太长
	 * @param macAddress
	 * @return
	 */
	public static String cut(String macAddress) {
		if(macAddress.trim().split(" ").length >= 2) {
			return macAddress.trim().split(" ")[0] + " " + macAddress.trim().split(" ")[1];
		}
		return macAddress;
	}
}
