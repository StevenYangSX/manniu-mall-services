package pro.stevendev.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.List;

import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    private static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    private static long expirationTimeAdmin = 1000 * 60 * 150;
    private static long expirationTimeCustomer = 1000 * 60 * 150;

    public <T> String generateToken(UserDetails userDetails, T subject, Boolean isAdmin) {
        Map<String, Object> claims = new HashMap<>();
        if (isAdmin && userDetails.getAuthorities() != null) {
            claims.put("userType", "adminUser");
            claims.put("authorities", userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
        }
        if (!isAdmin) {
            claims.put("userType", "member");
        }
        return createToken(claims, subject, isAdmin);
    }

    private <T> String createToken(Map<String, Object> claims, T subject, Boolean isAdmin) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (isAdmin ? expirationTimeAdmin : expirationTimeCustomer)))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUserType(String token) {
        return extractClaim(token, claims -> claims.get("userType", String.class));
    }


    public List<String> extractAuthorities(String token) {
        return extractClaim(token, claims -> claims.get("authorities", List.class));
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
    public Boolean validateToken(String token, boolean isAdminRequest) {
        System.out.println(" ==> " + extractUserType(token));
        System.out.println(" =?=> " + isTokenExpired(token));
        System.out.println(" =isAdminRequest=> " + isAdminRequest);
        if (isAdminRequest) {
            return !isTokenExpired(token) && extractUserType(token).equals("adminUser");
        }
        return !isTokenExpired(token) && extractUserType(token).equals("member");
    }


    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
