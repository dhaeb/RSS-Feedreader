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
	
	public void insertFeedEntriesIntoFeed(List<FeedEntry> entries, Feed target){
	}
	
	public void refreshFeed(Feed feed) {
		System.out.println("save feed " + feed);
		em.refresh(em.merge(feed));
	}
	
	public void saveFeed(Feed feed) {
		System.out.println("save feed " + feed);
		em.persist(em.merge(feed));
	}
	
	public void deleteFeed(Feed feed){
		System.out.println("try to remove " + feed);
		em.remove(em.merge(feed));
	}

	public void putFeedEntries(List<FeedEntry> newEntries, String linkFromFeed) {
		for(FeedEntry currentEntry : newEntries){
			Query createQuery = em.createQuery("Select e from FeedEntry e where e.feed.link = :arg ");
			createQuery.setParameter("arg", linkFromFeed);
			@SuppressWarnings("unchecked")
			List<FeedEntry> resultList = createQuery.getResultList();
			if(!resultList.contains(currentEntry)){
				em.refresh(em.merge(currentEntry));
			} else {
				System.out.println("NOT PERSISTED! " + currentEntry);
			}
		}
	}
}
