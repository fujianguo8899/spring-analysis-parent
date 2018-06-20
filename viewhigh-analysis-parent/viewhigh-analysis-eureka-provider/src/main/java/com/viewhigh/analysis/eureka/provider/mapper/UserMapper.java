package com.viewhigh.analysis.eureka.provider.mapper;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.viewhigh.analysis.domain.User;

public interface UserMapper {
	User getUserByName(String userName);
	int insertUser(@Param(value="user")User user);
	int countSexMale();
	int countSexFemale();
	int countAge020();
	int countAge2140();
	int countAge4160();
	int countAge6180();
	int countAge81100();
	List<User> getUsers();
}
