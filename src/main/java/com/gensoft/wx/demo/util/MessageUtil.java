package com.gensoft.wx.demo.util;

import com.gensoft.wx.demo.constants.MessageType;
import com.gensoft.wx.demo.dto.*;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @ desc：
 * @ Author     ：chenhl01.
 * @ Date       ：Created in 10:41 2019/8/14
 */
public class MessageUtil {

	public static Map<String, String> xmlToMap(HttpServletRequest request) {

		Map<String, String> map = new HashMap<>();

		SAXReader reader = new SAXReader();

		InputStream ins = null;
		try {
			ins = request.getInputStream();

			Document doc = reader.read(ins);

			Element root = doc.getRootElement();
			List<Element> list = root.elements();

			for (Element e : list) {
				map.put(e.getName(), e.getText());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			try {
				ins.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public static String textMessageToXml(TextMessage text){

		XStream xStream = new XStream();
		xStream.alias("xml",text.getClass());
		String xml=xStream.toXML(text);
		return xml;
	}

	/**
	 * 图文消息转xml类型
	 */
	public static String newsMessageToXml(NewsMessage newsMessage){

		XStream xStream = new XStream();
		xStream.alias("xml",newsMessage.getClass());
		xStream.alias("item",new News().getClass());
		String xml=xStream.toXML(newsMessage);
		System.out.println("返回报文："+xml);
		return xml;
	}

	/**
	 * 图片消息转xml类型
	 */
	public static String imageMessageToXml(ImageMessage imageMessage){

		XStream xStream = new XStream();
		xStream.alias("xml",imageMessage.getClass());
		String xml=xStream.toXML(imageMessage);
		return xml;
	}

	public static String initNewsMessage(String toUserName,String fromUserName){
		String message = null;

		NewsMessage newsMessage = new NewsMessage();
		List<News> list = new ArrayList<>();

		News news = new News();
		news.setTitle("图文消息测试");
		news.setDescription("就是一条测试图文消息的消息，哈哈");
		news.setPicUrl("http://chlin.free.idcfengye.com/images/miao.jpg");
		news.setUrl("http://chlin.free.idcfengye.com/index.html");

		list.add(news);

		newsMessage.setFromUserName(toUserName);
		newsMessage.setToUserName(fromUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageType.MESSAGE_NEWS);
		newsMessage.setArticles(list);
		newsMessage.setArticleCount(list.size());

		message = newsMessageToXml(newsMessage);

		return message;
	}

	/**
	 *
	 * @return
	 */
	public static String menuText(){
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您，菜单如下:\n\n");
		sb.append("1、公众号介绍\n\n");
		sb.append("2、猜猜看\n\n");
		sb.append("?、菜单");
		return sb.toString();
	}

	public static String initMessageText(String toUserName,String fromUserName,String content){
		TextMessage textMessage = new TextMessage();
		textMessage.setFromUserName(toUserName);
		textMessage.setToUserName(fromUserName);
		textMessage.setMsgType(MessageType.MESSAGE_TEXT);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setContent(content);

		return textMessageToXml(textMessage);
	}

	public static String firstMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("公众号介绍\n\n");
		return sb.toString();
	}

	public static String secondMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("猜啥猜  没啥用");
		return sb.toString();
	}

	/**
	 * 组装回复图片消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initImageMessage(String toUserName,String fromUserName){
		String message = null;

		ImageContent imageContent = new ImageContent();
		imageContent.setMediaId("lAUnhfD8SxbA7TY2ZhXTAf7uCNLgf1mQ0YbgibbNr3HJuFVsKi4SlGH725n085zi");

		ImageMessage imageMessage = new ImageMessage();

		imageMessage.setImage(imageContent);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MessageType.MESSAGE_IMAGE);

		message = imageMessageToXml(imageMessage);
		System.out.println(message);
		return message;
	}

}
