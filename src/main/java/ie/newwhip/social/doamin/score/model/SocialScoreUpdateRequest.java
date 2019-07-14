package ie.newwhip.social.doamin.score.model;

import ie.newwhip.social.doamin.score.KeyType;
import lombok.Data;

import java.io.Serializable;

@Data
public class SocialScoreUpdateRequest implements Serializable {
    private KeyType keyType;
    private String key;
    private long score;

    public KeyType getKeyType() {
        return keyType;
    }

    public void setKeyType(KeyType keyType) {
        this.keyType = keyType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
