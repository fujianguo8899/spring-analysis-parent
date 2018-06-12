package com.viewhigh.analysis.eureka.provider.mapper;

import org.apache.ibatis.annotations.Param;

import com.viewhigh.analysis.domain.User;

public interface UserLoginMapper {

	Integer recordUserLogin(@Param("user") User user);
}
