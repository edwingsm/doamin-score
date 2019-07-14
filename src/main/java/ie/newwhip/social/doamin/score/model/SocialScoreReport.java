package ie.newwhip.social.doamin.score.model;

import lombok.Data;

import java.io.Serializable;

//@Data
public class SocialScoreReport implements Serializable {



    private String domain;
    private int urlCount;
    private long score;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getUrlCount() {
        return urlCount;
    }

    public void setUrlCount(int urlCount) {
        this.urlCount = urlCount;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
