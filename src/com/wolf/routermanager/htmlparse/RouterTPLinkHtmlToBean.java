package com.wolf.routermanager.htmlparse;

import android.annotation.SuppressLint;
import com.wolf.routermanager.bean.BaseRouterBean;
import com.wolf.routermanager.bean.MacAddressFilterBean;
import com.wolf.routermanager.bean.RouterSafeAndPasswordBean;
import com.wolf.routermanager.bean.TpLinkRouterBean;
import com.wolf.routermanager.bean.WifiUserBean;
import com.wolf.routermanager.htmlparse.inter.HtmlParseInterface;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 将返回的html内容转换为javabean
 *
 * @author 狼骑兵
 */
@SuppressLint("DefaultLocale")
public class RouterTPLinkHtmlToBean extends HtmlParseInterface {

    /**
     * 将html文件解析成element对象
     */
    private static Elements parseHtmlToElements(String html) {
        // 开始解析html文件
        Document doc = Jsoup.parse(html);

        return doc.select("SCRIPT");
    }

    /**
     * 将所有连接的设备列表字符串解析为对象
     */
    @Override
    public BaseRouterBean getDhcpUser(String html) {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        List<WifiUserBean> wifiUsers = new ArrayList<WifiUserBean>();
        Elements elements = parseHtmlToElements(html);

        for (Element element : elements) {
            // 确定是正确的数据，从多个script中找到对的数据来解析
            if (element.toString().replace(" ", "")
                    .contains("varDHCPDynList=newArray")) {
                // 该element才是我们想要解析的script脚本
                wifiUsers = StringUtils.parseWifiUserBean(element
                        .toString());
                break;
            }
        }
        baseRouterBean.setDhcpUsers(wifiUsers);
        return baseRouterBean;
    }

    @Override
    public BaseRouterBean getAllActiveUser(String html) {
        TpLinkRouterBean baseRouterBean = new TpLinkRouterBean();
        List<WifiUserBean> wifiUsers = new ArrayList<WifiUserBean>();

        String count = "1";
        Elements elements = parseHtmlToElements(html);

        for (Element element : elements) {
            // 先读取总的主机数
            if (element.toString().replace(" ", "")
                    .contains("varwlanHostPara=newArray")) {
                count = StringUtils.getActiveUserCount(element.toString());
            }

            // 确定是正确的数据，从多个script中找到对的数据来解析
            if (element.toString().replace(" ", "")
                    .contains("varhostList=newArray")) {
                // 该element才是我们想要解析的script脚本
                wifiUsers = StringUtils.parseWifiActiveUserBean(element
                        .toString());
                break;
            }
        }
        baseRouterBean.setCount(count);
        baseRouterBean.setActiveUsers(wifiUsers);

        return baseRouterBean;
    }

    @Override
    public BaseRouterBean getAllMacAddressFilter(String html) {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        List<MacAddressFilterBean> wifiUsers = new ArrayList<MacAddressFilterBean>();
        Elements elements = parseHtmlToElements(html);
        for (Element element : elements) {
            // 确定是正确的数据，从多个script中找到对的数据来解析
            if (element.toString().replace(" ", "")
                    .contains("varwlanFilterList=newArray")) {
                // 该element才是我们想要解析的script脚本
                wifiUsers = StringUtils.parseMacAddress(element.toString());
                break;
            }
        }
        baseRouterBean.setBlackUsers(wifiUsers);
        return baseRouterBean;
    }

    /**
     * 检查过滤规则
     */
    @Override
    public BaseRouterBean getFilterRule(String html) {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        String result = "00";
        Elements elements = parseHtmlToElements(html);
        for (Element element : elements) {
            // 确定是正确的数据，从多个script中找到对的数据来解析
            if (element.toString().replace(" ", "")
                    .contains("varwlanFilterPara=newArray")) {
                // 该element才是我们想要解析的script脚本
                result = StringUtils.checkMacFilter(element.toString());
                break;
            }
        }

        if ("10".equals(result)) {
            baseRouterBean.setMacFilterOpen(true);
        }
        return baseRouterBean;
    }

    /**
     * 获取远程web安全
     */
    @Override
    public BaseRouterBean getWebSafeResult(String h) {
        TpLinkRouterBean baseRouterBean = new TpLinkRouterBean();
        String html = h.replace(" ", "");
        int begin = html.indexOf("managementPara=newArray");
        int end = html.indexOf("</script>");
        String newHtml = html.substring(begin, end);
        String wantString = newHtml.replace("managementPara=newArray(", "")
                .replace("\n", "").replace(");", "").replace("\"", "");
        String[] array = wantString.split(",");
        //开启了，是风险行为
        if ("1".equals(array[1])) {
             baseRouterBean.setWebSafe(false);
        } else {
            baseRouterBean.setWebSafe(true);
        }
        return baseRouterBean;
    }


    @Override
    public BaseRouterBean getRouterSafeAndPassword(String html) {
        BaseRouterBean baseRouterBean = new BaseRouterBean();
        RouterSafeAndPasswordBean bean = new RouterSafeAndPasswordBean();

        String newHmtl = html.replace(" ", "").replace("\n", "");
        int begin = newHmtl.indexOf("wlanPara=newArray");
        int end = newHmtl.indexOf(");");
        String newString = newHmtl.substring(begin, end);
        String[] array = newString.replace("wlanPara=newArray(", "")
                .replace("\"", "").split(",");
        String type = array[2];
        String password = array[9];
        bean.setPassword(password);
        bean.setType(Integer.valueOf(type));

        baseRouterBean.setRouterSafePassword(bean);
        return baseRouterBean;
    }

    /**
     * 获取dns是否安全。true为安全
     */
    @Override
    public BaseRouterBean getDnsSafe(String html) {
        TpLinkRouterBean baseRouterBean = new TpLinkRouterBean();
        String newHtml = html.replace(" ", "").replace("\n", "");
        int begin = newHtml.indexOf("1500");
        String newString = newHtml.substring(begin, begin + 6);
        String result = newString.replace("1500", "").replace(",", "");
        if ("1".equals(result)) {
            baseRouterBean.setDnsSafe(false);
        } else {
            baseRouterBean.setDnsSafe(true);
        }

        return baseRouterBean;

    }

}
