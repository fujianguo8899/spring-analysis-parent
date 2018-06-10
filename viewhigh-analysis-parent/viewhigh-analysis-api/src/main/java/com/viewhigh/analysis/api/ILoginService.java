package com.viewhigh.analysis.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface ILoginService {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	Integer login();
}
