package com.example.expressdeliveryback.user.servise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServise {
    @Autowired
    private UserRepository userRepotiory;
    public Map queryUser(Map params) {
        return userRepotiory.queryUser(params);
    }
}
