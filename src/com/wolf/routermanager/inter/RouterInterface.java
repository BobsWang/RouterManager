package com.wolf.routermanager.inter;

/**
 * Created by wuwf on 2014/12/18.
 * 基础的接口，定义路由管理的某些功能支持与否的常量
 */
public interface RouterInterface {
    /**
     * 保存数据的key
      */
    String BASEDATA = "data";
    /**
     * 某些功能支持与否的key
     */
    String SUPPORT = "support";
    /**
     * 不支持该功能
     */
    int SUPPORT_NO = -1;
    /**
     * 操作成功
     */
    int SUPPORT_YES = 1;
    /**
     * 操作失败
     */
    int SUPPORT_FAIL = 2;
    /**
     * 解析出异常
     */
    int SUPPORT_EXCEPTION = 3;

    void putData(int flag);

    //各功能的标志
    /**
     * 获取黑名单里用户
     */
    String UTIL_GET_BLACK_USERS = "10";
    /**
     * 获取当前连接用户
     */
    String UTIL_GET_ACTIVE_USERS = "11";
    /**
     * 获取dhcp用户
     */
    String UTIL_GET_DHCP_USERS = "12";
    /**
     * 获取当前过滤模式（mac过滤是否开启）
     */
    String UTIL_GET_FILTERTYPE = "14";
    /**
     * 获取当前WiFi密码及加密方式
     */
    String UTIL_GET_ROUTERSAFE = "15";
    /**
     * 获取web远程管理是否开启
     */
    String UTIL_GET_WEBCHECK = "16";
    /**
     * 检测dns是否安全
     */
    String UTIL_GET_DNSCHECK = "17";
}
