//package com.wolf.routermanager.http;
//
//import android.content.Context;
//import android.os.Looper;
//import android.util.Log;
//import com.loopj.android.http.RequestParams;
//import com.wolf.routermanager.bean.MacAddressFilterBean;
//import com.wolf.routermanager.bean.RouterManagerUserBean;
//import com.wolf.routermanager.bean.WifiUserBean;
//import com.wolf.routermanager.http.inter.RouterUtilInterface;
//import com.wolf.routermanager.inter.ConnInfoCallBack;
//import com.wolf.routermanager.inter.ContextCallBack;
//import com.wolf.routermanager.urlconstant.inter.RouterConstantInterface;
//import org.apache.http.client.params.ClientPNames;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerConfigurationException;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//import java.io.BufferedReader;
//import java.io.ByteArrayOutputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//   /**
//public class RouterDLinkUtil_615 extends RouterUtilInterface {
//
//	private List<WifiUserBean> wifiUsers;
//
//	public RouterDLinkUtil_615(Context context,
//			RouterConstantInterface constant, String username, String password) {
//		super(context, constant, username, password);
//	}
//
//	// http://192.168.0.1/adv_mac_filter.php
//
//	public String createXML() {
//		String xmlStr = null;
//		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//		try {
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			Document document = builder.newDocument();
//			document.setXmlVersion("1.0");
//
//			Element root = document.createElement("postxml");
//			document.appendChild(root);
//
//			Element telephone = document.createElement("TelePhone");
//
//			Element nokia = document.createElement("type");
//			nokia.setAttribute("name", "nokia");
//
//			Element priceNokia = document.createElement("price");
//			priceNokia.setTextContent("599");
//			nokia.appendChild(priceNokia);
//
//			Element operatorNokia = document.createElement("operator");
//			operatorNokia.setTextContent("CMCC");
//			nokia.appendChild(operatorNokia);
//
//			telephone.appendChild(nokia);
//
//			Element xiaomi = document.createElement("type");
//			xiaomi.setAttribute("name", "xiaomi");
//
//			Element priceXiaoMi = document.createElement("price");
//			priceXiaoMi.setTextContent("699");
//			xiaomi.appendChild(priceXiaoMi);
//
//			Element operatorXiaoMi = document.createElement("operator");
//			operatorXiaoMi.setTextContent("ChinaNet");
//			xiaomi.appendChild(operatorXiaoMi);
//
//			telephone.appendChild(xiaomi);
//
//			root.appendChild(telephone);
//
//			TransformerFactory transFactory = TransformerFactory.newInstance();
//			Transformer transFormer = transFactory.newTransformer();
//			DOMSource domSource = new DOMSource(document);
//
//			// export string
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			transFormer.transform(domSource, new StreamResult(bos));
//			xmlStr = bos.toString();
//			Log.e("love", "msg=" + xmlStr);
//			// //-------
//			// //save as file
//			// File file = new File("TelePhone.xml");
//			// if(!file.exists()){
//			// file.createNewFile();
//			// }
//			// FileOutputStream out = new FileOutputStream(file);
//			// StreamResult xmlResult = new StreamResult(out);
//			// transFormer.transform(domSource, xmlResult);
//			// --------
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (TransformerConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (TransformerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		// catch (IOException e) {
//		// // TODO Auto-generated catch block
//		// e.printStackTrace();
//		// }
//
//		return xmlStr;
//	}
//
//	// public void getdhcp(final ConnInfoCallBack call) {
//	// RequestParams params = new RequestParams();
//	// params.put("SERVICES",
//	// "DEVICE.HOSTNAME,INET.LAN-1,DHCPS4.LAN-1,RUNTIME.INF.LAN-1,WAN,RUNTIME.TIME");
//	// httpPost("http://192.168.0.1/getcfg.php",
//	// "http://192.168.0.1/bsc_lan.php",
//	// params, new SuccessCallBack() {
//	//
//	// @Override
//	// public void doStart() {
//	// }
//	//
//	// @Override
//	// public void doFailure() {
//	// call.putData(false);
//	// }
//	//
//	// @Override
//	// public void callBack(String content) {
//	// Log.e("love", "msg="+content);
//	// if (content != null && content.contains("SUCCESS")) {
//	// call.putData(true);
//	// } else {
//	// call.putData(false);
//	// }
//	// }
//	// });
//	// }
//	// 00:02:54.797 0.773 2267 107 POST 200 text/xml
//	// http://192.168.0.1/hedwig.cgi
//	public void getBlack(final ConnInfoCallBack call) {
//		login(new ConnInfoCallBack() {
//
//			@Override
//			public void putData(boolean flag) {
//				if (flag) {
//					deleteOneUserFromMacFilter(null);
////					deleteOneUserFromMacFilter(null);
//					RequestParams params = new RequestParams();
//					params.put("SERVICES", "MACCTRL,WIFI.WLAN-1");
////					httpPost("http://192.168.0.1/getcfg.php",z
////							"http://192.168.0.1/adv_mac_filter.php", params,
////							new SuccessCallBack() {
////
////								@Override
////								public void doStart() {
////								}
////
////								@Override
////								public void doFailure() {
////									call.putData(false);
////								}
////
////								@Override
////								public void callBack(String content) {
////
////									if (content != null
////											&& content.contains("SUCCESS")) {
////										call.putData(true);
////									} else {
////										call.putData(false);
////									}
////								}
////							});
//				}
//			}
//		});
//
//	}
//
//	private void hahahhaha(){
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//				// TODO Auto-generated method stub
//				RequestParams params = new RequestParams();
//				params.put("ACTIONS", "SETCFG,SAVE,ACTIVATE");
//				httpPost("http://192.168.0.1/pigwidgeon.cgi",
//						"http://192.168.0.1/adv_mac_filter.php", params,
//						new SuccessCallBack() {
//
//							@Override
//							public void doStart() {
//								Log.e("love", "sst=");
//							}
//
//							@Override
//							public void doFailure() {
//								Log.e("love", "ss=");
//							}
//
//							@Override
//							public void callBack(String content) {
//								Log.e("love", "ha="+content);
//								RequestParams params = new RequestParams();
//								params.put("SERVICES",	"MACCTRL,WIFI.WLAN-1");
//								httpPost("http://192.168.0.1/getcfg.php",
//										"http://192.168.0.1/adv_mac_filter.php", params,
//										new SuccessCallBack() {
//
//											@Override
//											public void doStart() {
//												Log.e("love", "sst=");
//											}
//
//											@Override
//											public void doFailure() {
//												Log.e("love", "ss=");
//											}
//
//											@Override
//											public void callBack(String content) {
//												Log.e("love", "ssssss=");
//											}
//								});
////								if (content != null
////										&& content.contains("SUCCESS")) {
////									call.putData(true);
////								} else {
////									call.putData(false);
////								}
//							}
//						});
//			}
//		}).start();
//
//	}
//
//	/**
//	 */
//	private void deleteOneUserFromMacFilter(final String macAddress) {
//
//		final String cc= "<postxml><module><service>MACCTRL</service><acl><macctrl><seqno>1</seqno><max>24</max><count>1</count><policy>ACCEPT</policy><entry><uid>MACF-1</uid><enable>0</enable><mac>AA:AA:AA:AA:AA:DF</mac><description>DIR-615</description></entry></macctrl></acl></module><module><service>WIFI.WLAN-1</service><wifi><seqno>2</seqno><max>2</max><count>1</count><entry><uid>WIFI-1</uid><opmode>AP</opmode><ssid>SA-PC_Network</ssid><bssid>00:11:22:33:44:55:99</bssid><ssidhidden>0</ssidhidden><authtype>WPA2PSK</authtype><encrtype>AES</encrtype><wps><enable>0</enable><configured>1</configured><pin/></wps><acl><policy>DISABLED</policy><seqno>1</seqno><max>8</max><count>0</count></acl><nwkey><psk><passphrase>1</passphrase><key>12345678</key></psk></nwkey></entry></wifi><phyinf><uid>WLAN-1</uid><active>1</active><type>wifi</type><wifi>WIFI-1</wifi><schedule/><media><freq>2.4</freq><wlmode>bgn</wlmode><beacon>100</beacon><fragthresh>2346</fragthresh><rtsthresh>2346</rtsthresh><ctsmode/><channel>0</channel><txrate>auto</txrate><txpower>100</txpower><preamble>long</preamble><dtim>1</dtim><dot11n><bandwidth>20+40</bandwidth><guardinterval>400</guardinterval><coexistence><enable>1</enable></coexistence><mcs><auto>1</auto><index/></mcs></dot11n><wmm><enable>1</enable></wmm></media><brinf/></phyinf></module></postxml>";
//		final String con = "<postxml><module><service>MACCTRL</service><acl><macctrl><seqno>1</seqno><max>24</max><count>2</count><policy>ACCEPT</policy><entry><uid>MACF-1</uid><enable>0</enable><mac>AA:AA:AA:AA:AA:AA</mac><description>DIR-615</description></entry><entry><uid>MACF-2</uid><enable>0</enable><mac>AA:BB:BB:BB:BB:BB</mac><description>DIR-615</description></entry></macctrl></acl></module><module><service>WIFI.WLAN-1</service><wifi><seqno>2</seqno><max>2</max><count>1</count><entry><uid>WIFI-1</uid><opmode>AP</opmode><ssid>SA-PC_Network</ssid><bssid>00:11:22:33:44:55:99</bssid><ssidhidden>0</ssidhidden><authtype>WPA2PSK</authtype><encrtype>AES</encrtype><wps><enable>0</enable><configured>1</configured><pin/></wps><acl><policy>DISABLED</policy><seqno>1</seqno><max>8</max><count>0</count></acl><nwkey><psk><passphrase>1</passphrase><key>12345678</key></psk></nwkey></entry></wifi><phyinf><uid>WLAN-1</uid><active>1</active><type>wifi</type><wifi>WIFI-1</wifi><schedule/><media><freq>2.4</freq><wlmode>bgn</wlmode><beacon>100</beacon><fragthresh>2346</fragthresh><rtsthresh>2346</rtsthresh><ctsmode/><channel>0</channel><txrate>auto</txrate><txpower>100</txpower><preamble>long</preamble><dtim>1</dtim><dot11n><bandwidth>20+40</bandwidth><guardinterval>400</guardinterval><coexistence><enable>1</enable></coexistence><mcs><auto>1</auto><index/></mcs></dot11n><wmm><enable>1</enable></wmm></media><brinf/></phyinf></module></postxml>";
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				Looper.prepare();
//				try {
//					URL url = new URL("http://192.168.0.1/hedwig.cgi");
//					HttpURLConnection connection = (HttpURLConnection) url
//							.openConnection();
//					connection.setRequestProperty("Referer",
//							"http://192.168.0.1/adv_mac_filter.php");
//					connection.setRequestProperty("Cookie",
//							"uid=1gdtj8rw1d");
//					connection.setRequestProperty("User-Agent",
//							"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:32.0) Gecko/20100101 Firefox/32.0");
//					connection.setRequestProperty("Content-Type",
//							"text/xml; charset=UTF-8");
//					connection.setRequestProperty("Connection-Type",
//							"keep-alive");
//					connection.setRequestProperty("Pragma",
//							"no-cache");
//					connection.setRequestProperty("Cache-Control",
//							"no-cache");
//					connection.setDoOutput(true);
//					connection.setRequestMethod("POST");
//
//					OutputStreamWriter wr = new OutputStreamWriter(connection
//							.getOutputStream());
//					// this is were we're adding post data to the request
//					wr.write(cc);
//					wr.flush();
//
//					BufferedReader in = new BufferedReader(
//							new InputStreamReader(connection.getInputStream()));
//					String dataString = null;
//					while ((dataString = in.readLine()) != null) {
//
//						Log.e("love", "data=" + dataString);
//					}
//					hahahhaha();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}).start();
//
//	}
//
//	public void addBlack(final ConnInfoCallBack call, final String con) {
//		login(new ConnInfoCallBack() {
//
//			@Override
//			public void putData(boolean flag) {
//				if (flag) {
//					RequestParams params = new RequestParams();
//					params.put("", con);
//					httpPost("http://192.168.0.1/hedwig.cgi",
//							"http://192.168.0.1/adv_mac_filter.php", params,
//							new SuccessCallBack() {
//
//								@Override
//								public void doStart() {
//								}
//
//								@Override
//								public void doFailure() {
//									Log.e("love", "bbbbb=");
//									call.putData(false);
//								}
//
//								@Override
//								public void callBack(String content) {
//									Log.e("love", "aaaa=" + content);
//									if (content != null
//											&& content.contains("SUCCESS")) {
//										call.putData(true);
//									} else {
//										call.putData(false);
//									}
//								}
//							});
//				}
//			}
//		});
//
//	}
//
//	public void addmac(final ConnInfoCallBack call) {
//		RequestParams params = new RequestParams();
//		params.put("", "");
//		httpPost(constant.getBaseUri() + "/session.cgi", constant.getBaseUri(),
//				params, new SuccessCallBack() {
//
//					@Override
//					public void doStart() {
//					}
//
//					@Override
//					public void doFailure() {
//						call.putData(false);
//					}
//
//					@Override
//					public void callBack(String content) {
//						if (content != null && content.contains("SUCCESS")) {
//							// getdhcp(null);
//							// call.putData(true);
//						} else {
//							call.putData(false);
//						}
//					}
//				});
//	}
//
//	public void login(final ConnInfoCallBack call) {
//		RequestParams params = new RequestParams();
//		params.put("REPORT_METHOD", "xml");
//		params.put("ACTION", "login_plaintext");
//		params.put("USER", "admin");
//		params.put("PASSWD", mPassword);
//		params.put("CAPTCHA", "");
//		httpPost("http://192.168.0.1/session.cgi", "http://192.168.0.1",
//				// httpPost(constant.getBaseUri() + "/session.cgi",
//				// constant.getBaseUri(),
//				params, new SuccessCallBack() {
//
//					@Override
//					public void doStart() {
//					}
//
//					@Override
//					public void doFailure() {
//						call.putData(false);
//					}
//
//					@Override
//					public void callBack(String content) {
//						if (content != null && content.contains("SUCCESS")) {
//							// getdhcp(null);
//							call.putData(true);
//						} else {
//							call.putData(false);
//						}
//					}
//				});
//	}
//
//	@Override
//	public void getAllUserMacInFilter(ContextCallBack c) {
//		// dlink不支持黑名单功能
//		List<MacAddressFilterBean> wifiUsers = new ArrayList<MacAddressFilterBean>();
//		c.putData(wifiUsers);
//	}
//
//	@Override
//	public void getAllUser(final ContextCallBack c) {
//		if (wifiUsers != null) {
//			c.putData(wifiUsers);
//		} else {
//			getAllActiveUser(new ContextCallBack() {
//
//				@Override
//				public void putData(List<?> objects) {
//					c.putData(objects);
//				}
//			});
//		}
//	}
//
//	@Override
//	public void getAllActiveUser(final ContextCallBack c) {
//		login(new ConnInfoCallBack() {
//
//			@Override
//			public void putData(boolean flag) {
//				if (flag) {
//					httpGet(constant.getAllActiveUsers(null),
//							constant.getAllActiveUsersReferer(),
//							new SuccessCallBack() {
//
//								@SuppressWarnings({ "unchecked" })
//								@Override
//								public void callBack(final String content) {
//									HashMap<String, Object> users = constant
//											.getHtmlParser().getAllActiveUser(
//													mContext, content);
//									wifiUsers = (List<WifiUserBean>) users
//											.get("list");
//									c.putData(wifiUsers);
//
//									String macListName = "";
//									for (int i = 0; i < wifiUsers.size(); i++) {
//										if (i != wifiUsers.size() - 1) {
//											macListName = macListName
//													+ wifiUsers.get(i)
//															.getMacAddress()
//													+ ",";
//										} else {
//											macListName = macListName
//													+ wifiUsers.get(i)
//															.getMacAddress();
//										}
//									}
//									RouterInforCollect.sendCheckMachineMsg(
//											mContext, macListName);
//								}
//
//								@Override
//								public void doFailure() {
//									c.putData(null);
//								}
//
//								@Override
//								public void doStart() {
//								}
//							});
//				} else {
//					c.putData(null);
//				}
//
//			}
//		});
//
//	}
//
//	@Override
//	public void stopUserByMacAddress(String macAddress, UtilSupportListener c) {
//		c.putData(UtilSupportListener.NOSUPPORT);
//	}
//
//	@Override
//	public void modifyManagerPassword(RouterManagerUserBean user,
//			final UtilSupportListener c) {
//		RequestParams params = new RequestParams();
//		params.put(
//				":InternetGatewayDevice.X_TWSZ-COM_Authentication.UserList.1.Password",
//				user.getOldPassword() + "#TW#TW#" + user.getNewPassword());
//		params.put("obj-action", "set");
//		params.put("var:nodeIndex", 1 + "");
//		params.put("var:page", "accountpsd");
//		params.put("var:errorpage", "accountpsd");
//		params.put("getpage", "html/index.html");
//		params.put("errorpage", "html/index.html");
//		params.put("var:menu", "tools");
//		params.put("var:CacheLastData", "SU5QVVRfU2Vzc2lvblRpbWVPdXQ9MzA=");
//		httpPost(constant.modifyLoginPassword(user),
//				constant.modifyLoginPasswordReferer(), params,
//				new SuccessCallBack() {
//
//					@Override
//					public void doStart() {
//						c.putData(UtilSupportListener.SUCCESS);
//					}
//
//					@Override
//					public void doFailure() {
//					}
//
//					@Override
//					public void callBack(String content) {
//					}
//				});
//	}
//
//	@Override
//	public void modifyWifiSSid(final String ssid, final UtilSupportListener c) {
//		getWifiSafeInfo(new ContextCallBack() {
//
//			@Override
//			public void putData(List<?> objects) {
//
//			}
//		});
//
//	}
//
//	@Override
//	public void modifyWifiPassword(final String password,
//			final UtilSupportListener c) {
//		RequestParams params = getCommonParams();
//		params.put(
//				":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.SSID",
//				WifiUtil.getCurrentWifiName(mContext));
//		params.put(
//				":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.PreSharedKey.1.KeyPassphrase",
//				password);
//		// 无密码时
//		params.put(
//				":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.BeaconType",
//				"WPAand11i");
//
//		httpPost(constant.modifyWifiPassword(password),
//				constant.modifyWifiPasswordReferer(), params,
//				new SuccessCallBack() {
//
//					@Override
//					public void doStart() {
//
//					}
//
//					@Override
//					public void doFailure() {
//						c.putData(UtilSupportListener.SUCCESS);
//					}
//
//					@Override
//					public void callBack(String content) {
//						c.putData(UtilSupportListener.SUCCESS);
//					}
//				});
//
//	}
//
//	private RequestParams getCommonParams() {
//		RequestParams params = new RequestParams();
//		params.put(
//				":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.WPS.Enable",
//				"1");
//		params.put(
//				":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.WPAEncryptionModes",
//				"TKIPandAESEncryption");
//		params.put(
//				":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.WPAAuthenticationMode",
//				"PSKAuthentication");
//		params.put("var:menu", "wireless");
//		params.put("obj-action", "set");
//		params.put("var:page", "wireless_basic");
//		params.put("var:errorpage", "wireless_basic");
//		params.put("getpage", "html/index.html");
//		params.put("errorpage", "html/index.html");
//		params.put(
//				":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.IEEE11iEncryptionModes",
//				"TKIPandAESEncryption");
//		params.put(
//				":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.IEEE11iAuthenticationMode",
//				"PSKAuthentication");
//		params.put(
//				":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.SSIDAdvertisementEnabled",
//				"1");
//		params.put(
//				":InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.X_TWSZ-COM_APIsolate",
//				"0");
//		params.put(":InternetGatewayDevice.X_TWSZ-COM_Radio.1.Channel", "0");
//		params.put(
//				":InternetGatewayDevice.X_TWSZ-COM_Radio.1.AutoChannelEnable",
//				"1");
//		params.put(
//				":InternetGatewayDevice.X_TWSZ-COM_Radio.1.RegulatoryDomain",
//				"CN");
//		params.put(":InternetGatewayDevice.X_TWSZ-COM_Radio.1.Standard", "bgn");
//		params.put(
//				":InternetGatewayDevice.X_TWSZ-COM_Radio.1.OperatingChannelBandwidth",
//				"40");
//		params.put(":InternetGatewayDevice.X_TWSZ-COM_Radio.1.MaxBitRate",
//				"Auto");
//		return params;
//	}
//
//	/**
//	 * 查看过滤规则和是否已开启过滤
//	 */
//	@Override
//	public void checkFilterAndRule(final CheckRuleCallBack c) {
//		// 检查规则,直接算通过，不支持该功能
//		c.check(true, true);
//	}
//
//	@Override
//	public void getDMZState(ConnInfoCallBack c) {
//		c.putData(false);
//	}
//
//	@Override
//	public void getDNSSafe(CheckDNSSafeCallBack call) {
//		call.putSafeInfo(true);
//	}
//
//	@Override
//	public void getWeb(ConnInfoCallBack c) {
//		c.putData(false);
//	}
//
//	@Override
//	public void reboot(ConnInfoCallBack c) {
//		c.putData(false);
//	}
//
//	@Override
//	public void addOtherRefer() {
//		client.getHttpClient().getParams()
//				.setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
//		client.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//		client.addHeader(
//				"User-Agent",
//				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 1.7; .NET CLR 1.1.4322; CIBA; .NET CLR 2.0.50727)");
//		client.addHeader(
//				"Cookie",
//				"sessionid=32c7079a; auth=ok; expires=Sun, 15-May-2112 01:45:46 GMT; langmanulset=yes; Lan_IPAddress=192.168.0.1; language=zh_cn; sys_UserName=admin; expires=Mon, 31-Jan-2112 16:00:00 GMT");
//	}
//
//	@Override
//	public void modifyWiFiChannel(int channel, UtilSupportListener c) {
//		c.putData(UtilSupportListener.NOSUPPORT);
//
//	}
//
//	@Override
//	public void getRouterDeviceName(RouterNameCallBack c) {
//		c.putName("DLink Dir615");
//	}            */
//}
