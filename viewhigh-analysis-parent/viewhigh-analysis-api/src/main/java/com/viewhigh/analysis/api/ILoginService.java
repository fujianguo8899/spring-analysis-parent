package com.viewhigh.analysis.api;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.viewhigh.analysis.domain.User;

public interface ILoginService {

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	User login(@RequestParam(value = "userName") String userName,
			   @RequestParam(value = "password") String password);
}
