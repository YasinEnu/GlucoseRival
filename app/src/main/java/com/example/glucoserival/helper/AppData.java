package com.example.glucoserival.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by YASIN on 07,May,2019
 * Email: yasinenubd5@gmail.com
 */

public class AppData {
    private static String TAG = AppData.class.getSimpleName();
    private static final String PREF_NAME = "GlucoseRival";
    private static final String KEY_USER_ID = "UserId";
    private static final String KEY_USER_TYPE = "UserType";
    private static final String KEY_USER_NAME = "UserName";
    private static final String KEY_USER_DOB = "dateOfBirth";
    private static final String KEY_USER_ADDRESS = "UserAddress";
    private static final String KEY_USER_PHONE = "UserPhoneNo";
    private static final String KEY_USER_PIC = "UserPic";
    private static final String KEY_USER_EMAIL = "UserEmail";
    private static final String KEY_IS_MOBILE_VERIFIED = "isMobileVerified";
    private static final String KEY_TOTAL_PAID = "TotalPay";
    private static final String KEY_TOTAL_DUE = "TotalDue";
    private static final String KEY_PROFILE_IMAGE = "profileImage";

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;


    public AppData(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public String getUserID() {
        return pref.getString(KEY_USER_ID, "null");
    }

    public void setUserID(String userID) {

        editor.putString(KEY_USER_ID, userID);
        editor.commit();
        Log.d(TAG, "User Id :" + userID);
    }

    public String getUserType() {
        return pref.getString(KEY_USER_TYPE, "null");
    }

    public void setUserType(String userType) {

        editor.putString(KEY_USER_TYPE, userType);
        editor.commit();
        Log.d(TAG, "User Type :" + userType);
    }

    public String getUserName() {
        return pref.getString(KEY_USER_NAME, "null");
    }

    public void setUserName(String userName) {

        editor.putString(KEY_USER_NAME, userName);
        editor.commit();
        Log.d(TAG, "User Name :" + userName);
    }

    public String getUserAddress() {
        return pref.getString(KEY_USER_ADDRESS, "null");
    }

    public void setUserAddress(String userAddress) {

        editor.putString(KEY_USER_ADDRESS, userAddress);
        editor.commit();
        Log.d(TAG, "User Address :" + userAddress);
    }

    public String getUserPhone() {
        return pref.getString(KEY_USER_PHONE, "null");
    }

    public void setUserPhone(String userPhone) {

        editor.putString(KEY_USER_PHONE, userPhone);
        editor.commit();
        Log.d(TAG, "User Phone :" + userPhone);
    }

    public boolean isMobileVerified() {
        return pref.getBoolean(KEY_IS_MOBILE_VERIFIED,false);
    }

    public void setMobileVerificationStatus(boolean totalConsumer) {

        editor.putBoolean(KEY_IS_MOBILE_VERIFIED, totalConsumer);
        editor.commit();
        Log.d(TAG, "verified :" + totalConsumer);
    }

    public String getTotalPaid() {
        return pref.getString(KEY_TOTAL_PAID, "null");
    }

    public void setTotalPaid(String totalPaid) {

        editor.putString(KEY_TOTAL_PAID, totalPaid);
        editor.commit();
        Log.d(TAG, "Paid :" + totalPaid);
    }

    public String getTotalDue() {
        return pref.getString(KEY_TOTAL_DUE, "null");
    }

    public void setTotalDue(String totalDue) {

        editor.putString(KEY_TOTAL_DUE, totalDue);
        editor.commit();
        Log.d(TAG, "User Pic :" + totalDue);
    }

    public String getUserPic() {
        return pref.getString(KEY_USER_PIC, "null");
    }

    public void setUserPic(String userPic) {

        editor.putString(KEY_USER_PIC, userPic);
        editor.commit();
        Log.d(TAG, "User Pic :" + userPic);
    }

    public String getUserEmail() {
        return pref.getString(KEY_USER_EMAIL, "null");
    }

    public void setUserEmail(String userEmail) {

        editor.putString(KEY_USER_EMAIL, userEmail);
        editor.commit();
        Log.d(TAG, "User Email :" + userEmail);
    }
    public String getUserDOB() {
        return pref.getString(KEY_USER_DOB, "null");
    }

    public void setUserDOB(String dateOfBirth) {

        editor.putString(KEY_USER_DOB, dateOfBirth);
        editor.commit();
        Log.d(TAG, "User dateOfBirth :" + dateOfBirth);
    }

    public String getProfileImage() {
        return pref.getString(KEY_PROFILE_IMAGE, "null");
    }

    public void setPRofileImage(String uriString) {

        editor.putString(KEY_PROFILE_IMAGE, uriString);
        editor.commit();
        Log.d(TAG, "Profile_Image :" + uriString);
    }



//    SharedPreferences pref;
//
//    SharedPreferences.Editor editor;
//    Context _context;
//
//
//    int PRIVATE_MODE = 0;
//
//
//    private static final String PREF_NAME = "GlucoseRival";
//    private static final String KEY_USER_UNIQUE_ID="userUniqueId";
//    private static final String KEY_IS_SET_UP="isSetUp";
//
//    private static final String APP_LANGUAGE = "en";
//
//
//
//    public AppData(Context context) {
//        this._context = context;
//        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
//        editor = pref.edit();
//    }
//
//
//
//    public void setUserId(String userId) {
//        editor.putString(KEY_USER_UNIQUE_ID, userId);
//        editor.commit();
//        Log.d(TAG, "USER_ID modified!"+" ###_ID="+userId);
//    }
//
//    public void setUp(boolean isSetUp){
//        editor.putBoolean(KEY_IS_SET_UP,isSetUp);
//        editor.commit();
//        Log.d(TAG, "SetUp session modified!"+" ### ID="+isSetUp);
//    }
//
//
//    public boolean isSetUp(){
//        return pref.getBoolean(KEY_IS_SET_UP, false);
//    }
//    public String getUserId(){
//        return pref.getString(KEY_USER_UNIQUE_ID, null);
//    }
//
//    public void setAppLanguage(String mLanguage) {
//        editor.putString(APP_LANGUAGE, mLanguage);
//        editor.commit();
//    }
//    public String getAppLanguage() {
//        return pref.getString(APP_LANGUAGE, "unknown");
//    }

}
