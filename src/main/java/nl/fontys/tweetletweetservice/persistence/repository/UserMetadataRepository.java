package nl.fontys.tweetletweetservice.persistence.repository;

import nl.fontys.tweetletweetservice.persistence.document.UserMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMetadataRepository extends MongoRepository<UserMetadata, Long> {
}
