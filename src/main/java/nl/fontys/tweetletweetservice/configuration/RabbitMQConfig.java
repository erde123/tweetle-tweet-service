package nl.fontys.tweetletweetservice.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static nl.fontys.tweetletweetservice.domain.RabbitMQConstants.TWEET_USER_DELETE_QUEUE;
import static nl.fontys.tweetletweetservice.domain.RabbitMQConstants.TWEET_USER_REGISTER_QUEUE;
import static nl.fontys.tweetletweetservice.domain.RabbitMQConstants.TWEET_USER_UPDATE_QUEUE;
import static nl.fontys.tweetletweetservice.domain.RabbitMQConstants.TWEET_EXCHANGE;
import static nl.fontys.tweetletweetservice.domain.RabbitMQConstants.USER_AUTH_EXCHANGE;
import static nl.fontys.tweetletweetservice.domain.RabbitMQConstants.USER_AUTH_REGISTERED_KEY;
import static nl.fontys.tweetletweetservice.domain.RabbitMQConstants.USER_DELETED_KEY;
import static nl.fontys.tweetletweetservice.domain.RabbitMQConstants.USER_GENERAL_EXCHANGE;
import static nl.fontys.tweetletweetservice.domain.RabbitMQConstants.USER_UPDATED_KEY;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange tweetExchange() {
        return new TopicExchange(TWEET_EXCHANGE);
    }

    @Bean
    public TopicExchange userAuthExchange() {
        return new TopicExchange(USER_AUTH_EXCHANGE);
    }

    @Bean
    public TopicExchange userGeneralExchange() {
        return new TopicExchange(USER_GENERAL_EXCHANGE);
    }

    @Bean
    public Queue tweetUserRegisterQueue() {
        return new Queue(TWEET_USER_REGISTER_QUEUE, true);
    }

    @Bean
    public Queue tweetUserUpdateQueue() {
        return new Queue(TWEET_USER_UPDATE_QUEUE, true);
    }

    @Bean
    public Queue tweetUserDeleteQueue() {
        return new Queue(TWEET_USER_DELETE_QUEUE, true);
    }

    @Bean
    public Binding bindTweetUserRegisterQueue() {
        return BindingBuilder.bind(tweetUserRegisterQueue())
                .to(userAuthExchange())
                .with(USER_AUTH_REGISTERED_KEY);
    }

    @Bean
    public Binding bindTweetUserUpdateQueue() {
        return BindingBuilder.bind(tweetUserUpdateQueue())
                .to(userGeneralExchange())
                .with(USER_UPDATED_KEY);
    }

    @Bean
    public Binding bindTweetUserDeleteQueue() {
        return BindingBuilder.bind(tweetUserDeleteQueue())
                .to(userGeneralExchange())
                .with(USER_DELETED_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setAlwaysConvertToInferredType(true);
        return converter;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
