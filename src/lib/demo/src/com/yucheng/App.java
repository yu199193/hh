package lib.demo.src.com.yucheng;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class App {

	public static void main(String[] args) throws UnsupportedEncodingException {
		App app = new App();
		HashSet<String> set = app.getPhone();
		for (String str : set) {
			app.message(str);
		}
	}

	public HashSet<String> getPhone() {
		InputStream stream = null;
		BufferedReader reader = null;
		HashSet<String> set = new HashSet<String>();
		try {
			stream = new FileInputStream(new File("D:\\phone.txt"));
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


	//这里的mobile就是读的file文本中的内容
	public void message(String mobile) throws UnsupportedEncodingException {
		String[] str = mobile.split(",");//将名字和电话按逗号分隔
		String phoneNumber = str[0];//车主电话号码
		String name = str[1];//车主名字
		System.out.println(name);
		Map<String, Object> paramMap = new HashMap<>(16);
		paramMap.put("mobile", phoneNumber);
		paramMap.put("account", "ypkj-001");
		paramMap.put("pswd", "Ypkj888888");
		paramMap.put("needstatus", true);
		paramMap.put("msg", "车主尊享，五一假期免费喝粥，0元享粥传酥肉粥+蒸饺套餐，请登录车主版小程序领取，详询4008851969");
		String retSms = "";
		try {
			retSms = URLConnectionUtil.doPost("http://sapi.253.com/msg/HttpBatchSendSM", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println(mobile+"-->"+retSms);
		}
	}

}