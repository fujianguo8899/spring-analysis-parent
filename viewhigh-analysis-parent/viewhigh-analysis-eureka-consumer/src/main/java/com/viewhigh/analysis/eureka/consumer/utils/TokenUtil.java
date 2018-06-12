package com.viewhigh.analysis.eureka.consumer.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.viewhigh.analysis.eureka.consumer.vo.UserVo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtil implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String USER_KEY_PREFIX = "user:";
	
	public static final String CLAIM_KEY_CREATED = "created";
    public static final String CLAIM_KEY_ID = "id"; // 用户标识
    public static final String CLAIM_KEY_NAME = "name";//用户名称
    public static final String CLAIM_KEY_REALNAME = "realname";//用户名称
    
    private static final String ID_HASH_KEY = "id";
    private static final String USER_HASH_KEY = "user";
    private static final String TOKEN_HASH_KEY = "token";

    private static final BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(500);
    private final transient ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 3, 2, TimeUnit.MINUTES, blockingQueue, new ThreadPoolExecutor.DiscardPolicy());
    
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;
    
	@Autowired
	private StringRedisTemplate strRedisTemplate;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 生成token并存入redis
	 * @param user
	 * @return
	 */
	public String generateToken(UserVo user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_NAME, user.getName());
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_ID, user.getId());
        claims.put(CLAIM_KEY_REALNAME, user.getRealName());
        String token = generateToken(claims);
        // 同步设置 token ttl
        Long uid = user.getId();
        Map<String, Object> map = new HashMap<>();
        map.put(ID_HASH_KEY, uid);
        map.put(USER_HASH_KEY, user);
        map.put(TOKEN_HASH_KEY, token);
        redisTemplate.opsForHash().putAll(USER_KEY_PREFIX + uid, map);
        redisTemplate.expire(USER_KEY_PREFIX + uid, expiration, TimeUnit.SECONDS);
        
        return token;
    }
	
	/**
	 * 生成token
	 * @param claims
	 * @return
	 */
    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                   .setClaims(claims)
                   .signWith(SignatureAlgorithm.HS512, secret)
                   .compact();
    }
    
    /**
     * 判断token是否失效
     * @param token
     * @return
     */
    public Boolean isTokenValid(String token) {
        if (StringUtils.isEmpty(token))
            return false;
//    	1. 可parse
        Long uid = getUidFromToken(token);
        if (uid == -1)
            return false;
//    	2. uid - token 映射关系正确 (登录校验完成，记录token-uid映射关系)
//    	3. token 未失效
        String tokenInCache = getTokenFromCache(uid);
        return token.equals(tokenInCache) ? true : false;
    }
    
    private String getTokenFromCache(Long uid) {
        Object token = redisTemplate.opsForHash().get(USER_KEY_PREFIX + uid, TOKEN_HASH_KEY);
        if (token == null) {
            return "";
        }
        return (String) token;
    }
    
    public Long getUidFromToken(String token) {
        Long uid = -1l;
        try {
            final Claims claims = getClaimsFromToken(token);
            if (claims != null) {
                uid = Long.valueOf(claims.get(CLAIM_KEY_ID).toString());
            }
        } catch (Exception ex) {
            uid = -1l;
        }
        return uid;
    }
    
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            if (token.equals("printToken")) {
                claims=null;
            } else {
                claims = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody();
            }
        } catch (Exception ex) {
            claims = null;
        }
        return claims;
    }
    
    /**
     * 刷新token失效时间
     * @param token
     */
    public void refreshToken(String token) {
        threadPoolExecutor.submit(new RefreshTokenDelegator(token));
    }
    
    private final class RefreshTokenDelegator implements Runnable {
        private String token;

        public RefreshTokenDelegator(final String token) {
            this.token = token;
        }

        public void run() {
			if (isTokenValid(token)) {
				Long uid = getUidFromToken(token);
				strRedisTemplate.expire(USER_KEY_PREFIX + uid, expiration, TimeUnit.SECONDS);
			}
        }
    }

}
