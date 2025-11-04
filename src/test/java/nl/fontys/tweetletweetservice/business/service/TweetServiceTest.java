package nl.fontys.tweetletweetservice.business.service;

import nl.fontys.tweetletweetservice.persistence.document.Tweet;
import nl.fontys.tweetletweetservice.persistence.repository.TweetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TweetServiceTest {

    @Mock
    private TweetRepository tweetRepository;

    @InjectMocks
    private TweetService tweetService;

    private Tweet testTweet;

    @BeforeEach
    void setUp() {
        testTweet = new Tweet();
        testTweet.setId("1");
        testTweet.setUserId(123L);
        testTweet.setContent("This is a test tweet");
        testTweet.setCreatedAt(new Date());
    }

    @Test
    void save_ShouldCallRepositorySave() {
        when(tweetRepository.save(testTweet)).thenReturn(testTweet);

        tweetService.save(testTweet);

        verify(tweetRepository, times(1)).save(testTweet);
    }

    @Test
    void findAll_ShouldReturnListOfTweets() {
        List<Tweet> expectedTweets = Arrays.asList(testTweet);
        when(tweetRepository.findAll()).thenReturn(expectedTweets);

        List<Tweet> result = tweetService.findAll();

        assertEquals(expectedTweets, result);
        verify(tweetRepository, times(1)).findAll();
    }

    @Test
    void findById_WhenTweetExists_ShouldReturnTweet() {
        when(tweetRepository.findById("1")).thenReturn(Optional.of(testTweet));

        Tweet result = tweetService.findById("1");

        assertEquals(testTweet, result);
        verify(tweetRepository, times(1)).findById("1");
    }

    @Test
    void findById_WhenTweetDoesNotExist_ShouldReturnNull() {
        when(tweetRepository.findById("999")).thenReturn(Optional.empty());

        Tweet result = tweetService.findById("999");

        assertNull(result);
        verify(tweetRepository, times(1)).findById("999");
    }

    @Test
    void deleteById_ShouldCallRepositoryDeleteById() {
        doNothing().when(tweetRepository).deleteById("1");

        tweetService.deleteById("1");

        verify(tweetRepository, times(1)).deleteById("1");
    }
}
