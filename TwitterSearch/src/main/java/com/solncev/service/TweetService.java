package com.solncev.service;

import com.solncev.model.Tweet;

import java.util.List;

/**
 * Created by Марат on 30.06.2017.
 */
public interface TweetService {
    List<Tweet> findAll(String criteria);

    List<Tweet> findAll();

    void addAll(List<Tweet> tweets);

    List<Tweet> findByAuthor(String author);

    List<Tweet> findByText(String text);

    List<Tweet> fillDB();
}
