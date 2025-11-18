package nl.fontys.tweetletweetservice.domain;

public final class RabbitMQConstants {

    private RabbitMQConstants() {
    }

    public static final String USER_AUTH_EXCHANGE = "user-auth-exchange";
    public static final String USER_GENERAL_EXCHANGE = "user-general-exchange";

    public static final String USER_AUTH_REGISTERED_KEY = "user.auth.registered";
    public static final String USER_UPDATED_KEY = "user.updated";
    public static final String USER_DELETED_KEY = "user.deleted";
    public static final String TWEET_CREATED_KEY = "tweet.created";
    public static final String TWEET_DELETED_KEY = "tweet.deleted";

    public static final String TWEET_USER_REGISTER_QUEUE = "tweet.user.register.queue";
    public static final String TWEET_USER_UPDATE_QUEUE = "tweet.user.update.queue";
    public static final String TWEET_USER_DELETE_QUEUE = "tweet.user.deleted.queue";
}
