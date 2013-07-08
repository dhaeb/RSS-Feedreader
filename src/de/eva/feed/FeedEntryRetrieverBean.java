package de.eva.feed;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import de.eva.parser.FeedParser;
import de.eva.subscription.StorageService;

@Stateless
public class FeedEntryRetrieverBean {

	@EJB
	private StorageService mp;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void saveNewFeedEntries() throws Exception {
		System.out.println("called timeout");
		List<Feed> feeds = mp.getFeeds();
		for (Feed currentFeed : feeds) {
			List<FeedEntry> newEntries = retrieveFeedEntries(currentFeed);
			mp.putFeedEntries(newEntries, currentFeed.getLink());
			currentFeed.setEntries(newEntries);
			System.out.println(currentFeed + " was refreshed");
		}
	}

	private List<FeedEntry> retrieveFeedEntries(Feed currentFeed) throws Exception {
		FeedParser feedParser = new FeedParser(currentFeed.getLink());
		List<FeedEntry> newEntries = feedParser.parseFeed();
		for(FeedEntry entry : newEntries){
			entry.setFeed(currentFeed);
		}
		return newEntries;
	}
}
