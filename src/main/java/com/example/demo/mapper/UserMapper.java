package com.example.demo.mapper;

import com.example.demo.common.MyMapper;
import com.example.demo.model.User;
import com.github.pagehelper.Page;

/**
 * Created by user on 2017/9/19.
 */

public interface UserMapper extends MyMapper<User> {
    public Page<User> findUserInfo(User user);

    public void insertMycat(User user);
}
