package de.eva.ui;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import de.eva.feed.Feed;
import de.eva.subscription.FeedSubscription;
import de.eva.subscription.StorageService;
import de.eva.subscription.SubscriptionAlreadyExistsException;
import de.eva.subscription.SubscriptionNotExistsException;

@ManagedBean(name="uiConnector")
@ViewScoped
public class UIConnector {
	
	@Inject
	private FeedSubscription subscription;
	
	@EJB
	private StorageService storageService;
	
	private Feed feed;
	private String feedName;
	private String feedLink;
	private String feedCategory;
	
	
	public UIConnector() throws Exception {
		subscription = new FeedSubscription();
	}

	public List<Feed> getFeeds() {
		subscription.setFeeds(storageService.getFeeds());
		return subscription.getFeeds();
	}

	public Feed getFeed() {
		return feed;
	}

	public void setFeed(Feed feed) {
		this.feed = feed;
	}

	public void addSubscription() {
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
	}

	public void cancelSubscription(Feed feed) {
		try {
			subscription.cancelSubscription(feed);
			storageService.deleteFeed(feed);
		} catch (SubscriptionNotExistsException e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Feed is not subscribed."));
		}
	}
	public void refreshSubscription() {
		try {
			this.subscription.refreshSubscription(this.feed);
			storageService.saveFeed(feed);
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