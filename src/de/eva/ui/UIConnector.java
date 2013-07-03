package de.eva.ui;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import de.eva.feed.*;
import de.eva.subscription.*;

import java.util.*;

@ManagedBean(name="uiConnector")
@SessionScoped
public class UIConnector {
	@Inject
	private FeedSubscription subscription;
	
	private Feed feed;
	private String feedName;
	private String feedLink;
	private String feedCategory;
	
	private List<FeedEntry> feedEntries;
	
	@EJB
	private StorageService storageService;
	
	public UIConnector() {
		
		this.subscription = new FeedSubscription();
	}

	public List<Feed> getFeeds() {
		this.subscription.setFeeds(storageService.getFeeds());
		return this.subscription.getFeeds();
	}
	
	public List<FeedEntry> getFeedEntries() {
		for(Feed feed : this.subscription.getFeeds()){
				this.feedEntries.addAll(feed.getEntries());
		}
		return this.feedEntries;
	}
	
	public Feed getFeed() {
		return feed;
	}

	public void setFeed(Feed feed) {
		this.feed = feed;
	}

	public String addSubscription() {
		// ToDo: Navigation
		try {
			Feed feed = new Feed(this.	feedName, this.feedCategory, this.feedLink);
			this.subscription.newSubscription(feed);
			storageService.saveFeed(feed);
		} catch (SubscriptionAlreadyExistsException e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Feed is already subscribed"));
		}
		Collections.sort(this.subscription.getFeeds());
		this.feedName = "";
		this.feedCategory = "";
		this.feedLink = "";
		return "/Subscriptions.xhtml";
	}

	public void cancelSubscription(Feed feed) {
		try {
			subscription.cancelSubscription(feed);
			storageService.deleteFeed(feed);
		} catch (SubscriptionNotExistsException e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Feed is not subscribed."));
		}
	}
	public String refreshSubscription(){
		// ToDo: Navigation
		try {
			this.subscription.refreshSubscription(this.feed);
			storageService.saveFeed(feed);
		} catch (SubscriptionNotExistsException e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Feed is not subscribed."));
		}
		return "/Subscriptions.xhtml";
	}

	public String edit(Feed feed) {
		this.feed = feed;
		return "/EditSubscription.xhtml";
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