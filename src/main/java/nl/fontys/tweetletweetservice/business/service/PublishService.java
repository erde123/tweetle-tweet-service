package nl.fontys.tweetletweetservice.business.service;

import nl.fontys.tweetletweetservice.business.dto.TweetCreatedEvent;
import nl.fontys.tweetletweetservice.business.dto.TweetDeletedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static nl.fontys.tweetletweetservice.domain.RabbitMQConstants.TWEET_CREATED_KEY;
import static nl.fontys.tweetletweetservice.domain.RabbitMQConstants.TWEET_DELETED_KEY;

@Service
public class PublishService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    public void publishTweetCreated(TweetCreatedEvent event) {
        rabbitTemplate.convertAndSend(exchange, TWEET_CREATED_KEY, event);
    }

    public void publishTweetDeleted(TweetDeletedEvent event) {
        rabbitTemplate.convertAndSend(exchange, TWEET_DELETED_KEY, event);
    }
}
