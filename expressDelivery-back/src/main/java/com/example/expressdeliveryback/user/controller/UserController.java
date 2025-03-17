package com.example.expressdeliveryback.user.controller;

import com.example.expressdeliveryback.user.servise.UserServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/user/")
public class UserController {
    @Autowired
    private UserServise userServise;

    @GetMapping(value = "queryUser")
    public Map queryUser(@RequestParam Map params) {
        return userServise.queryUser(params);
    }

    @PostMapping(value = "mangeUser")
    public ResponseEntity<String> mangeUser(@RequestBody Map params) {
        return userServise.mangeUser(params);
    }
}
