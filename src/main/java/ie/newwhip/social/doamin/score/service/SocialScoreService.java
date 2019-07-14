package ie.newwhip.social.doamin.score.service;

import ie.newwhip.social.doamin.score.model.KeyType;
import ie.newwhip.social.doamin.score.model.SocialScoreReport;
import ie.newwhip.social.doamin.score.mongodb.SocialScoreDBO;
import ie.newwhip.social.doamin.score.model.SocialScoreRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface SocialScoreService {
    SocialScoreDBO save(SocialScoreRequest socialScoreRequest);

    Optional<SocialScoreDBO> findById(String id);

    Optional<SocialScoreDBO> findByUrl(String url);

    Page<SocialScoreDBO> findByDomainName(String domainName, PageRequest pageRequest);

    List<SocialScoreReport> generateReport();

    void updateSocialScore(KeyType keyType , String key, long score);

    void deleteSocialScore(String key, KeyType keyType);

}
