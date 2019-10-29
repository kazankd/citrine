package com.citrine;

import java.net.URL;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    private URL baseUrl;
    private HttpHeaders headers;
    private ResponseEntity<String> response;
    private ObjectMapper objectMapper;
    private JsonNode root;

    @Before
    public void setUp() throws Exception {
        this.baseUrl = new URL("http://localhost:" + port + "/units/si");
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        objectMapper = new ObjectMapper();
    }


    @Test
    public void badRequestNoQuery() throws Exception {
        response = template.exchange(
            baseUrl.toString(),
            HttpMethod.GET,
            new HttpEntity<>(null, headers),
            String.class);

        Assert.assertThat(
            response.getBody(),
            response.getStatusCode(),
            Matchers.equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void badRequestMalformedQuery() throws Exception {
        response = template.exchange(
            baseUrl + "?units=(degree",
            HttpMethod.GET,
            new HttpEntity<>(null, headers),
            String.class);

        Assert.assertThat(
            response.getBody(),
            response.getStatusCode(),
            Matchers.equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void badRequestUnknownUnit() throws Exception {
        response = template.exchange(
            baseUrl + "?units=velocity",
            HttpMethod.GET,
            new HttpEntity<>(null, headers),
            String.class);

        Assert.assertThat(
            response.getBody(),
            response.getStatusCode(),
            Matchers.equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void oneParameterConversion() throws Exception {
        response = template.exchange(
            baseUrl + "?units=minute",
            HttpMethod.GET,
            new HttpEntity<>(null, headers),
            String.class);

        Assert.assertThat(
            response.getBody(),
            response.getStatusCode(),
            Matchers.equalTo(HttpStatus.OK));

        root = objectMapper.readTree(response.getBody());

        Assert.assertEquals(
            response.getBody(),
            "s",
            root.get("unit_name").asText());

        Assert.assertEquals(
            response.getBody(),
            60,
            root.get("multiplication_factor").asInt());
    }

    @Test
    public void twoParameterConversion() throws Exception {
        response = template.exchange(
            baseUrl + "?units=(degree/minute)",
            HttpMethod.GET,
            new HttpEntity<>(null, headers),
            String.class);

        Assert.assertThat(
            response.getBody(),
            response.getStatusCode(),
            Matchers.equalTo(HttpStatus.OK));

        root = objectMapper.readTree(response.getBody());

        Assert.assertEquals(
            response.getBody(),
            "(rad/s)",
            root.get("unit_name").asText());

        Assert.assertTrue(
            response.getBody(),
            0.00029088820867 == root.get("multiplication_factor").asDouble());
    }

    @Test
    public void twoParameterIdentityConversion() throws Exception {
        response = template.exchange(
            baseUrl + "?units=(hour/hour)",
            HttpMethod.GET,
            new HttpEntity<>(null, headers),
            String.class);

        Assert.assertThat(
            response.getBody(),
            response.getStatusCode(),
            Matchers.equalTo(HttpStatus.OK));

        root = objectMapper.readTree(response.getBody());

        Assert.assertEquals(
            response.getBody(),
            "(s/s)",
            root.get("unit_name").asText());

        Assert.assertTrue(
            response.getBody(),
            1 == root.get("multiplication_factor").asDouble());
    }

    @Test
    public void threeParameterConversion() throws Exception {
        response = template.exchange(
            baseUrl + "?units=(day/(hour/minute))",
            HttpMethod.GET,
            new HttpEntity<>(null, headers),
            String.class);

        Assert.assertThat(
            response.getBody(),
            response.getStatusCode(),
            Matchers.equalTo(HttpStatus.OK));

        root = objectMapper.readTree(response.getBody());

        Assert.assertEquals(
            response.getBody(),
            "(s/(s/s))",
            root.get("unit_name").asText());

        double minutesInDay = 1440;
        Assert.assertTrue(
            response.getBody(),
            minutesInDay == root.get("multiplication_factor").asDouble());
    }
}