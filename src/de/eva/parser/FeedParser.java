package de.eva.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import de.eva.feed.FeedEntry;

public class FeedParser {
	private static final String PUB_DATE_TAG_NAME = "pubDate";
	private static final String LINK_TAG_NAME = "link";
	private static final String DESCRIPTION_TAG_NAME = "description";
	private static final String TITLE_TAG_NAME = "title";
	private static final String ITEM_TAG_NAME = "item";
	private URL linkToFeed;
	private static final XMLInputFactory INPUT_FACTORY = XMLInputFactory.newInstance();
	private String title = "";
	private String link = "";
	private String description = "";
	private String pubDate = "";
	
	public FeedParser(String link) throws MalformedURLException, UnsupportedEncodingException {
		System.out.println("LINK: " + link);
		this.linkToFeed = URI.create(link.trim()).toURL();
	}

	public List<FeedEntry> parseFeed() throws IOException, XMLStreamException, ParseException {
		List<FeedEntry> resultingEntries = new ArrayList<FeedEntry>();
		InputStream inputStream = linkToFeed.openStream();
		XMLEventReader eventReader = INPUT_FACTORY.createXMLEventReader(inputStream);
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();
			if (event.isStartElement()) {
				QName name = event.asStartElement().getName();
				String part = name.getLocalPart();
				if (part.equals(ITEM_TAG_NAME)) {
					continue;
				} else if (part.equals(TITLE_TAG_NAME)) {
					title = getCharacterData(event, eventReader);
				} else if (part.equals(DESCRIPTION_TAG_NAME)) {
					description = getCharacterData(event, eventReader);
				} else if (part.equals(LINK_TAG_NAME)) {
					link = getCharacterData(event, eventReader);
				} else if (part.equals(PUB_DATE_TAG_NAME)) {
					pubDate = getCharacterData(event, eventReader);
				}
			} else if (event.isEndElement()) {
				if (ITEM_TAG_NAME.equals(event.asEndElement().getName().getLocalPart())) {
					resultingEntries.add(buildFeedEntry(eventReader));
					resetVars();
				}
			}
		}
		return resultingEntries;
	}

	private void resetVars() {
		title = "";
		description = "";
		link = "";
		pubDate = "";
	}

	private FeedEntry buildFeedEntry(XMLEventReader eventReader) throws ParseException, XMLStreamException {
		DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
		return new FeedEntry(dateFormat.parse(pubDate), title, description, link);
	}
	
	private String getCharacterData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
		String result = "";
		event = eventReader.nextEvent();
		if (event instanceof Characters == true) {
			result = event.asCharacters().getData();
		}
		return result;
	}

}
