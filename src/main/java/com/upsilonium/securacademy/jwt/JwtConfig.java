package com.upsilonium.securacademy.jwt;

import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.crypto.SecretKey;

/**
 * @author Yannick Van Ham
 * created on Monday, 19/10/2020
 */
@ConfigurationProperties(prefix = "application.jwt")
@NoArgsConstructor
@Getter
@Setter
public class JwtConfig {
    private String secretKey;
    private String tokenPrefix;
    private Integer tokenExpirationDays;

    public String getAuthorizationHeader(){
        return HttpHeaders.AUTHORIZATION;
    }
}
