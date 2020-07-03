package com.enosh.users.remote;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Component
public class RemoteRequest {

    private final RestTemplate restTemplate;

    public JSONObject get(final String URL) {
        // JSONObject ( "{ "name": "koby", "friends": [ ] }" )
        return new JSONObject(restTemplate.getForObject(URL, String.class));
    }
}
