package ie.newwhip.social.doamin.score.service;

import ie.newwhip.social.doamin.score.KeyType;
import ie.newwhip.social.doamin.score.model.SocialScoreReport;
import ie.newwhip.social.doamin.score.mongodb.SocialScoreDBO;
import ie.newwhip.social.doamin.score.mongodb.SocialScoreRepository;
import ie.newwhip.social.doamin.score.model.SocialScoreRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class SocialScoreServiceImpl implements SocialScoreService {

    @Autowired
    private SocialScoreRepository socialScoreRepository;


    @Override
    public SocialScoreDBO save(SocialScoreRequest socialScoreRequest) {
        SocialScoreDBO socialScoreDBO = new SocialScoreDBO();
        try {
            URL myUrl = new URL(socialScoreRequest.getUrl());
            socialScoreDBO.setDomainName(myUrl.getHost());
            socialScoreDBO.setUrl(socialScoreRequest.getUrl().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        socialScoreDBO.setScore(socialScoreRequest.getScore());
        return socialScoreRepository.save(socialScoreDBO);
    }

    @Override
    public void deleteSocialScore(String key , KeyType keyType) {
        switch (keyType) {
            case ID:
                socialScoreRepository.deleteById(key);
            case URL:
                deleteByUrl(key);
            case DOMAIN:
                System.out.println("");
        }

    }


    private void deleteByUrl(String url) {
        try {
            final String decodedUrl = decodeUrl(url);
            Optional<SocialScoreDBO> socialScoreDBO = findByUrl(decodedUrl);
            socialScoreDBO.orElseThrow(() -> new RuntimeException("No user found with username "));
            socialScoreRepository.delete(socialScoreDBO.get());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<SocialScoreDBO> findById(String id) {
        return socialScoreRepository.findById(id);
    }

    @Override
    public Optional<SocialScoreDBO> findByUrl(String url)  {
        return socialScoreRepository.findByUrl(url);
    }


    @Override
    public Page<SocialScoreDBO> findByDomainName(String author, PageRequest pageRequest) {
        return socialScoreRepository.findByDomainName(author, pageRequest);
    }

    @Override
    public void updateSocialScore(KeyType keyType , String key, long score){
        switch (keyType) {
            case ID:
                updateSocialScoreById(key,score);
            case URL:
                updateSocialScoreByUrl(key,score);
        }
    }


    private void updateSocialScoreById(String id, long score) {
        socialScoreRepository.updateSocialScore("id",id,score);
    }

    private void updateSocialScoreByUrl(String url, long score) {
        try {
            socialScoreRepository.updateSocialScore("url",decodeUrl(url),score);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private String decodeUrl(String encodedUrl) throws UnsupportedEncodingException{
        return URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.name());
    }

    @Override
    public List<SocialScoreReport> generateReport() {
        return socialScoreRepository.aggregateSocialScoreByDomainName();
    }


}
