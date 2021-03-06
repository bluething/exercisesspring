package io.github.bluething.spring.security.fundamentaljwt.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JwtSecret {

    public SecretKey getRandomKey() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode("QkMxOTQyRTE0MEFEOEY1M0NBOTIyOEQwMkU5NDlEQkI2NDlDNDA5MjVFN0VEODNEREVDMTczNzhDMUU3M0U0NQ=="));
    }

}
