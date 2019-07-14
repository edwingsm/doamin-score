package ie.newwhip.social.doamin.score.controller;

import ie.newwhip.social.doamin.score.KeyType;
import ie.newwhip.social.doamin.score.model.SocialScoreRequest;
import ie.newwhip.social.doamin.score.model.SocialScoreUpdateRequest;
import ie.newwhip.social.doamin.score.mongodb.SocialScoreDBO;
import ie.newwhip.social.doamin.score.service.SocialScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class SocialScoreController {

    @Autowired
    private SocialScoreService socialScoreService;

    @PostMapping("save")
    public ResponseEntity<SocialScoreDBO> save(@RequestBody SocialScoreRequest socialScore) {
        SocialScoreDBO score = socialScoreService.save(socialScore);
        return new ResponseEntity<>(score, HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<?> search(@RequestParam(value = "key", required = true) final String key,
                                    @RequestParam(value = "value", required = true) final String value) {
        System.out.println(key + "-" + value);
        KeyType keyType = KeyType.valueOf(key.toUpperCase());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("update")
    public ResponseEntity<?> updateSocialScoreById(@RequestBody SocialScoreUpdateRequest request) {
        socialScoreService.updateSocialScore(request.getKeyType(),request.getKey(), request.getScore());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteSocialScoreById(@RequestParam(value = "key", required = true) final String key,
                                                   @RequestParam(value = "value", required = true) final String value) {
        KeyType keyType = KeyType.valueOf(key.toUpperCase());
        socialScoreService.deleteSocialScore(value, keyType);
        return ResponseEntity.ok().build();
    }

    @GetMapping("report")
    public ResponseEntity<?> getReport() {
        return ResponseEntity.ok(socialScoreService.generateReport());
    }

}
