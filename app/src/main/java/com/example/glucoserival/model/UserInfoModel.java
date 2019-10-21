package com.example.glucoserival.model;

/**
 * Created by YASIN on 07,May,2019
 * Email: yasinenubd5@gmail.com
 */

public class UserInfoModel {


    private String userUniqueId;
    private String name;
    private String type;
    private String email;
    private String pass;

    public UserInfoModel() {
    }

    public UserInfoModel(String userUniqueId, String name,String type, String email, String pass) {
        this.userUniqueId=userUniqueId;
        this.name = name;
        this.type = type;
        this.email = email;
        this.pass=pass;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserUniqueId() {
        return userUniqueId;
    }

    public void setUserUniqueId(String userUniqueId) {
        this.userUniqueId = userUniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
