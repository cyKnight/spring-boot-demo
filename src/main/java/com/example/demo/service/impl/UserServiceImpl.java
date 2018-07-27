package com.example.demo.service.impl;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by user on 2017/9/20.
 */
@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(User user) {
//        User userD = new User();
//        userD.setId(7L);
//        userD.setCreatedTime(new Date());
//        userD.setModifiedTime(new Date());
//        userMapper.updateByPrimaryKeySelective(userD);
//        List<Long> idList = new ArrayList<>();
//        idList.add(1L);
//        idList.add(2L);
        Long id = 1L;
        userMapper.deleteByPrimaryKey(id);
        userMapper.updateByPrimaryKey(user);
        userMapper.updateByPrimaryKeySelective(user);
        return userMapper.insert(user);
    }

    @Override
    public Page<User> getUserInfo(int pageNo, int pageSize){
        PageHelper.startPage(pageNo, pageSize);
        User user = new User();
        user.setUserName("demoTest");
        userMapper.insertMycat(user);
        return null;
//        return userMapper.findUserInfo(user);
    }
}
