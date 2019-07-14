package ie.newwhip.social.doamin.score.mongodb;

import ie.newwhip.social.doamin.score.model.SocialScoreReport;

import java.util.List;

public interface SocialScoreReportRepository {

    List<SocialScoreReport> aggregateSocialScoreByDomainName();

    SocialScoreDBO updateSocialScore(String searchKey, String searchKeyValue, long score);
}
