package nl.fontys.tweetletweetservice.controller;

import nl.fontys.tweetletweetservice.business.service.TweetService;
import nl.fontys.tweetletweetservice.persistence.document.Tweet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TweetControllerTest {

    @Mock
    private TweetService tweetService;

    @InjectMocks
    private TweetController tweetController;

    private Tweet testTweet;

    @BeforeEach
    void setUp() {
        testTweet = new Tweet();
        testTweet.setId("1");
        testTweet.setUserId(123L);
        testTweet.setContent("Hello world!");
        testTweet.setCreatedAt(new Date());
    }

    // --- POST /api/tweets ---
    @Test
    void saveTweet_WhenSuccessful_ShouldReturnOkResponse() {
        doNothing().when(tweetService).save(any(Tweet.class));

        ResponseEntity<String> response = tweetController.saveTweet(testTweet);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tweet saved successfully", response.getBody());
        verify(tweetService, times(1)).save(testTweet);
    }

    @Test
    void saveTweet_WhenExceptionThrown_ShouldReturnErrorResponse() {
        doThrow(new RuntimeException("Error")).when(tweetService).save(any(Tweet.class));

        assertThrows(RuntimeException.class, () -> tweetController.saveTweet(testTweet));
        verify(tweetService, times(1)).save(testTweet);
    }

    // --- GET /api/tweets ---
    @Test
    void getAllTweets_WhenSuccessful_ShouldReturnAllTweets() {
        List<Tweet> expectedTweets = Arrays.asList(testTweet);
        when(tweetService.findAll()).thenReturn(expectedTweets);

        ResponseEntity<List<Tweet>> response = tweetController.getAllTweets();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTweets, response.getBody());
        verify(tweetService, times(1)).findAll();
    }

    @Test
    void getAllTweets_WhenExceptionThrown_ShouldReturnError() {
        when(tweetService.findAll()).thenThrow(new RuntimeException("Error"));

        assertThrows(RuntimeException.class, () -> tweetController.getAllTweets());
        verify(tweetService, times(1)).findAll();
    }

    // --- GET /api/tweets/{id} ---
    @Test
    void getTweetById_WhenTweetExists_ShouldReturnTweet() {
        when(tweetService.findById("1")).thenReturn(testTweet);

        ResponseEntity<Object> response = tweetController.getTweetById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testTweet, response.getBody());
        verify(tweetService, times(1)).findById("1");
    }

    @Test
    void getTweetById_WhenTweetNotFound_ShouldReturn404() {
        when(tweetService.findById("999")).thenReturn(null);

        ResponseEntity<Object> response = tweetController.getTweetById("999");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Tweet not found", response.getBody());
        verify(tweetService, times(1)).findById("999");
    }

    @Test
    void getTweetById_WhenExceptionThrown_ShouldReturnErrorResponse() {
        when(tweetService.findById("1")).thenThrow(new RuntimeException("Error"));

        assertThrows(RuntimeException.class, () -> tweetController.getTweetById("1"));
        verify(tweetService, times(1)).findById("1");
    }

    // --- DELETE /api/tweets/{id} ---
    @Test
    void deleteTweetById_WhenTweetExists_ShouldReturnOkResponse() {
        when(tweetService.findById("1")).thenReturn(testTweet);
        doNothing().when(tweetService).deleteById("1");

        ResponseEntity<String> response = tweetController.deleteTweetById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tweet deleted successfully", response.getBody());
        verify(tweetService, times(1)).deleteById("1");
    }

    @Test
    void deleteTweetById_WhenTweetNotFound_ShouldReturn404() {
        when(tweetService.findById("999")).thenReturn(null);

        ResponseEntity<String> response = tweetController.deleteTweetById("999");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Tweet not found", response.getBody());
        verify(tweetService, never()).deleteById(anyString());
    }

    @Test
    void deleteTweetById_WhenExceptionThrown_ShouldReturnErrorResponse() {
        when(tweetService.findById("1")).thenThrow(new RuntimeException("Error"));

        assertThrows(RuntimeException.class, () -> tweetController.deleteTweetById("1"));
        verify(tweetService, never()).deleteById(anyString());
    }
}
