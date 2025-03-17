package com.example.expressdeliveryback.user.servise;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserRepository {

    Map queryUser(Map params);

    void mangeUser(Map params);

    String getPassword(Map params);
}
