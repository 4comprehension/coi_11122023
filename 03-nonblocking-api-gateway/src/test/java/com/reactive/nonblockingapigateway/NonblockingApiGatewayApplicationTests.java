package com.reactive.nonblockingapigateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NonblockingApiGatewayApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetRealUsers() {
        ResponseEntity<String> response = restTemplate.getForEntity("/tenant/real/users", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("[\"adam\",\"peter\",\"olga\"]");
    }

    @Test
    public void testGetAuchanOrders() {
        ResponseEntity<String> response = restTemplate.getForEntity("/tenant/auchan/orders/42", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("[\"apple\",\"orange\",\"milk\"]");
    }

    @Test
    public void testGetAuchanOrdersAgain() {
        ResponseEntity<String> response = restTemplate.getForEntity("/tenant/auchan/orders/42", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("[\"apple\",\"orange\",\"milk\"]");
    }
}
