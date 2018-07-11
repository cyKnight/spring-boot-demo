package com.example.demo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by user on 2017/9/14.
 *
 */
@RestController
@EnableAutoConfiguration
public class Example {

    @RequestMapping(value = "/")
    public String home(){
        return "Hello World!";
    }

    @RequestMapping(value = "/hello/{myName}")
    public String index(@PathVariable String myName){
        return "Hello" + myName + "!!";
    }
}
