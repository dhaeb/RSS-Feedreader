package de.eva.feed;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	@Column(length=6000, name="content")
	private String content;
	private String link;
	
	@ManyToOne
	@JoinColumn(name="link_to_feed")
	private Feed feed;

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

	public Feed getFeed() {
		return feed;
	}

	public void setFeed(Feed feed) {
		this.feed = feed;
	}

	@Override
	public String toString() {
		return "FeedEntry [id=" + id + ", publishedDate=" + publishedDate + ", title=" + title
				+ ", content=" + content + ", link=" + link + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean isEqual = false;
		if(obj != null && obj instanceof FeedEntry){
			FeedEntry comparable = (FeedEntry) obj;
			String compTitle = comparable.getTitle();
			Date compDate = comparable.getPublishedDate();
			isEqual = compTitle.equals(title) && compDate.equals(publishedDate);
			System.out.println(comparable.getTitle() + " is Equal to " + this.getTitle() + ": " + isEqual);
		}
		return isEqual;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((feed == null) ? 0 : feed.hashCode());
		result = prime * result + id;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((publishedDate == null) ? 0 : publishedDate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

}
