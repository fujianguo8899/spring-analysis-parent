package com.viewhigh.analysis.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.viewhigh.analysis.domain.User;


public interface IRegisterService {
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	Integer register( User user);
}
