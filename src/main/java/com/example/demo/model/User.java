package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created by user on 2017/9/19.
 */
@Table(name = "user")
public class User extends BasePOJO{

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "description")
    private String description;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
