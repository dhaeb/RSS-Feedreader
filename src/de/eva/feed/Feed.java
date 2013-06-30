package de.eva.feed;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Feed implements Comparable<Feed> {
	
	private String name;
	private String category;
	@Id
	private String link;
	@OneToMany(fetch=FetchType.LAZY)
	private List<FeedEntry> entries;

	@Inject
	public Feed() {}
	
	public Feed(String name, String category, String link) {
		this.name = name;
		this.category = category;
		this.link = link;
		this.entries = new ArrayList<FeedEntry>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<FeedEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<FeedEntry> entries) {
		this.entries = entries;
	}

	@Override
	public int compareTo(Feed feed) {
		if (feed != null) {
			return name.compareTo(feed.getName());
		}
		return -1;
	}

	
	
	@Override
	public String toString() {
		return "Feed [name=" + name + ", category=" + category + ", link=" + link + ", entries="
				+ entries + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof Feed) {
			return ((Feed)obj).getLink().equals(this.link);
		}
		return false;
	}
}
