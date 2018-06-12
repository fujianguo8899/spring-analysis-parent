package com.viewhigh.analysis.eureka.consumer.runnable;

import com.viewhigh.analysis.domain.User;
import com.viewhigh.analysis.eureka.consumer.service.LoginFeignClient;

public class RecordUserLogin implements Runnable {

	private User user;
	
	private LoginFeignClient loginFeignClient;

	public RecordUserLogin(final User user, final LoginFeignClient loginFeignClient) {
		this.user = user;
		this.loginFeignClient = loginFeignClient;
	}

	@Override
	public void run() {
		loginFeignClient.recordUserLogin(user);
	}

}
