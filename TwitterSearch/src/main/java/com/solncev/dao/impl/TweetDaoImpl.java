package com.solncev.dao.impl;

import com.solncev.connection.ConnectionSingleton;
import com.solncev.dao.TweetDao;
import com.solncev.model.Tweet;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Марат on 30.06.2017.
 */
@Component
public class TweetDaoImpl implements TweetDao {
    private final static String CREATE_SQL = "CREATE TABLE tweets (\n" +
            "  id int NOT NULL AUTO_INCREMENT,\n" +
            "  text VARCHAR,\n" +
            "  link VARCHAR,\n" +
            "  date VARCHAR,\n" +
            "  author VARCHAR,\n" +
            "  retweets INT,\n" +
            "  likes INT,\n" +
            "  CONSTRAINT PK_Tweets PRIMARY KEY (author,text)\n" +
            ");\n" +
            "\n" +
            "\n";
    private Connection connection = ConnectionSingleton.getConnection();

    public TweetDaoImpl() throws IOException, SQLException {
    }

    private static List<Tweet> getResultList(List<Tweet> tweets, ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                Tweet tweet = new Tweet();
                tweet.setText(resultSet.getString("text"));
                tweet.setLink(resultSet.getString("link"));
                tweet.setDate(resultSet.getString("date"));
                tweet.setAuthor(resultSet.getString("author"));
                tweet.setRetweetCount(resultSet.getInt("retweets"));
                tweet.setLikeCount(resultSet.getInt("likes"));
                tweets.add(tweet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tweets;
    }

    @PostConstruct
    private void initDb() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void add(Tweet tweet) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT into tweets (text, link, date, author, retweets," +
                    " likes) VALUES(?,?,?,?,?,?)");
            preparedStatement.setString(1, tweet.getText());
            preparedStatement.setString(2, tweet.getLink());
            preparedStatement.setString(3, String.valueOf(tweet.getDate()));
            preparedStatement.setString(4, tweet.getAuthor());
            preparedStatement.setInt(5, tweet.getRetweetCount());
            preparedStatement.setInt(6, tweet.getLikeCount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
//            e.printStackTrace();
        }
    }

    @Override
    public List<Tweet> findAll(String criteria) {
        List<Tweet> tweets = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = null;
            switch (criteria) {
                case "date":
                    preparedStatement = connection.prepareStatement("SELECT * FROM tweets t ORDER BY t.date DESC ");
                    break;
                case "likes":
                    preparedStatement = connection.prepareStatement("SELECT * FROM tweets t ORDER BY t.likes DESC ");
                    break;
                case "retweets":
                    preparedStatement = connection.prepareStatement("SELECT * FROM tweets t ORDER BY t.retweets DESC ");
                    break;
                case "author":
                    preparedStatement = connection.prepareStatement("SELECT * FROM tweets t ORDER BY LOWER(t.author)");
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            return getResultList(tweets, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Tweet> findByAuthor(String author) {
        List<Tweet> tweets = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tweets t WHERE t.author=? ORDER BY t.date DESC ");
            preparedStatement.setString(1, author);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getResultList(tweets, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Tweet> findByText(String text) {
        List<Tweet> tweets = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tweets t WHERE t.text=? ORDER BY t.date DESC ");
            preparedStatement.setString(1, text);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getResultList(tweets, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
