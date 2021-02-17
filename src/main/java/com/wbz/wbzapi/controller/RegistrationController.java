package com.wbz.wbzapi.controller;

import com.wbz.wbzapi.entity.User;
import com.wbz.wbzapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RegistrationController {
    private static final Logger LOG = Logger.getLogger(RegistrationController.class.getName());

    private final UserService userService;

    @PostMapping("/register")
    public void registerUser(@RequestBody User user) {
        LOG.info(userService.register(user).toString());
    }
}