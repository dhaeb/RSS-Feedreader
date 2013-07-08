package de.eva.feed;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class FeedEntryRetriever {

	@EJB
	private FeedEntryRetrieverBean savingServiceForFeedEntries;

	public FeedEntryRetriever() {}

	@PostConstruct
	public void init(){
		retrieveFeedEntries();
	}
	
	@Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
	public void retrieveFeedEntries() {
		try {
			savingServiceForFeedEntries.saveNewFeedEntries();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.WARNING, e.getMessage(), e);
		}
	}
}
