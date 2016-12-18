/*
 * Copyright (c) 2016,  Reinier van Leussen
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 * disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 *  DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 *  WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 *  WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.rvlstudio.feedl.parser;

import com.rvlstudio.feedl.channel.RSSChannel;
import com.rvlstudio.feedl.item.RSSItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Reinier van Leussen on 14-12-2016.
 * Package: com.rvlstudio.feedl.parser
 */
public class FeedParser {
	private static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";

	private String url;
	private String content;

	private String documentType;
	private String version;
	private RSSChannel channel;

	public FeedParser(String url) {
		this.url = url;
		channel = new RSSChannel();

		content = fetchContent();
		if(content != null) parse();
		else System.out.println("No content to parse");
	}

	private String fetchContent() {
		try {
			URL _url = new URL(url);
			URLConnection connection = _url.openConnection();
			connection.setRequestProperty("user-agent", userAgent);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			StringBuilder content = new StringBuilder();
			String line;
			while((line = bufferedReader.readLine()) != null) {
				content.append(line);
			}
			bufferedReader.close();
			return content.toString();
		} catch(MalformedURLException e) {
			System.out.println("Malformed URL detected: " + url);
			System.out.println(e.getMessage());
		} catch(IOException e) {
			System.out.println("Could not read content from " + url);
			System.out.println(e.getMessage());
		}
		return null;
	}

	private void parse() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(content)));
			Element root = doc.getDocumentElement();

			documentType = root.getNodeName();
			version = root.getAttributeNode("version").getValue();
			Node channelNode = root.getElementsByTagName("channel").item(0);

			if(channelNode != null) {
				parseChannel(channelNode);
			} else {
				System.out.println("No channel found");
			}
		} catch(ParserConfigurationException e) {
			System.out.println("Parser not correctly configured: " + e.getMessage());
		} catch(IOException e) {
			System.out.println("Error creating document from web content: " + e.getMessage());
		} catch(SAXException e) {
			System.out.println("Error reading XML from web content" + e.getMessage());
		}
	}

	private void parseChannel(Node channelNode) {
		NodeList cItems = channelNode.getChildNodes();
		for(int i = 0; i < cItems.getLength(); i++) {
			switch(cItems.item(i).getNodeName()) {
				case "url":
					channel.setURL(cItems.item(i).getTextContent());
					break;
				case "title":
					channel.setTitle(cItems.item(i).getTextContent());
					break;
				case "link":
					channel.setLink(cItems.item(i).getTextContent());
					break;
				case "description":
					channel.setDescription(cItems.item(i).getTextContent());
					break;
				case "language":
					channel.setLanguage(cItems.item(i).getTextContent());
					break;
				case "copyright":
					channel.setCopyright(cItems.item(i).getTextContent());
					break;
				case "managingEditor":
					channel.setManagingEditor(cItems.item(i).getTextContent());
					break;
				case "webMaster":
					channel.setWebMaster(cItems.item(i).getTextContent());
					break;
				case "pubDate":
					channel.setPubDate(cItems.item(i).getTextContent());
					break;
				case "lastBuildDate":
					channel.setLastBuildDate(cItems.item(i).getTextContent());
					break;
				case "category":
					channel.addCategory(cItems.item(i).getTextContent());
					break;
				case "generator":
					channel.setGenerator(cItems.item(i).getTextContent());
					break;
				case "item":
					parseItems(cItems.item(i));
					break;
				case "image":
					parseImage(cItems.item(i));
					break;
				default:
					break;
			}
		}
	}

	private void parseImage(Node imageNode) {
		NodeList nodes = imageNode.getChildNodes();
		for(int i = 0; i < nodes.getLength(); i++) {
			switch(nodes.item(i).getNodeName()) {
				case "title":
					channel.getImage().setTitle(nodes.item(i).getTextContent());
					break;
				case "url":
					channel.getImage().setUrl(nodes.item(i).getTextContent());
					break;
				case "link":
					channel.getImage().setLink(nodes.item(i).getTextContent());
					break;
				case "width":
					channel.getImage().setWidth(Integer.parseInt(nodes.item(i).getTextContent()));
					break;
				case "height":
					channel.getImage().setHeight(Integer.parseInt(nodes.item(i).getTextContent()));
					break;
				default:
					break;
			}
		}
	}

	private void parseItems(Node item) {
		RSSItem rssItem = new RSSItem();

		NodeList elms = item.getChildNodes();
		for(int i = 0; i < elms.getLength(); i++) {
			switch(elms.item(i).getNodeName()) {
				case "title":
					rssItem.setTitle(elms.item(i).getTextContent());
					break;
				case "link":
					rssItem.setLink(elms.item(i).getTextContent());
					break;
				case "description":
					rssItem.setDescription(elms.item(i).getTextContent());
					break;
				case "author":
					rssItem.setAuthor(elms.item(i).getTextContent());
					break;
				case "category":
					rssItem.addCategory(elms.item(i).getTextContent());
					break;
				case "comments":
					rssItem.setComments(elms.item(i).getTextContent());
					break;
				case "enclosure":
					rssItem.setEnclosure(elms.item(i).getTextContent());
					break;
				case "pubDate":
					rssItem.setPubDate(elms.item(i).getTextContent());
					break;
				case "guid":
					rssItem.setGuid(elms.item(i).getTextContent());
					break;
				case "source":
					rssItem.setSource(elms.item(i).getTextContent());
					break;
				default:
					break;
			}
		}
		channel.getItems().add(rssItem);
	}

	public String getDocumentType() {
		return documentType;
	}

	public String getVersion() {
		return version;
	}

	public RSSChannel getChannel() {
		return channel;
	}
}
