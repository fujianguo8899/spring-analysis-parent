package com.viewhigh.analysis.eureka.provider.service;

import com.viewhigh.analysis.api.ICountService;
import com.viewhigh.analysis.eureka.provider.mapper.UserMapper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
public class CountService implements ICountService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public Map<Integer, Integer> countSex() {
		int male = userMapper.countSexMale();
		int female = userMapper.countSexFemale(); 
		Map<Integer,Integer> count = new HashMap<>();
		count.put(1, male);
		count.put(0, female);
		return count;
	}

	@Override
	public Map<Integer, Integer> countAge() {
		int r1 = userMapper.countAge020();
		int r2 = userMapper.countAge2140();
		int r3 = userMapper.countAge4160();
		int r4 = userMapper.countAge6180();
		int r5 = userMapper.countAge81100();
		Map<Integer,Integer> count = new HashMap<>();
		count.put(1, r1);
		count.put(2, r2);
		count.put(3, r3);
		count.put(4, r4);
		count.put(5, r5);
		return count;
	}
	
}
