package com.viewhigh.analysis.eureka.provider.mapper;

import java.util.List;

import com.viewhigh.analysis.domain.User;

public interface UserMapper {
	User getUserByName(String userName);
	List<User> getUsers();
}
