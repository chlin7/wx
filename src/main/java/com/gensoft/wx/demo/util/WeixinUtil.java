package com.gensoft.wx.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gensoft.wx.demo.dto.AccessToken;
import com.gensoft.wx.demo.dto.menu.Button;
import com.gensoft.wx.demo.dto.menu.ClickButton;
import com.gensoft.wx.demo.dto.menu.Menu;
import com.gensoft.wx.demo.dto.menu.ViewButton;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ desc：
 * @ Author     ：chenhl01.
 * @ Date       ：Created in 15:02 2019/8/14
 */
public class WeixinUtil {
	private static final String APPID = "wx704de245944b5f42";
	private static final String APPSECRET = "f5c954118024ea25a7469bfd4776099a";

	/**
	 * 获取access_token请求路径
	 */
	private static final String ACCESS_TOKEN_URL = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",APPID,APPSECRET);

	/**
	 * 上传图片获取mediaId请求路径
	*/
	private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

	/**
	 * 创建菜单url
	 */
	private static final String CREAT_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	public static JSONObject getRequest(String url){

		JSONObject jsonObject = null;
		//创建httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		//创建GET对象
		HttpGet httpget = new HttpGet(url);//xxx是服务器API
		try {
			//执行请求
			CloseableHttpResponse response = httpclient.execute(httpget);
			if(response.getStatusLine().getStatusCode()==200) {
				HttpEntity entity = response.getEntity();
				//所需内容都在entity里面，这里可以对数据操作
				String detail = EntityUtils.toString(entity,"UTF-8");
				System.out.println(detail);
				jsonObject = JSON.parseObject(detail);
			}
			response.close();
			httpclient.close();
		}catch (Exception e){

		}finally {

		}
		return jsonObject;
	}
	public static JSONObject postRequest(String url,String outStr){
		JSONObject jsonObject = null;
		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建http POST请求
		HttpPost httpPost = new HttpPost(url);
		//伪装浏览器请求
		CloseableHttpResponse response = null;
		try {
			httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
			// 执行请求
			response = httpclient.execute(httpPost);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				String content = EntityUtils.toString(response.getEntity(), "UTF-8");
				//内容写入文件
				System.out.println("内容长度："+content.length());
				jsonObject = JSON.parseObject(content);
			}
			if (response != null) {
				response.close();
			}
			httpclient.close();
		} catch (Exception e){

		}finally {

		}
		return jsonObject;
	}

	public static String upload(String filePath,String accessToken,String type)throws Exception{
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()){
			throw new Exception("文件不存在");
		}
		String url = UPLOAD_URL.replace("ACCESS_TOKEN",accessToken).replace("TYPE",type);
		URL urlObj = new URL(url);
		HttpURLConnection connection = (HttpURLConnection)urlObj.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);

		connection.setRequestProperty("Connection","Keep-Alive");
		connection.setRequestProperty("Charset","UTF-8");

		String BOUNDRY = "-------"+System.currentTimeMillis();
		connection.setRequestProperty("Content-Type","multipart/form-data;boundary="+BOUNDRY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDRY);
		sb.append("\r\b");
		sb.append("Content-Disposition;form-data;name=\"file\";filename=\""+file.getName()+"\"\r\n");
		sb.append("Content-Type;application/octet-stream\r\n\r\n");
		byte[] head = sb.toString().getBytes("UTF-8");

		OutputStream out = new DataOutputStream(connection.getOutputStream());
		out.write(head);

		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1){
			out.write(bufferOut,0,bytes);
		}
		in.close();

		byte[] foot = ("\r\n--"+BOUNDRY+"--\r\n").getBytes("utf-8");
		out.write(foot);
		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null){
				buffer.append(line);
			}
			if (result == null){
				result = buffer.toString();
			}
		}catch (Exception e){

		}finally {
			if (reader != null){
				reader.close();
			}
		}

		JSONObject jsonObject = JSONObject.parseObject(result);
		System.out.println("======="+jsonObject);
		String mediaId = jsonObject.getString("media_id");
		return mediaId;


	}

	/**
	 * 获取微信access_token
	 * @return
	 */
	public static AccessToken getAccessToken(){
		AccessToken token = new AccessToken();
		token.setAccessToken("24_SzPZL-WtYMqgAzJhBkaEAzGVlCpxvZ7EBIYiHvfNXsrg4xf6CJjfGCpAxp7UL2bfd40hRBsXTfGMeJPbUGVEISi_9MXjGgtmjiJ7Jf6nu7OQC5DtjqaHOjsaFiu8OIRalgcxF7jxz94d_huWCAPjAFASQF");
		token.setExpiresIn(7000L);

//		JSONObject jsonObject = getRequest(ACCESS_TOKEN_URL);
//		if (jsonObject != null){
//			token.setAccessToken(jsonObject.getString("access_token"));
//			token.setExpiresIn(jsonObject.getLongValue("expires_in"));
//		}

		return token;
	}

	/**
	 * 菜单组装
	 * @return
	 */
	public static Menu initMenu(){
		Menu menu = new Menu();

		ClickButton clickButton = new ClickButton();
		clickButton.setName("click菜单");
		clickButton.setType("click");
		clickButton.setKey("11");

		ClickButton button1 = new ClickButton();
		button1.setName("扫码菜单");
		button1.setType("scancode_push");
		button1.setKey("21");

		ClickButton button2 = new ClickButton();
		button2.setName("位置菜单");
		button2.setType("location_select");
		button2.setKey("31");

		ViewButton button3 = new ViewButton();
		button3.setName("页面测试");
		button3.setType("view");
		button3.setUrl("http://chlin.free.idcfengye.com/test.html");

		Button button = new Button();
		button.setName("菜单");
		button.setSub_button(new Button[]{button1,button2,button3});

		ViewButton viewButton = new ViewButton();
		viewButton.setName("view菜单");
		viewButton.setType("view");
		viewButton.setUrl("http://chlin.free.idcfengye.com/index.html");


		menu.setButton(new Button[]{button,clickButton,viewButton});

		return menu;
	}

	public static int createMenu(String token,String menu){
		int result = -1;
		String url = CREAT_MENU_URL.replace("ACCESS_TOKEN",token);
		JSONObject jsonObject = postRequest(url,menu);
		if (jsonObject != null){
			result = jsonObject.getIntValue("errcode");
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
//		AccessToken token = getAccessToken();
//		System.out.println("accessToken is:"+token.getAccessToken());
//		System.out.println("expireIn is:"+token.getExpiresIn());

		String token = "24_SzPZL-WtYMqgAzJhBkaEAzGVlCpxvZ7EBIYiHvfNXsrg4xf6CJjfGCpAxp7UL2bfd40hRBsXTfGMeJPbUGVEISi_9MXjGgtmjiJ7Jf6nu7OQC5DtjqaHOjsaFiu8OIRalgcxF7jxz94d_huWCAPjAFASQF";
//
//		String path = "E:/projects/tiejian/demo/src/main/resources/static/images/miao.jpg";
//		String mediaId = upload(path,token,"image");
//
//		System.out.println("==media_id="+mediaId);

		String menu  = JSON.toJSONString(initMenu());

		int result = createMenu(token,menu);
		if (result == 0){
			System.out.println("===创建菜单成功===");
		}else {
			System.out.println("===创建菜单失败==="+result);
		}
	}

}
