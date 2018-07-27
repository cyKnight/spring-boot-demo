package com.example.demo.controller;

import com.example.demo.controller.base.BaseController;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Json
 * 返回Json格式数据，多用于Ajax请求
 * Created by user on 2017/9/19.
 */
@Controller
public class UserController extends BaseController {

    private Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    @ResponseBody
//    @LoggerManager(logDescription = "22222222222222222222")
    public PageInfo<User> getUserInfo(@RequestBody(required=false) Map<String,Object> map,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {

//        User user = new User();
//        user.setUserName("base");
//        user.setPassword("base");
//        user.setDescription("base");
//        userService.insert(user);

        Page<User> userList = userService.getUserInfo(2, 2);
        PageInfo<User> userPageInfo = new PageInfo<>(userList);
        if(CollectionUtils.isEmpty(userList)){
            return null;
        }
        return userPageInfo;
    }

    @RequestMapping(value = "/getToken",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> getToken(@RequestParam("token")String token,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        Map<String,String> result = new HashMap<>();
        result.put("token",token);
        return result;
    }
}
