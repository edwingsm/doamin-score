package ie.newwhip.social.doamin.score.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import ie.newwhip.social.doamin.score.model.KeyType;
import ie.newwhip.social.doamin.score.model.SocialScoreReport;
import ie.newwhip.social.doamin.score.model.SocialScoreRequest;
import ie.newwhip.social.doamin.score.model.SocialScoreUpdateRequest;
import ie.newwhip.social.doamin.score.mongodb.SocialScoreDBO;
import ie.newwhip.social.doamin.score.mongodb.SocialScoreRepository;
import ie.newwhip.social.doamin.score.service.SocialScoreService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = SocialScoreController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Slf4j
public class SocialScoreControllerTest {


    @Mock
    Page<SocialScoreDBO> socialScoreDBOs;
    @MockBean
    private SocialScoreService socialScoreService;
    @MockBean
    private SocialScoreRepository socialScoreRepository;
    @MockBean
    private MongoTemplate mongoTemplate;
    @Autowired
    private MockMvc mvc;

    private SocialScoreDBO google;
    private SocialScoreDBO faceBook;
    private SocialScoreReport googleReport;
    private SocialScoreReport facebookReport;
    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {

        google = new SocialScoreDBO();
        google.setDomainName("www.google.com");
        google.setUrl("http://www.google.com/search?query=Something");
        google.setScore(10l);
        google.setId(UUID.randomUUID().toString());

        faceBook = new SocialScoreDBO();
        faceBook.setDomainName("www.facebook.com");
        faceBook.setUrl("http://www.facebook.com/search?query=Something");
        faceBook.setScore(10l);
        faceBook.setId(UUID.randomUUID().toString());

        googleReport = new SocialScoreReport();
        googleReport.setDomain("www.google.com");
        googleReport.setScore(20l);
        googleReport.setUrlCount(2);

        facebookReport = new SocialScoreReport();
        facebookReport.setDomain("www.facebook.com");
        facebookReport.setScore(10l);
        facebookReport.setUrlCount(1);

        Mockito.when(socialScoreDBOs.getContent()).thenReturn(Arrays.asList(google, faceBook));
        Mockito.when(socialScoreService.save(ArgumentMatchers.any(SocialScoreRequest.class))).thenReturn(google);
        Mockito.when(socialScoreService.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(google));
        Mockito.when(socialScoreService.findByUrl(ArgumentMatchers.anyString())).thenReturn(Optional.of(google));
        Mockito.when(socialScoreService.findByDomainName(ArgumentMatchers.anyString(), ArgumentMatchers.any(PageRequest.class))).thenReturn(socialScoreDBOs);
        Mockito.when(socialScoreService.generateReport()).thenReturn(Arrays.asList(googleReport, facebookReport));
        Mockito.doNothing().when(socialScoreService).deleteSocialScore(ArgumentMatchers.anyString(), ArgumentMatchers.any(KeyType.class));
        Mockito.doNothing().when(socialScoreService).updateSocialScore(ArgumentMatchers.any(KeyType.class), ArgumentMatchers.anyString(), ArgumentMatchers.anyLong());

    }


    @Test
    public void testDeleteById() throws Exception {
        this.mvc
                .perform(
                        delete("/delete?key=id&value=123456")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .headers(headers))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteByUrl() throws Exception {
        String url = URLEncoder.encode("http://www.faceBook.com/search?query=Something", StandardCharsets.UTF_8.toString());
        this.mvc
                .perform(
                        delete("/delete?key=url&value="+url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .headers(headers))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchById() throws Exception {
        this.mvc
                .perform(
                        get("/search?key=id&value=123456")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .headers(headers))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchByUrl() throws Exception {
        String url = URLEncoder.encode("http://www.faceBook.com/search?query=Something", StandardCharsets.UTF_8.toString());
        this.mvc
                .perform(
                        get("/search/?key=url&value="+url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .headers(headers))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearch() throws Exception {
        //wrong-key
        this.mvc
                .perform(
                        get("/search?keyType=id&key=123456")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .headers(headers))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSearchFail() throws Exception {
        String url = URLEncoder.encode("http://www.faceBook.com/search?query=Something", StandardCharsets.UTF_8.toString());
        this.mvc
                .perform(
                        get("/search?keyType=id")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .headers(headers))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSearchFail2() throws Exception {
        String url = URLEncoder.encode("http://www.faceBook.com/search?query=Something", StandardCharsets.UTF_8.toString());
        this.mvc
                .perform(
                        get("/search?key=123456")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .headers(headers))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testGenerateReport() throws Exception {
        this.mvc
                .perform(
                        get("/report")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .headers(headers))
                .andExpect(status().isOk());
    }

    @Test
    public void testSave() throws Exception {
        this.mvc
                .perform(
                        post("/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(saveRequestAsJsonString())
                                .headers(headers))
                .andExpect(status().isOk());
    }

    @Test
    public void testSaveFail() throws Exception {
        this.mvc
                .perform(
                        post("/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content("")
                                .headers(headers))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSaveFail2() throws Exception {
        this.mvc
                .perform(
                        post("/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .headers(headers))
                .andExpect(status().isBadRequest());
    }



//    @Test
//    public void testSaveFail3() throws Exception {
//        this.mvc
//                .perform(
//                        post("/save")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON)
//                                .content("{\"something\":\"somthing\"}")
//                                .headers(headers))
//                .andExpect(status().isBadRequest());
//    }


    @Test
    public void testUpdate() throws Exception {
        this.mvc
                .perform(
                        patch("/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(updateRequestAsJsonString())
                                .headers(headers))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateFail() throws Exception {
        this.mvc
                .perform(
                        patch("/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content("")
                                .headers(headers))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateFail2() throws Exception {
        this.mvc
                .perform(
                        patch("/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .headers(headers))
                .andExpect(status().isBadRequest());
    }

    //    @Test
//    public void testUpdateFail3() throws Exception {
//        this.mvc
//                .perform(
//                        patch("/save")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON)
//                                .content("{\"something\":\"somthing\"}")
//                                .headers(headers))
//                .andExpect(status().isBadRequest());
//    }

    private String saveRequestAsJsonString() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        final ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        final SocialScoreRequest socialScoreRequest = new SocialScoreRequest();
        socialScoreRequest.setUrl("https://www.duckgo.com/search?query=soemthing");
        socialScoreRequest.setScore(20);
        return ow.writeValueAsString(socialScoreRequest);
    }

    private String updateRequestAsJsonString() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        final ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        final SocialScoreUpdateRequest socialScoreRequest = new SocialScoreUpdateRequest();
        socialScoreRequest.setKey("https://www.duckgo.com/search?query=soemthing");
        socialScoreRequest.setKeyType(KeyType.URL);
        socialScoreRequest.setScore(20);
        return ow.writeValueAsString(socialScoreRequest);
    }

}
