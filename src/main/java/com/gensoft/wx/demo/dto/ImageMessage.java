package com.gensoft.wx.demo.dto;

/**
 * @ desc：图片消息
 * @ Author     ：chenhl01.
 * @ Date       ：Created in 16:25 2019/8/14
 */
public class ImageMessage extends BaseMessage {

	private ImageContent Image;

	public ImageContent getImage() {
		return Image;
	}

	public void setImage(ImageContent image) {
		Image = image;
	}
}
