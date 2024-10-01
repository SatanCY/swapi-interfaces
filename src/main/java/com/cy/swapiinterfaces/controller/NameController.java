package com.cy.swapiinterfaces.controller;

import cn.hutool.json.JSONUtil;
import com.sw.swapiclientsdk.common.SignGenerator;
import com.sw.swapiclientsdk.model.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 测试用例接口
 *
 * @Author：SatanCY
 * @Date：2024/10/1 16:31
 */
@RestController
@RequestMapping("/name")
public class NameController {

    private String accessKeyInDb = "CWL2bTNn8co6SScwBLr8T79bmLwyPwEf";
    private String secretKeyInDb = "TTLqtqlvuX4lcJLMu+xxkgWcffex+hP4pzUQgODwljU=";

    @GetMapping("/")
    public String getNameByGet(String name) {
        return "GET 你的名字是：" + name;
    }

    @PostMapping("/")
    public String getNameByPost(@RequestParam String name) {
        return "POST 你的名字是：" + name;
    }

    @PostMapping("/json")
    public String getNameByRestful(@RequestBody User user, HttpServletRequest request) {
        //从请求头中获取accessKey的值
        String accessKey = request.getHeader("accessKey");
        //从请求头中获取secretKey的值
//        String secretKey = request.getHeader("secretKey");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String body = JSONUtil.toJsonStr(user);
        String sign = request.getHeader("sign");
        //校验
//        if (!"CWL2bTNn8co6SScwBLr8T79bmLwyPwEf".equals(accessKey) || !"TTLqtqlvuX4lcJLMu+xxkgWcffex+hP4pzUQgODwljU=".equals(secretKey)) {
//            //未通过
//            throw new RuntimeException("无权限");
//        }
        //校验
        // todo 实际从数据库中查用户数据
        if (!accessKeyInDb.equals(accessKey)) {
            //未通过
            throw new RuntimeException("无权限");
        }
        // 直接检验随机数是否大于10000
        if (Long.parseLong(nonce) > 10000) {
            throw new RuntimeException("无权限");
        }
        // 校验与当前时间不超过5分钟
        if ((System.currentTimeMillis() / 1000) - Long.parseLong(timestamp) > 60*5) {
            throw new RuntimeException("超时");
        }
        String generateSign = SignGenerator.generateSign(body, secretKeyInDb);
        if (!sign.equals(generateSign)) {
            throw new RuntimeException("无权限");
        }
        //通过
        return "POST 你的名字是：" + user.getName();
    }
}
