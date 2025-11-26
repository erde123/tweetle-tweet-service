package nl.fontys.tweetletweetservice.business.service;

import nl.fontys.tweetletweetservice.business.dto.TweetCreatedEvent;
import nl.fontys.tweetletweetservice.business.dto.TweetDeletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static nl.fontys.tweetletweetservice.domain.RabbitMQConstants.TWEET_CREATED_KEY;
import static nl.fontys.tweetletweetservice.domain.RabbitMQConstants.TWEET_DELETED_KEY;
import static nl.fontys.tweetletweetservice.domain.RabbitMQConstants.TWEET_EXCHANGE;

@Service
public class PublishService {
    private static final Logger log = LoggerFactory.getLogger(PublishService.class);

    private final RabbitTemplate rabbitTemplate;

    public PublishService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishTweetCreated(TweetCreatedEvent event) {
        rabbitTemplate.convertAndSend(TWEET_EXCHANGE, TWEET_CREATED_KEY, event);
        log.info("RabbitMQ tweet.created event sent for tweet {}", event.getId());
    }

    public void publishTweetDeleted(TweetDeletedEvent event) {
        rabbitTemplate.convertAndSend(TWEET_EXCHANGE, TWEET_DELETED_KEY, event);
        log.info("RabbitMQ tweet.deleted event sent for tweet {}", event.getTweetId());
    }
}
