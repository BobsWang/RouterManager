package com.wolf.routermanager.common.tool;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.wolf.routermanager.common.db.AssetsDatabaseManager;

public class PullParser {

	public PullParser(Context context) {
		// 初始化，只需要调用一次
		AssetsDatabaseManager.initManager(context);
	}

	/**
	 * 从数据库中找到mac地址对应的厂商
	 * @param macid
	 * @return
	 */
	private String findName(String macid) {
		// 00002B
		try {
			// 获取管理对象，因为数据库需要通过管理对象才能够获取
			AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
			// 通过管理对象获取数据库
			SQLiteDatabase db1 = mg.getDatabase("macAddress.db");
			// 对数据库进行操作
			Cursor cur = db1.rawQuery(
					"select company from MacCompany where mac='" + macid + "'",
					null);
			if (cur.moveToFirst()) {
				return cur.getString(0);
			} else {
				return "未知厂商";
			}
		} catch (Exception e) {
			return "未知厂商";
		}
	}

	public String findNameByMac(String macid) {
		String s = macid.substring(0, 8).toUpperCase().trim().replace("-", "")
				.replace(":", "").replace("\"", "");

		return findName(s);

	}

}
