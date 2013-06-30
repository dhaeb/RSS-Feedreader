package de.eva.feed;

import java.util.Date;

public class FeedEntry {
	private Date publishedDate;
	private String title;
	private String content;
	private String link;
	public FeedEntry(Date publishedDate, String title, String content, String link) {
		this.publishedDate = publishedDate;
		this.title = title;
		this.content = content;
		this.link = link;
	}
	public Date getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
}
