package com.solncev.dao;

import com.solncev.model.Tweet;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Марат on 30.06.2017.
 */
@Component
public interface TweetDao {
    void add(Tweet tweet);

    List<Tweet> findAll(String criteria);

    List<Tweet> findByAuthor(String author);

    List<Tweet> findByText(String text);
}
