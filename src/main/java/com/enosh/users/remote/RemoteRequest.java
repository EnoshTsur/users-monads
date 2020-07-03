package com.enosh.users.remote;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Component
public class RemoteRequest {

    private final RestTemplate restTemplate;

    public JSONObject get(String url) {
        return new JSONObject(restTemplate.getForObject(url, String.class));
    }
}
