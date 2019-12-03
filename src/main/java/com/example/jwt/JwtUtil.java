package com.example.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JwtUtil {

  private static final String secret = "sjhjkdhlskhvusdhvqiuwbvkzk";

  public static String createJWT(long ttlMillis) {
    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);
    Map<String, Object> claims = new HashMap<>();
    claims.put("uid", UUID.randomUUID().toString());
    JwtBuilder jwtBuilder =
        Jwts.builder()
            .setClaims(claims)
            .setId(UUID.randomUUID().toString())
            .setIssuedAt(now)
            .signWith(SignatureAlgorithm.HS256, secret);
    if (ttlMillis > 0) {
      Date expTime = new Date(nowMillis + ttlMillis);
      jwtBuilder.setExpiration(expTime);
    }
    return jwtBuilder.compact();
  }

  public static Claims parseJWT(String token){
    Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

    return claims;
  }

  public static Boolean isVerify(String token,String pwd) {
    //得到DefaultJwtParser
    Claims claims = Jwts.parser()
            //设置签名的秘钥
            .setSigningKey(secret)
            //设置需要解析的jwt
            .parseClaimsJws(token).getBody();

    if ("123456".equals(pwd)) {
      return true;
    }

    return false;
  }
}
