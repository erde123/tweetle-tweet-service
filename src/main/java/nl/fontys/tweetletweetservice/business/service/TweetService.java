package nl.fontys.tweetletweetservice.business.service;

import nl.fontys.tweetletweetservice.persistence.document.Tweet;
import nl.fontys.tweetletweetservice.persistence.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {
    @Autowired private TweetRepository tweetRepository;

    public void save(Tweet tweet) { tweetRepository.save(tweet); }

    public List<Tweet> findAll() { return tweetRepository.findAll(); }

    public Tweet findById (String id) { return tweetRepository.findById(id).orElse(null); }

    public void deleteById (String id) { tweetRepository.deleteById(id); }

    public void deleteTweetsFromUser(Long userId) {
        tweetRepository.deleteByUserId(userId);
        System.out.println("Deleted all tweets from user " + userId);
    }
}
