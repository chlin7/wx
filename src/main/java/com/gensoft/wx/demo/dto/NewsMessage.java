package com.gensoft.wx.demo.dto;

import java.util.List;

/**
 * @ desc：图文消息
 * @ Author     ：chenhl01.
 * @ Date       ：Created in 14:17 2019/8/14
 */
public class NewsMessage extends BaseMessage {
	private int ArticleCount;
	private List<News> Articles;

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<News> getArticles() {
		return Articles;
	}

	public void setArticles(List<News> articles) {
		Articles = articles;
	}
}
