package com.onlyoffice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtil {
    @Value("${token.secret}")
    private String secret;
    @Value("${token.expire}")
    private Integer expire;



    /**
     * only office获取token
     */
    public String onlyOfficeCreateToken(Map<String,Object> params){
        JWTCreator.Builder builder = JWT.create();
        builder.withExpiresAt(getExpire());
        try {
            Class<? extends JWTCreator.Builder> aClass = builder.getClass();
            Method addClaim = aClass.getDeclaredMethod("addClaim", String.class,Object.class);
            addClaim.setAccessible(true);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                addClaim.invoke(builder,entry.getKey(),entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.sign(Algorithm.HMAC256(secret));
    }


    private Date getExpire() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expire);
        return calendar.getTime();
    }
}
