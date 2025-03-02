package com.example.expressdeliveryback.user.controller;

import com.example.expressdeliveryback.user.servise.UserServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/user/")
public class UserController {
    @Autowired
    private UserServise userServise;

    @GetMapping(value = "appendixData")
    public Map queryUser(@RequestParam Map params) {
        return userServise.queryUser(params);
    }
}
