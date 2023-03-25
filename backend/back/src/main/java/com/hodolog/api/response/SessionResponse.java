package com.hodolog.api.response;

import lombok.Getter;

@Getter
public class SessionResponse {

    private final String accessToken;
    private final int id;

    public SessionResponse(String accessToken, int id) {
        this.accessToken = accessToken;
        this.id = id;
    }
}
