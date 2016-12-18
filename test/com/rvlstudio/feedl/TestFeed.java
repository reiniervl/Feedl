package com.rvlstudio.feedl;

/**
 * Created by Reinier van Leussen on 14-12-2016.
 * Package: com.rvlstudio.feedl
 */
public class TestFeed {
	public static void main(String args[]) {
		Feed feed = new Feed("http://www.kvraudio.com/rss/kvr_news_top.rss");
		//Feed feed = new Feed("http://www.foodhunting.nl/?feed=rss2");
		System.out.println(feed.getChannel());
		System.out.println();
		for(int i = 0; i < feed.getChannel().getItems().size(); i++) {
			/*
			System.out.println(feed.getChannel().getItems().get(i).getTitle());
			System.out.println(feed.getChannel().getItems().get(i).getPubDateObject().toString());
			System.out.println(feed.getChannel().getItems().get(i).getCategory());
			*/
			System.out.println(feed.getChannel().getItems().get(i));
			System.out.println("====================================\n");
		}
	}
}
