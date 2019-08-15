package com.gensoft.wx.demo.dto;

/**
 * @ desc：
 * @ Author     ：chenhl01.
 * @ Date       ：Created in 10:50 2019/8/14
 */
public class TextMessage extends BaseMessage {
	//	文本消息内容
	private String Content;
	//	消息id，64位整型
	private String MsgId;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
}
