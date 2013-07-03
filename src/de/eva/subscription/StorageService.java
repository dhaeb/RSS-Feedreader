package de.eva.subscription;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.eva.feed.Feed;
import de.eva.feed.FeedEntry;

@Stateless
public class StorageService {

	@PersistenceContext(unitName="feed-persistence-unit")
	private EntityManager em;
	
	public StorageService() {}

	public List<Feed> getFeeds(){
		Query query = em.createQuery("select f from Feed f");
		@SuppressWarnings("unchecked")
		List<Feed> resultList = query.getResultList();
		return resultList;
	}
	
	public void saveFeed(Feed feed) {
		System.out.println("save feed " + feed);
		em.merge(feed);
	}
	
	public void saveFeedEntry(FeedEntry feed){
		System.out.println("save feedentry " + feed);
		em.merge(feed);
	}
	
	public void deleteFeed(Feed feed){
		System.out.println("try to remove " + feed);
		em.remove(em.merge(feed));
	}
}
