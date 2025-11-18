package nl.fontys.tweetletweetservice.business.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetCreatedEvent {
    private String id;
    private Long userId;
    private String content;
    private long createdAt;
}

