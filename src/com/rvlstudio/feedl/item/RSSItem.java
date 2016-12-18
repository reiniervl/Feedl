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

package com.rvlstudio.feedl.item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Reinier van Leussen on 14-12-2016.
 * Package: com.rvlstudio.feedl
 */
public class RSSItem implements Item {
	private String title;
	private String link;
	private String description;
	private String author;
	private ArrayList<String> category = new ArrayList<>();
	private String comments;
	private String enclosure;
	private String guid;
	private Date pubDateObject;
	private String pubDate;
	private String source;

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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public ArrayList<String> getCategory() {
		return category;
	}

	public void addCategory(String category) {
		this.category.add(category);
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getEnclosure() {
		return enclosure;
	}

	public void setEnclosure(String enclosure) {
		this.enclosure = enclosure;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Date getPubDateObject() { return pubDateObject; }

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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(getTitle() != null) sb.append("Title: " + getTitle() + "\n");
		if(getLink() != null) sb.append("Link: " + getLink() + "\n");
		if(getDescription() != null) sb.append("Description: " + getDescription() + "\n");
		if(getAuthor() != null) sb.append("Author: " + getAuthor() + "\n");
		if(getComments() != null) sb.append("Comments: " + getComments() + "\n");
		if(getEnclosure() != null) sb.append("Enclosure: " + getEnclosure() + "\n");
		if(getPubDate() != null) sb.append("Publication Date: " + getPubDate() + "\n");
		if(getGuid() != null) sb.append("GUID: " + getGuid() + "\n");
		if(!getCategory().isEmpty()) {
			sb.append("Category: ");
			for(String c : getCategory()) sb.append(c + " ");
		}
		if(getSource() != null) sb.append("Source: " + getSource());

		return sb.toString();
	}
}
