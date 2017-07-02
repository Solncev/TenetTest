package com.solncev.controller;

import com.solncev.model.Tweet;
import com.solncev.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Марат on 30.06.2017.
 */
@Controller
public class MainController {
    private final TweetService tweetService;

    @Autowired
    public MainController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostConstruct
    public void init() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                tweetService.fillDB();
            }
        }, 0, 300, TimeUnit.SECONDS);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getHomePage(Model model) throws IOException {
        model.addAttribute("tweets", tweetService.findAll());
        return "home";
    }

    @ResponseBody
    @RequestMapping(value = "/search")
    public List<Tweet> tweetSearch(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "criteria", required = false) Character criteria,
                                   @RequestParam(value = "text", required = false) String text) {
        if (!name.equals("")) {
            return tweetService.findByAuthor(name);
        }
        if (!text.equals("")) {
            return tweetService.findByText(text);
        }
        switch (criteria) {
            case 'D':
                return tweetService.findAll("date");
            case 'L':
                return tweetService.findAll("likes");
            case 'R':
                return tweetService.findAll("retweets");
            case 'A':
                return tweetService.findAll("author");
            default:
                return tweetService.findAll("date");
        }
    }
}
