package com.gensoft.wx.demo.dto;

/**
 * @ desc：消息基类
 * @ Author     ：chenhl01.
 * @ Date       ：Created in 14:15 2019/8/14
 */
public class BaseMessage {

	//	开发者微信号
	private String ToUserName;
	//发送方帐号（一个OpenID）
	private String FromUserName;
	//	消息创建时间 （整型）
	private Long CreateTime;
	//	消息类型，文本为text
	private String MsgType;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public Long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
}
