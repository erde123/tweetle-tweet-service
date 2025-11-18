package nl.fontys.tweetletweetservice.business.listener;

import nl.fontys.tweetletweetservice.business.service.TweetService;
import nl.fontys.tweetletweetservice.business.service.UserMetadataService;
import nl.fontys.tweetletweetservice.domain.UserEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static nl.fontys.tweetletweetservice.domain.RabbitMQConstants.TWEET_USER_DELETE_QUEUE;
import static nl.fontys.tweetletweetservice.domain.RabbitMQConstants.TWEET_USER_REGISTER_QUEUE;
import static nl.fontys.tweetletweetservice.domain.RabbitMQConstants.TWEET_USER_UPDATE_QUEUE;

@Component
public class UserEventListener {

    private final UserMetadataService userMetadataService;
    private final TweetService tweetService;

    public UserEventListener(UserMetadataService userMetadataService, TweetService tweetService) {
        this.userMetadataService = userMetadataService;
        this.tweetService = tweetService;
    }

    @RabbitListener(queues = TWEET_USER_REGISTER_QUEUE)
    public void handleUserRegister(UserEvent event) {
        System.out.println("📩 Tweet service received user registration for: " + event.getUsername());
        userMetadataService.saveOrUpdate(event);
    }

    @RabbitListener(queues = TWEET_USER_UPDATE_QUEUE)
    public void handleUserUpdate(UserEvent event) {
        System.out.println("📩 Tweet service received user update for: " + event.getUsername());
        userMetadataService.saveOrUpdate(event);
    }

    @RabbitListener(queues = TWEET_USER_DELETE_QUEUE)
    public void handleUserDelete(UserEvent event) {
        System.out.println("📩 Tweet service received user deletion for id: " + event.getUserId());
        tweetService.deleteTweetsFromUser(event.getUserId());
        userMetadataService.deleteUser(event.getUserId());
    }
}
