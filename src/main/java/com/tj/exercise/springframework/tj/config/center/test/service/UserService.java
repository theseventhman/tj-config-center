package com.tj.exercise.springframework.tj.config.center.test.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author: tj
 * @Date: 2022/10/30 21:29
 */
@Service
public class UserService {
    @Value("${person.name}")
    String name;

    @Value("${person.age}")
    Integer age;

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
