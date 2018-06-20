package com.viewhigh.analysis.api;


import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


public interface ICountService {

	@RequestMapping(value = "/countSex", method = RequestMethod.GET)
	Map<Integer, Integer> countSex();
	@RequestMapping(value = "/countAge", method = RequestMethod.GET)
	Map<Integer, Integer> countAge();
}
