package nl.fontys.tweetletweetservice.business.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetDeletedEvent {
    private String tweetId;
    private Long userId;
}
