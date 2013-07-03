package de.eva.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import de.eva.feed.*;

public class FeedParser {
	private URL linkToFeed;

	public FeedParser(String link) throws MalformedURLException {
		System.out.println("LINK: " + link);
		this.linkToFeed = new URL(link);
	}

	public Feed parseFeed() throws IOException, XMLStreamException, ParseException {
		Feed feed = null;
		// ToDo... Domain Model...
		String title = "";
		String link = "";
		String description = "";
		String author = "";
		String pubDate = "";

		boolean isFeed = true;
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		InputStream inputStream = linkToFeed.openStream();
		XMLEventReader eventReader = inputFactory
				.createXMLEventReader(inputStream);
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();
			if (event.isStartElement()) {
				QName name = event.asStartElement().getName();
				String part = name.getLocalPart();
				if (part.equals("item") == true) {
					if (isFeed == true) {
						isFeed = false;
						// ToDo: further information like pubDate, author, etc.
						// ?
						feed = new Feed(title, "Test", link);
					}
					event = eventReader.nextEvent();
				} else if (part.equals("title") == true) {
					title = getCharacterData(event, eventReader);
				} else if (part.equals("description") == true) {
					description = getCharacterData(event, eventReader);
				} else if (part.equals("link") == true) {
					link = getCharacterData(event, eventReader);
				} else if (part.equals("author") == true) {
					author = getCharacterData(event, eventReader);
				} else if (part.equals("pubDate") == true) {
					pubDate = getCharacterData(event, eventReader);
				}
				// ToDo: language?
			} else if (event.isEndElement() == true) {
				if (event.asEndElement().getName().getLocalPart().equals("item")) {
					DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
					FeedEntry entry = new FeedEntry(dateFormat.parse(pubDate), title, description, link);
					feed.getEntries().add(entry);
					event = eventReader.nextEvent();
					continue;

				}
			}
		}
		return feed;

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
