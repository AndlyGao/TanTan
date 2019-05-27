package com.xuyijie.module_lib.entity;

public class UserStateBean {

    /**
     * login : true
     * online : false
     */

    public boolean login;
    public boolean online;

    @Override
    public String toString() {
        return "UserStateBean{" +
                "login=" + login +
                ", online=" + online +
                '}';
    }
}