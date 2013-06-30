package de.eva.feed;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class FeedEntry {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Temporal(TemporalType.DATE) 
	private Date publishedDate;
	private String title;
	private String content;
	private String link;

	public FeedEntry() {}
	
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "FeedEntry [id=" + id + ", publishedDate=" + publishedDate + ", title=" + title
				+ ", content=" + content + ", link=" + link + "]";
	}
	
	
}
