
package com.example.glucoserival.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DoctorInfo {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("appointment_mobile")
    private Object mAppointmentMobile;
    @SerializedName("category_id")
    private String mCategoryId;
    @SerializedName("chamber")
    private Object mChamber;
    @SerializedName("chamber_address")
    private Object mChamberAddress;
    @SerializedName("chamber_days")
    private Object mChamberDays;
    @SerializedName("date_of_birth")
    private String mDateOfBirth;
    @SerializedName("doctor_id")
    private String mDoctorId;
    @SerializedName("edu_qua")
    private Object mEduQua;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("id")
    private String mId;
    @SerializedName("mobile")
    private String mMobile;
    @SerializedName("mobile_varify_status")
    private String mMobileVarifyStatus;
    @SerializedName("name")
    private String mName;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("post")
    private String mPost;
    @SerializedName("present_hospital")
    private String mPresentHospital;
    @SerializedName("reg_date")
    private String mRegDate;
    @SerializedName("user_type")
    private String mUserType;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public Object getAppointmentMobile() {
        return mAppointmentMobile;
    }

    public void setAppointmentMobile(Object appointmentMobile) {
        mAppointmentMobile = appointmentMobile;
    }

    public String getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(String categoryId) {
        mCategoryId = categoryId;
    }

    public Object getChamber() {
        return mChamber;
    }

    public void setChamber(Object chamber) {
        mChamber = chamber;
    }

    public Object getChamberAddress() {
        return mChamberAddress;
    }

    public void setChamberAddress(Object chamberAddress) {
        mChamberAddress = chamberAddress;
    }

    public Object getChamberDays() {
        return mChamberDays;
    }

    public void setChamberDays(Object chamberDays) {
        mChamberDays = chamberDays;
    }

    public String getDateOfBirth() {
        return mDateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        mDateOfBirth = dateOfBirth;
    }

    public String getDoctorId() {
        return mDoctorId;
    }

    public void setDoctorId(String doctorId) {
        mDoctorId = doctorId;
    }

    public Object getEduQua() {
        return mEduQua;
    }

    public void setEduQua(Object eduQua) {
        mEduQua = eduQua;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

    public String getMobileVarifyStatus() {
        return mMobileVarifyStatus;
    }

    public void setMobileVarifyStatus(String mobileVarifyStatus) {
        mMobileVarifyStatus = mobileVarifyStatus;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getPost() {
        return mPost;
    }

    public void setPost(String post) {
        mPost = post;
    }

    public String getPresentHospital() {
        return mPresentHospital;
    }

    public void setPresentHospital(String presentHospital) {
        mPresentHospital = presentHospital;
    }

    public String getRegDate() {
        return mRegDate;
    }

    public void setRegDate(String regDate) {
        mRegDate = regDate;
    }

    public String getUserType() {
        return mUserType;
    }

    public void setUserType(String userType) {
        mUserType = userType;
    }

}
