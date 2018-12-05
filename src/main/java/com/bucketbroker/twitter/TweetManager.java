package com.bucketbroker.twitter;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TweetManager {

	public static ArrayList<String> getTweets(String topic) {

		Twitter twitter = new TwitterFactory().getInstance();
		// Twitter Consumer key & Consumer Secret
	    twitter.setOAuthConsumer("BzYz66IvLLNdEqf4v7nLTFnBA", "tFkNHUuUqXm2RxYSWvyJifMVrRrqTF98tnptUY4OSLtti1xmPz");
	    // Twitter Access token & Access token Secret
	    twitter.setOAuthAccessToken(new AccessToken("714274990162583555-RUFAcEWh68lvCW1VgZIKDwFz06jizI8",
	    "YASaOeBEqczkAcIo0H52tctvuzPzFDIWYzKOoCBbuqXpV"));
		ArrayList<String> tweetList = new ArrayList<String>();
		try {
			Query query = new Query(topic);
			query.setLang("en");
			QueryResult result;
			do {
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				for (Status tweet : tweets) {
					tweetList.add(tweet.getText());
				}
			} while ((query = result.nextQuery()) != null);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
		}
		return tweetList;
	}
	
}