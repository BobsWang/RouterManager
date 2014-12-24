package com.wolf.routermanager.inter;

import java.util.HashMap;

/**
 * 数据返回完成之后的回调接口
 * @author wolf
 *
 */
public interface ContextCallBack {
	/**
	 * 将请求回来的数据回调
	 * @param map
	 * 存放值的map
	 */
	void putData(HashMap<String, Object> map);
}
