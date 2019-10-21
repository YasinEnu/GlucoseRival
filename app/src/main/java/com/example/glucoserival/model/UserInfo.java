
package com.example.glucoserival.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class UserInfo {

    @SerializedName("age")
    private Long mAge;
    @SerializedName("date_of_birth")
    private String mDateOfBirth;
    @SerializedName("mobile")
    private String mMobile;
    @SerializedName("name")
    private String mName;

    public Long getAge() {
        return mAge;
    }

    public void setAge(Long age) {
        mAge = age;
    }

    public String getDateOfBirth() {
        return mDateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        mDateOfBirth = dateOfBirth;
    }

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
