package com.tj.exercise.springframework.tj.config.center.test.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author: tj
 * @Date: 2022/10/30 21:28
 */
@Service
public class UserDeptService {
    @Value("${person.name")
    String panme;

    public String getName() {
        return panme;
    }
}
