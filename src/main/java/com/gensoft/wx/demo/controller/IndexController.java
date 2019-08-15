package com.gensoft.wx.demo.controller;

import com.gensoft.wx.demo.constants.MessageType;
import com.gensoft.wx.demo.dto.TextMessage;
import com.gensoft.wx.demo.util.CheckUtil;
import com.gensoft.wx.demo.util.MessageUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @ desc：
 * @ Author     ：chenhl01.
 * @ Date       ：Created in 9:38 2019/8/14
 */
@RestController
@RequestMapping("/hello")
public class IndexController {

	@GetMapping("/test")
	public String aaa() {
		return "hello world";
	}

	@GetMapping
	public String index(@RequestParam String signature,
						@RequestParam String timestamp,
						@RequestParam String nonce,
						@RequestParam String echostr) {

		System.out.println("========== current date is ===========:" + new Date());
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			System.out.println("====验证通过===");
			return echostr;
		}
		return "error";
	}

	@PostMapping
	public String test(HttpServletRequest request) {

		String message = "";
		try {

			Map<String, String> map = MessageUtil.xmlToMap(request);

			String MsgType = map.get("MsgType");
			String ToUserName = map.get("ToUserName");
			String FromUserName = map.get("FromUserName");
			String Content = map.get("Content");

			if (MessageType.MESSAGE_TEXT.equals(MsgType)) {

				if ("1".equals(Content)) {
					message = MessageUtil.initMessageText(ToUserName, FromUserName,MessageUtil.firstMenu() );

				} else if ("2".equals(Content)) {
					message = MessageUtil.initNewsMessage(ToUserName, FromUserName);

				} else if ("3".equals(Content)) {
					message = MessageUtil.initImageMessage(ToUserName, FromUserName);

				}else if ("?".equals(Content) || "？".equals(Content)) {
					message = MessageUtil.initMessageText(ToUserName, FromUserName, MessageUtil.menuText());

				} else {
					message = MessageUtil.initMessageText(ToUserName, FromUserName, "您发送的消息内容是:" + Content);
				}
			} else if (MessageType.MESSAGE_EVENT.equals(MsgType)) {
				String eventType = map.get("Event");
				if (MessageType.MESSAGE_SUBCRIBE.equals(eventType)) {
					System.out.println(FromUserName+"==添加关注=");
					message = MessageUtil.initMessageText(ToUserName, FromUserName, Content);
				}else if (MessageType.MESSAGE_UNSUBSCRIBE.equals(eventType)) {
					System.out.println(FromUserName+"==取消关注=");
				}else if (MessageType.MESSAGE_CLICK.equals(eventType)) {
					message = MessageUtil.initMessageText(ToUserName, FromUserName, MessageUtil.menuText());
				}else if (MessageType.MESSAGE_VIEW.equals(eventType)) {
					String url = map.get("EventKey");
					message = MessageUtil.initMessageText(ToUserName,FromUserName,url);
				}else if (MessageType.MESSAGE_SCANCODE_PUSH.equals(eventType)) {
					String url = map.get("EventKey");
					message = MessageUtil.initMessageText(ToUserName,FromUserName,url);
				}
			}else if (MessageType.MESSAGE_LOCATION.equals(MsgType)) {
				String label = map.get("Label");
				message = MessageUtil.initMessageText(ToUserName,FromUserName,label);
			}

		} catch (Exception e) {

		}

		System.out.println("返回报文："+message);
		return message;
	}
}
