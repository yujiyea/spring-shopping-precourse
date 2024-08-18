package shopping.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import org.springframework.util.StringUtils;
import shopping.entity.User;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private static final String AUTHORITIES_KEY = "authority"; //role로 줄 예정
    private final Long ACCESS_TOKEN_EXPIRE_TIME = 1000L*60*600; //10hour
    private final Long REFRESH_TOKEN_EXPIRE_TIME = 1000L*60*60*24*14; //14day

    private Key key;

    protected JwtTokenProvider() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    //accesstoken 생성
    public String createAccessToken(User user){
        return createToken(user, ACCESS_TOKEN_EXPIRE_TIME);
    }

    //refreshToken 생성
    public String createRefreshToken(User user){
        return createToken(user, REFRESH_TOKEN_EXPIRE_TIME);
    }

    public String createToken(User user, long expireTime){
        //토큰을 발급하는 시간을 기준으로 토큰 유효기간 설정
        Date now = new Date();
        Date validity = new Date(now.getTime() + expireTime);

        //claim에 넣을 정보 설정하기
        Claims claims = Jwts.claims().subject(String.valueOf(user.getId())).build(); //authId 저장

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key,SignatureAlgorithm.HS512)
                .compact();
    }

    //request 헤더에서 토큰 parsing
    public String resolveToken(HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(!StringUtils.hasText(token) || !token.startsWith("Bearer ")){
            throw new IllegalStateException("토큰이 없습니다.");
        }

        return token.substring(7);
    }

    //토큰 검증
    public boolean validateToken(final String token){
        try{
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        }catch (JwtException | IllegalArgumentException e){//추후에 log에 기록남도록 하기
            return false;
        }
    }

    //access token에 들어있는 정보를 꺼내서 authentication 만들기
    public Authentication getAuthentication(String token){
        Claims claims = parseClaims(token);
        String authId = claims.getSubject();

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(authId, "", authorities);
    }

    public String getUserIdFromToken(String token){
        return parseClaims(token).getSubject();
    }

    //jwt 토큰 복호화한 후 정보 추출
    private Claims parseClaims(String accessToken){
        try{
            return Jwts.parser().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e){
            return e.getClaims();
        } catch (JwtException e){
            throw new RuntimeException("유효하지 않은 토큰입니다.");//log에 기록남도록 하기
        }
    }
}
