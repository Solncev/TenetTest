package com.solncev.util;

import com.solncev.model.Tweet;

/**
 * Created by Марат on 01.07.2017.
 */
public class TweetConverter {
    public static Tweet convert(org.springframework.social.twitter.api.Tweet tweet) {
        Tweet result = new Tweet();
        result.setText(tweet.getText());
        result.setRetweetCount(tweet.getRetweetCount());
        result.setLikeCount(tweet.getFavoriteCount());
        result.setLink(LinkDetector.extractUrl(tweet.getText()));
        result.setDate(String.valueOf(tweet.getCreatedAt()));
        result.setAuthor(tweet.getFromUser());
        return result;
    }
}
