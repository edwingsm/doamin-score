package ie.newwhip.social.doamin.score;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.stereotype.Service;

@Service
public class DomainService {

  public DomainScore save(String input) throws URISyntaxException {
    String [] inpustAsArray = input.split(" ");
    final URI uri = new URI(inpustAsArray[2]);
    final int score = Integer.parseInt(inpustAsArray[3]);
    final DomainScore domainScore = new DomainScore(uri.getHost(),score,uri);
    return domainScore;

  }

  public void delete(String input) throws URISyntaxException {
    String [] inpustAsArray = input.split(" ");
    final URI uri = new URI(inpustAsArray[0]);
    final int score = Integer.parseInt(inpustAsArray[1]);
    final DomainScore domainScore = new DomainScore(uri.getHost(),score,uri);
    System.out.println(domainScore);

  }

}


