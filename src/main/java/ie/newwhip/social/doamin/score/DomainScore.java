package ie.newwhip.social.doamin.score;

import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DomainScore {

  private String domain;
  private int score;
  private URI uri;

}
