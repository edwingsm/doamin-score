package ie.newwhip.social.doamin.score.mongodb;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface SocialScoreRepository extends MongoRepository<SocialScoreDBO, String>, SocialScoreReportRepository {
    Page<SocialScoreDBO> findByDomainName(String domainName, Pageable pageable);

    Optional<SocialScoreDBO> findByUrl(String url);

}
