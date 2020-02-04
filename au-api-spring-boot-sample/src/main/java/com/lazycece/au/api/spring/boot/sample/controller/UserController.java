package com.lazycece.au.api.spring.boot.sample.controller;

import com.lazycece.au.api.spring.boot.sample.entity.dos.UserSubject;
import com.lazycece.au.api.spring.boot.sample.entity.req.LoginReq;
import com.lazycece.au.api.spring.boot.sample.response.ResponseMap;
import com.lazycece.au.api.token.SubjectContext;
import com.lazycece.au.api.token.TokenHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lazycece
 */
@RestController
@RequestMapping("/u")
@Slf4j
public class UserController {

    @Resource
    private TokenHolder tokenHolder;

    @PostMapping("/login")
    public Object login(@RequestBody LoginReq req, HttpServletResponse response) throws Exception {
        log.info("username -> {}, password = {}", req.getUsername(), req.getPassword());
        UserSubject subject = new UserSubject();
        subject.setUsername(req.getUsername());
        String token = tokenHolder.generateToken(subject);
        response.addHeader(tokenHolder.getTokenHeader(), token);
        return ResponseMap.success();
    }

    @GetMapping("/info")
    public Object getUserInfo() {
        UserSubject subject = (UserSubject) SubjectContext.getContext();
        return ResponseMap.success(subject);
    }
}
