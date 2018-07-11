package com.example.demo.service;

import com.example.demo.model.User;
import com.github.pagehelper.Page;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Created by user on 2017/9/19.
 */

public interface UserService {

    /**
     * 增
     * @param user
     * @return
     */

    public int insert(User user);


    public Page<User> getUserInfo(int pageNo, int pageSize);
}
