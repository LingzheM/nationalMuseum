package cn.bupt.sse.nmp.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JWTUtil {

    // 过期时间30分钟
    private final static long expireTime = 1000 * 60 * 30;

    /**
     * 校验token
     * @param token
     * @param userPhone
     * @param password
     * @return
     */
    public static boolean verify(String token, Integer userPhone, String password) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(password);
            // 校验器
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", userPhone)
                    .build();

            // 校验token
            DecodedJWT jwt = verifier.verify(token);

            log.info("user-"+userPhone+", 登录成功");
            return true;
        } catch (Exception e) {
            log.error("登录验证失败");
        }

        return false;
    }

    /**
     *
     * @param token
     * @return
     */
    public static String getUserPhone(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);

            return jwt.getClaim("userPhone").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    /**
     * 生成token签名，expireTime时间后过期
     * @param userPhone
     * @param password
     * @return
     */
    public static String sign(Integer userPhone, String password) {
        Date expireDate = new Date(System.currentTimeMillis() + expireTime);

        Algorithm algorithm = Algorithm.HMAC256(password);

        return JWT.create()
                .withClaim("userPhone", userPhone)
                .withExpiresAt(expireDate)
                .sign(algorithm);
    }

}
