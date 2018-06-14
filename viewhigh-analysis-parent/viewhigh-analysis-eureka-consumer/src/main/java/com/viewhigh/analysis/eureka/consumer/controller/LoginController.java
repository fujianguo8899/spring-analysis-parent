package com.viewhigh.analysis.eureka.consumer.controller;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Producer;
import com.viewhigh.analysis.domain.ErrorInfo;
import com.viewhigh.analysis.domain.User;
import com.viewhigh.analysis.eureka.consumer.runnable.RecordUserLogin;
import com.viewhigh.analysis.eureka.consumer.service.LoginFeignClient;
import com.viewhigh.analysis.eureka.consumer.utils.TokenUtil;
import com.viewhigh.analysis.eureka.consumer.vo.LoginVo;
import com.viewhigh.analysis.eureka.consumer.vo.UserVo;
import com.viewhigh.analysis.exception.CheckedException;

@RestController 
public class LoginController {
	
	public static final String CAPTCHAKEY = "captchaKey";
	public static final Integer FAILCOUNTNUMBER = 3;

	private final ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 
																	   3, 
																	   2, 
																	   TimeUnit.MINUTES, 
																	   new ArrayBlockingQueue<>(100), 
																	   new ThreadPoolExecutor.DiscardPolicy());
	
	@Autowired
	private TokenUtil tokenUtil;
	
	@Autowired
	private StringRedisTemplate strRedisTemplate;
	
	@Autowired
    private RedisTemplate<String, Object> redisTemplate;
	
    @Autowired
    private LoginFeignClient loginFeignClient;
    
    @Autowired
	private Producer captchaProducer;

    /**
     * 用户登录
     * @param userName
     * @param password
     * @param captchaCode
     * @return
     */
    @PostMapping("/login")
    public Map<String, Object> login(HttpServletRequest request,
    								 HttpServletResponse response,
    								 @RequestBody LoginVo loginVo){
    	// 获取20位的captchaKey， 如果没有就新增
    	String captchaKey = getSetCaptchaKeyByRequest(request, response);
    	// 根据查询登录失败次数,并校验验证码
    	String captchaFailCountKey = validateCaptcha(loginVo.getCaptchaCode(), captchaKey);
    	// service处理逻辑
    	User user;
    	try{
    		// 登陆成功删除redis中captchaKey和captchaFailCountKey
    		user = loginFeignClient.login(loginVo.getUserName(), loginVo.getPassword());
    		List<String> keys = new ArrayList<>();
    		keys.add(captchaKey);
    		keys.add(captchaFailCountKey);
    		strRedisTemplate.delete(keys);
    	}catch (Exception e) {
    		// 登录失败，失败次数+1
			Long captchaFailCount = strRedisTemplate.opsForValue().increment(captchaFailCountKey, 1L);
			setFailCountCookie(response, captchaFailCountKey, captchaFailCount);
			throw e;
		}
    	UserVo userVo = UserVo.fromDoMian(user);
    	String token = tokenUtil.generateToken(userVo);
    	setTokenCookie(response, token);
    	Map<String, Object> map = new HashMap<>();
        map.put("user", userVo);
        map.put("token", token);
        
        // 异步记录登录记录
        executor.submit(new RecordUserLogin(user, loginFeignClient, redisTemplate));
        
        return map;
    }
    
    /**
     * 获取captchaKey,若cookie中不存在，直接生成
     * @param request
     * @param response
     * @return
     */
    private String getSetCaptchaKeyByRequest(HttpServletRequest request,
			 								 HttpServletResponse response){
    	Cookie[] cookies = request.getCookies();
    	String captchaKey = getCaptchaKeyInCookie(cookies);
    	if (captchaKey == null) {
    		// 生成20位随机字符作为key
    		captchaKey = RandomStringUtils.randomAlphabetic(20);
    		Cookie cookie = new Cookie(CAPTCHAKEY, captchaKey);
    		cookie.setHttpOnly(false);
    		cookie.setPath("/");
    		response.addCookie(cookie);
		}
    	return captchaKey;
    }
    
    /**
     * 获取CAPTCHAKEY的value值,即20位随机字符
     * @param cookies
     * @return
     */
    private String getCaptchaKeyInCookie(Cookie[] cookies){
    	if (cookies == null) {
			return null;
		}
    	for (Cookie cookie : cookies) {
			String cookieName = cookie.getName();
			if (CAPTCHAKEY.equals(cookieName)) {
				return cookie.getValue();
			}
		}
    	return null;
    }
    
    /**
     * 获取登录失败次数,并验证验证码
     * @param captchaCode
     * @param captchaKey
     * @return
     */
    private String validateCaptcha(String captchaCode, String captchaKey){
    	// 获取登录失败次数
    	String captchaFailCountKey = captchaKey + "_count";
    	String failCount = strRedisTemplate.opsForValue().get(captchaFailCountKey);
    	if (failCount == null) {
			strRedisTemplate.opsForValue().set(captchaFailCountKey, "0", 1000, TimeUnit.SECONDS);
		}else {
			Long failCountNumber = Long.valueOf(failCount);
			String captchaCodeInRedis = strRedisTemplate.opsForValue().get(captchaKey);
			if (!captchaValid(failCountNumber, captchaCode, captchaCodeInRedis)) {
				throw new CheckedException(ErrorInfo.VERIFY_CODE_ERROR);
			}
		}
    	
    	return captchaFailCountKey;
    }
    
    private Boolean captchaValid(Long failCountNumber, String captchaCode, String captchaInRedis){
        if (failCountNumber >= FAILCOUNTNUMBER.longValue() ){
            if (StringUtils.isEmpty(captchaCode)){
                return false;
            }
            if (!captchaCode.equalsIgnoreCase(captchaInRedis)){
                return false;
            }
        }
        return true;
    }
    
    private void setFailCountCookie(HttpServletResponse response, String captchaFailCountKey, Long captchaFailCount){
      Cookie cookie = new Cookie("failCount",String.valueOf(captchaFailCount));
      cookie.setHttpOnly(false);
      cookie.setPath("/");
      response.addCookie(cookie);
    }
    
    /**
     * 将token放到cookie里面去，并设置cookie的httponly属性为true
     * @param response
     * @param token
     */
    private void setTokenCookie(HttpServletResponse response,String token){
    	Cookie cookie = new Cookie("Authorization",token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    
    /**
     * 生成验证码
     * @param request
     * @param response
     * @return
     */
	@GetMapping(value = "/captcha-image")
	public ModelAndView getKaptchaImage(HttpServletRequest request,
										HttpServletResponse response) {
        String captchaKey = getSetCaptchaKeyByRequest(request, response);
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control",
				"no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");

		String capText = captchaProducer.createText();

		BufferedImage bi = captchaProducer.createImage(capText);
		try (
				ServletOutputStream out = response.getOutputStream();
			){
            strRedisTemplate.opsForValue().set(captchaKey, capText, (long)60*5, TimeUnit.SECONDS);
            ImageIO.write(bi, "jpg", out);
			out.flush();
		} catch (Exception e){
			throw new CheckedException(ErrorInfo.VERIFY_REFRESH_CODE_ERROR);
        }
		return null;
	}
    
    
    
    
}
