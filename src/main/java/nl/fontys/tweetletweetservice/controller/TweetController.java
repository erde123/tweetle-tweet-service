package nl.fontys.tweetletweetservice.controller;

import nl.fontys.tweetletweetservice.business.dto.TweetCreatedEvent;
import nl.fontys.tweetletweetservice.business.dto.TweetDeletedEvent;
import nl.fontys.tweetletweetservice.business.service.PublishService;
import nl.fontys.tweetletweetservice.business.service.TweetService;
import nl.fontys.tweetletweetservice.persistence.document.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/tweets")
public class TweetController {
    @Autowired
    private TweetService tweetService;

    @Autowired
    private PublishService publishService;

    @PostMapping
    public ResponseEntity<String> saveTweet(@RequestBody Tweet tweet) {
        System.out.println("Received tweet: " + tweet);
        tweet.setCreatedAt(Date.from(Instant.now()));
        tweetService.save(tweet);
        publishService.publishTweetCreated(new TweetCreatedEvent(tweet.getId(), tweet.getUserId(), tweet.getContent(), tweet.getCreatedAt().getTime()));
        return ResponseEntity.ok("Tweet saved successfully");
    }

    @GetMapping
    public ResponseEntity<List<Tweet>> getAllTweets() {
        return ResponseEntity.ok(tweetService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTweetById(@PathVariable("id") String id) {
        Tweet tweet = tweetService.findById(id);
        if (tweet == null) {
            return ResponseEntity.status(404).body("Tweet not found");
        } else {
            return ResponseEntity.ok(tweet);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTweetById(@PathVariable("id") String id) {
        Tweet tweet = tweetService.findById(id);
        if (tweet == null) {
            return ResponseEntity.status(404).body("Tweet not found");
        } else {
            tweetService.deleteById(id);
            publishService.publishTweetDeleted(new TweetDeletedEvent(id, tweet.getUserId()));
            return ResponseEntity.ok("Tweet deleted successfully");
        }
    }
}
