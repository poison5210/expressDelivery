package com.example.expressdeliveryback.user.servise;

import com.example.expressdeliveryback.tools.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServise {
    @Autowired
    private UserRepository userRepotiory;

    /**
     * 验证用户信息
     * @param params
     * @return
     */
    public Map queryUser(Map params) {
        Map returnMap = new HashMap();
        String password = userRepotiory.getPassword(params);
        if (password != null) {
            // 对输入的密码进行MD5加密
            String encryptedPassword = MD5Utils.inputPassToFormPass(params.get("password").toString());
            // 验证加密后的密码是否与数据库中的密码匹配
            returnMap.put("status",password.equals(encryptedPassword));
        }
        returnMap.put("data",userRepotiory.queryUser(params));
        return returnMap;
    }

    /**
     * 用户信息管理
     * @param params
     * @return
     */
    public ResponseEntity<String> mangeUser(Map params) {
        String hashedPassword = MD5Utils.inputPassToFormPass(params.get("password").toString());
        params.put("password",hashedPassword);
        params.put("id", UUID.randomUUID().toString());
        userRepotiory.mangeUser(params);
        return ResponseEntity.ok("操作成功");
    }
}
