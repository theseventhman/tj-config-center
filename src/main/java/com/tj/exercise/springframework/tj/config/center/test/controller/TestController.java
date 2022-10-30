package com.tj.exercise.springframework.tj.config.center.test.controller;

import com.tj.exercise.springframework.tj.config.center.test.service.UserDeptService;
import com.tj.exercise.springframework.tj.config.center.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: tj
 * @Date: 2022/10/30 21:30
 */
@RestController
public class TestController {
    @Autowired
    UserService userService;

    @Autowired
    UserDeptService userDeptService;

    @GetMapping("name")
    public String getUserName(){
       return "name: " +userService.getName()+"\r\n age:"+userService.getAge();
    }

    @GetMapping("name2")
    public String getUserDeptName(){
        return "name: " +userDeptService.getName();

    }
}
