package com.gensoft.wx.demo.dto;

/**
 * @ desc：access_token 实体
 * @ Author     ：chenhl01.
 * @ Date       ：Created in 15:21 2019/8/14
 */
public class AccessToken {

	private String accessToken;
	private Long expiresIn;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}
}
