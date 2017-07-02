package com.solncev.service.impl;

import com.solncev.dao.TweetDao;
import com.solncev.dao.impl.TweetDaoImpl;
import com.solncev.model.Tweet;
import com.solncev.service.TweetService;
import com.solncev.util.TweetSearcher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Марат on 30.06.2017.
 */
@Service
public class TweetServiceImpl implements TweetService {
    TweetDao tweetDao = new TweetDaoImpl();

    public TweetServiceImpl() throws IOException, SQLException {
    }

    @Override
    public List<Tweet> findAll(String criteria) {
        return tweetDao.findAll(criteria);
    }

    @Override
    public List<Tweet> findAll() {
        return findAll("date");
    }

    @Override
    public void addAll(List<Tweet> tweets) {
        for (Tweet tweet : tweets) {
            tweetDao.add(tweet);
        }
    }

    @Override
    public List<Tweet> findByAuthor(String author) {
        return tweetDao.findByAuthor(author);
    }

    @Override
    public List<Tweet> findByText(String text) {
        return tweetDao.findByText(text);
    }

    @Override
    public List<Tweet> fillDB() {
        try {
            List<Tweet> tweets = TweetSearcher.findTweets();
            addAll(tweets);
            return tweets;
        } catch (IOException e) {
//            e.printStackTrace();
            return null;
        }
    }
}
