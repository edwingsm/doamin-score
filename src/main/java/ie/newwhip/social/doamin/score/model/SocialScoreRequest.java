package ie.newwhip.social.doamin.score.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SocialScoreRequest implements Serializable {

    private String url;
    private long score;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
