package com.solncev.util;


import org.springframework.context.annotation.PropertySource;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * Created by Марат on 30.06.2017.
 */
@Component
@PropertySource("classpath:twitter.properties")
public class TweetSearcher {
    public static List<com.solncev.model.Tweet> findTweets() throws IOException {
        Twitter twitter = getTwitter();
        SearchResults results = twitter.searchOperations().search("microservices");
        List<Tweet> tweets = results.getTweets();
        ArrayList<com.solncev.model.Tweet> tweetArrayList = new ArrayList<>();
        for (Tweet t : tweets) {
            tweetArrayList.add(TweetConverter.convert(t));
        }
        TweetLogger.log(String.valueOf(tweetArrayList.size()));
        return tweetArrayList;
    }

    private static Twitter getTwitter() throws IOException {
        Properties properties = new Properties();
        InputStream input = new FileInputStream("\\TwitterSearch\\src\\main\\resources\\twitter.properties");
        properties.load(input);
        String consumerKey = properties.getProperty("twitter.consumerKey");
        String consumerSecret = properties.getProperty("twitter.consumerSecret");
        String accessToken = properties.getProperty("twitter.accessToken");
        String accessTokenSecret = properties.getProperty("twitter.accessTokenSecret");
        Twitter twitter = (Twitter) new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        return twitter;
    }
}
