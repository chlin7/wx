package com.gensoft.wx.demo.dto.menu;

/**
 * @ desc：菜单基类
 * @ Author     ：chenhl01.
 * @ Date       ：Created in 9:26 2019/8/15
 */
public class Button {

	private String name;
	private String type;
	private Button[] sub_button;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
}
