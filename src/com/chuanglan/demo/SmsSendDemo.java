package com.chuanglan.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.chuanglan.sms.util.ChuangLanSmsUtil;
import com.chuanglan.sms.util.URLConnectionUtil;
/**
 * 
 * @author tianyh 
 * @Description:普通短信发送
 */
public class SmsSendDemo {

	public static final String charset = "utf-8";
	
	// 请登录zz.253.com 获取创蓝API账号(非登录账号,示例:N1234567)
	public static String account = "M8744499";
	
	// 请登录zz.253.com 获取创蓝API密码(非登录密码)
	public static String pswd = "7lakAbh46";

	public HashSet<String> getPhone() {
		InputStream stream = null;
		BufferedReader reader = null;
		HashSet<String> set = new HashSet<String>();
		try {
			stream = new FileInputStream(new File("D:\\test.txt"));
			reader = new BufferedReader(new InputStreamReader(stream));
           /* //1. 文本中只有电话号码
            String phoneNumber = null;//txt文件中的电话号码
            while((phoneNumber = reader.readLine()) != null) {
                set.add(phoneNumber);
            }*/
			//2. 文本中是姓名+电话号码
			String person = null; //txt文件中的每个车主
			while((person = reader.readLine()) != null) {
				set.add(person);
			}
			//set.add("18581897015");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					reader = null;
				}
			}

			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					stream = null;
				}
			}
		}
		return set;
	}
	public void message(String mobile) throws UnsupportedEncodingException {
		String[] str = mobile.split(",");//将名字和电话按逗号分隔
		String phoneNumber = str[0];//车主电话号码
//		String name = str[1];//车主名字
//		System.out.println(name);
		Map<String, Object> paramMap = new HashMap<>(16);
		paramMap.put("phone", phoneNumber);
		paramMap.put("account", account);
		paramMap.put("password", pswd);
		paramMap.put("report", true);
		paramMap.put("msg", "清明假期，送你117元来两根脆鸭肠火锅菜品券(18个菜随便选敞开吃），请登陆车主版小程序使用，退订回T");
		String retSms = "";
		try {
			JSONObject json =new JSONObject(paramMap);
			 retSms = ChuangLanSmsUtil.sendSmsByPost("http://smssh1.253.com/msg/send/json", json.toJSONString());
//			retSms = URLConnectionUtil.doPost("http://smssh1.253.com/msg/send/json", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println(mobile+"-->"+retSms);
		}
	}
	public static void main(String[] args) throws UnsupportedEncodingException {

		SmsSendDemo ssd = new SmsSendDemo();
		HashSet<String> set = ssd.getPhone();
		for (String str : set) {
			ssd.message(str);
		}
	}


}
