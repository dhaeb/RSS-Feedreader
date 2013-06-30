package de.eva.ui;

import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import de.eva.feed.*;
import de.eva.subscription.*;

import java.util.*;

@ManagedBean(name="uiConnector")
@ViewScoped
public class UIConnector {
	@Inject
	private FeedSubscription subscription;
	
	private Feed feed;
	private String feedName;
	private String feedLink;
	private String feedCategory;
	
	
	public UIConnector() {
		subscription = new FeedSubscription(Sample.getSampleData());
	}

	public List<Feed> getFeeds() {
		return this.subscription.getFeeds();
	}

	public Feed getFeed() {
		return feed;
	}

	public void setFeed(Feed feed) {
		this.feed = feed;
	}

	public void addSubscription() {
		try {
			this.subscription.newSubscription(new Feed(this.feedName, this.feedCategory, this.feedLink));
		} catch (SubscriptionAlreadyExistsException e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Feed is already subscribed"));
		}
		Collections.sort(this.subscription.getFeeds());
		this.feedName = "";
		this.feedCategory = "";
		this.feedLink = "";
	}

	public void cancelSubscription(Feed feed) {
		try {
			subscription.cancelSubscription(feed);
		} catch (SubscriptionNotExistsException e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Feed is not subscribed."));
		}
	}
	public void refreshSubscription(){
		try {
			this.subscription.refreshSubscription(this.feed);
		} catch (SubscriptionNotExistsException e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Feed is not subscribed."));
		}
	}

	public void edit(Feed feed) {
		this.feed = feed;
	}

	public String getFeedName() {
		return feedName;
	}

	public void setFeedName(String feedName) {
		this.feedName = feedName;
	}

	public String getFeedLink() {
		return feedLink;
	}

	public void setFeedLink(String feedLink) {
		this.feedLink = feedLink;
	}
	public String getFeedCategory(){
		return feedCategory;
	}
	public void setFeedCategory(String feedCategory){
		this.feedCategory = feedCategory;
	}
}