package de.eva.subscription;

import java.util.ArrayList;
import java.util.List;

import de.eva.feed.Feed;
import de.eva.subscription.SubscriptionAlreadyExistsException;
import de.eva.subscription.SubscriptionNotExistsException;

public class FeedSubscription {
	private List<Feed> feeds;

	public FeedSubscription() {
		this.feeds = new ArrayList<Feed>();
	}

	public FeedSubscription(List<Feed> feeds) {
		this.feeds = feeds;
	}

	public void newSubscription(Feed feed) throws SubscriptionAlreadyExistsException {
		if (feeds.contains(feed) == false) {
			this.feeds.add(feed);
		}else{
			throw new SubscriptionAlreadyExistsException();
		}
	}

	public void cancelSubscription(Feed feed) throws SubscriptionNotExistsException {
		if (feeds.contains(feed) == true) {
			this.feeds.remove(feed);
		} else {
			throw new SubscriptionNotExistsException();
		}
	}
	public void refreshSubscription(Feed feed) throws SubscriptionNotExistsException{
		if(feeds.contains(feed) == true){
			this.feeds.remove(feed);
			this.feeds.add(feed);
		}else{
			throw new SubscriptionNotExistsException();
		}
	}

	public List<Feed> getFeeds() {
		return this.feeds;
	}
}
