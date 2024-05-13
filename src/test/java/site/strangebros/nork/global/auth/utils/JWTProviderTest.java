package site.strangebros.nork.global.auth.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SecretKeyBuilder;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import java.util.Arrays;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class JWTProviderTest {

    @Test
    void test() {
        System.out.println(Base64.getEncoder().encodeToString(Jwts.SIG.HS256.key().build().getEncoded()));
    }
}