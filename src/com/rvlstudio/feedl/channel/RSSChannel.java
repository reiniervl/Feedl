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

package com.rvlstudio.feedl.channel;

/**
 * Created by Reinier van Leussen on 14-12-2016.
 * Package: com.rvlstudio.feedl
 */

import com.rvlstudio.feedl.item.RSSItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RSSChannel implements Channel {
	private String url;
	private String title;
	private String link;
	private String description;
	private String language;
	private String copyright;
	private String managingEditor;
	private String webMaster;
	private String pubDate;
	private Date pubDateObject;
	private String lastBuildDate;
	private ArrayList<String> category = new ArrayList<>();
	private String generator;
	private ChannelImage image = new ChannelImage();

	private ArrayList<RSSItem> items = new ArrayList<>();


	public ArrayList<RSSItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<RSSItem> items) {
		this.items = items;
	}

	public String getURL() {
		return url;
	}

	public void setURL(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getManagingEditor() {
		return managingEditor;
	}

	public void setManagingEditor(String managingEditor) {
		this.managingEditor = managingEditor;
	}

	public String getWebMaster() {
		return webMaster;
	}

	public void setWebMaster(String webMaster) {
		this.webMaster = webMaster;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
		try {
			SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
			pubDateObject = format.parse(pubDate);
		} catch(ParseException e) {
			System.out.println("Error parsing date: " + e.getMessage());
		}
	}

	public Date getPubDateObject() {
		return pubDateObject;
	}

	public String getLastBuildDate() {
		return lastBuildDate;
	}

	public void setLastBuildDate(String lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

	public ArrayList<String> getCategory() {
		return category;
	}

	public void addCategory(String category) {
		this.category.add(category);
	}

	public String getGenerator() {
		return generator;
	}

	public void setGenerator(String generator) {
		this.generator = generator;
	}

	public ChannelImage getImage() {
		return image;
	}

	public void setImage(ChannelImage image) {
		this.image = image;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Title: " + getTitle());
		sb.append("\nLink: " + getLink());
		sb.append("\nDescription: " + getDescription());
		if(getURL() != null) sb.append("\nURL: " + getURL());
		if(getLanguage() != null) sb.append("\nLanguage: " + getLanguage());
		if(getCopyright() != null) sb.append("\nCopyright: " + getCopyright());
		if(getManagingEditor() != null) sb.append("\nManaging Editor: " + getManagingEditor());
		if(getWebMaster() != null) sb.append("\nWeb Master: " + getWebMaster());
		if(getPubDate() != null) sb.append("\nPublication Date: " + getPubDate());
		if(getLastBuildDate() != null) sb.append("\nLast Build Date: " + getLastBuildDate());
		if(!getCategory().isEmpty()) sb.append("\nLast Build Date: " + getLastBuildDate());
		if(getGenerator() != null) sb.append("\nGenerator: " + getGenerator());
		if(getImage().getTitle() != null) sb.append("\nImage: " + getImage().getTitle());

		return sb.toString();
	}
}
