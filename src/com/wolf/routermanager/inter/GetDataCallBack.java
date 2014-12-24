package com.wolf.routermanager.inter;


import java.util.HashMap;

/**
 * @author wuwf
 * @version 创建时间：2014-12-1 下午4:56:45 
 * 类说明 
 */
public abstract class GetDataCallBack {
	private ContextCallBack mCallBack;
	private ConnInfoCallBack mConnInfoCallBack;
	private RouterInterface mRouterInterface;

	public GetDataCallBack() {

	}

	public GetDataCallBack(ContextCallBack callBack) {
		this.mCallBack = callBack;
	}

	public GetDataCallBack(ConnInfoCallBack callBack) {
		this.mConnInfoCallBack = callBack;
	}

	public GetDataCallBack(RouterInterface routerInterface) {
		this.mRouterInterface = routerInterface;
	}

	public void start() {

	}

	/**
	 * 成功返回并且返回字符串
	 * @param content
	 * 返回的字符串
	 */
	public void success(String content) {
		if (mConnInfoCallBack != null) {
			mConnInfoCallBack.putData(true);
		}
		if (mRouterInterface != null) {
			mRouterInterface.putData(RouterInterface.SUPPORT_YES);
		}
	}
	/**
	 * 成功返回并且返回对象
	 * @param content
	 * 返回的对象
	 */
	public <T> void successBean(T bean) {

	}
	/**
	 * 失败调用
	 */
	public void failure() {
		if (mCallBack != null) {
			mCallBack.putData(supportFailMap());
		}
		if(mConnInfoCallBack != null) {
			mConnInfoCallBack.putData(false);
		}
		if (mRouterInterface != null) {
			mRouterInterface.putData(RouterInterface.SUPPORT_FAIL);
		}
	}

	/**
	 * 网络请求失败走这里
	 *
	 * @return 回调的map
	 */
	private HashMap<String, Object> supportFailMap() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(RouterInterface.SUPPORT, RouterInterface.SUPPORT_FAIL);
		return map;
	}
}
