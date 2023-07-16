package manito.springmanito.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    // JWT에서 사용하는 상수모음
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";
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

            // Response 객체에 Cookie 추가
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedJwtException(e.getMessage());
        }
    }

    // 검증 시, JWT 토큰을 Substring
    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        log.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }

    // JWT 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // JWT 에서 사용자 정보 가지고 오기
    // body 정보이므로 getBody()로 꺼내옴
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build().parseClaimsJws(token).getBody();
    }

    public String getTokenFromRequest(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) { // 가져온 쿠키 전부다 검사
                if (cookie.getName().equals(AUTHORIZATION_HEADER)) {
                    try {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8"); // Encode 되어 넘어간 Value 다시 Decode
                    } catch (UnsupportedEncodingException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }

}