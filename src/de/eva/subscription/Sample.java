package de.eva.subscription;

import java.util.*;

import de.eva.feed.*;

public class Sample {
	private static final List<Feed> feeds = new ArrayList<Feed>();

	private static void populateSampleData() {
		feeds.add(new Feed("heise Developer News", "Developer News","http://www.heise.de/developer/rss/news-atom.xml"));
		feeds.add(new Feed("iX News", "IT News", "http://www.heise.de/ix/news/news-atom.xml"));
		feeds.add(new Feed("Airbus News", "Aviation", "http://www.airbus.com/newsevents/rss/all_news.xml"));
		Collections.sort(feeds);
	}

	public static List<Feed> getSampleData() {
		if (feeds.size() == 0) {
			populateSampleData();
		}
		return feeds;
	}
}