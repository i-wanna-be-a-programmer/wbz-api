package com.wbz.wbzapi.service;

import org.springframework.http.HttpHeaders;

public interface AuthorizationService {
    void validateToken(HttpHeaders httpHeaders);
}
