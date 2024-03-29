package manito.springmanito.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import manito.springmanito.global.exception.JwtErorrException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    // JWT에서 사용하는 상수모음
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final long TOKEN_EXPIRED_TIME = 60 * 60 * 1000L;    // 1 시간

    @Value("${jwt.secretKey}") // Base64 Encode
    private String secretKey;
    private Key key;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // 해당 클래스가 만들어질 때, 일종의 생성자처럼 활용되는 것
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // JWT 생성
    // UserNamePassword
    public String createToken(String userId) {
        Date date = new Date();
        return BEARER_PREFIX + Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(date.getTime() + TOKEN_EXPIRED_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    // Cookie 에 저장 -> 공백 없애는 encoding 필요 O
    public void addJwtToCookie(String token, HttpServletResponse response) {
        try {
            token = URLEncoder.encode(token, "utf-8")
                    .replaceAll("\\+", "%20");

            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token); // Name-Value
            cookie.setPath("/");
            cookie.setHttpOnly(false); // Enhances security by preventing access from JavaScript
            cookie.setSecure(true); // Use only with HTTPS
            cookie.setAttribute("SameSite", "None"); // CSRF
            // Response 객체에 Cookie 추가
            response.addCookie(cookie);

        } catch (UnsupportedEncodingException e) {
            throw new JwtErorrException(e.getMessage());
        }
    }

    // 검증 시, JWT 토큰을 Substring
    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        log.error("Not Found Token");
        throw new JwtErorrException("Not Found Token");
    }

    // JWT 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            throw new JwtErorrException("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
            throw  new JwtErorrException("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            throw new JwtErorrException("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            throw new JwtErorrException("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
    }

    // JWT 에서 사용자 정보 가지고 오기
    // body 정보이므로 getBody()로 꺼내옴
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build().parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String token) {
        String tokenValue = substringToken(token);
        return validateToken(tokenValue);
    }

    // Token 에서 userId 가져오기
    public String getUsernameFromToken(String token) {
        String tokenValue = substringToken(token);
        return getUserInfoFromToken(tokenValue).getSubject();
    }
}