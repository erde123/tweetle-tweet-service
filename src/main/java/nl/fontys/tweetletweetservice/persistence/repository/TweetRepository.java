package nl.fontys.tweetletweetservice.persistence.repository;

import nl.fontys.tweetletweetservice.persistence.document.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends MongoRepository<Tweet, String> {
}
