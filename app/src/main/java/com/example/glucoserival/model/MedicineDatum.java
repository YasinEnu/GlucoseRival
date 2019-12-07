
package com.example.glucoserival.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class MedicineDatum {

    @SerializedName("appointment_id")
    private String mAppointmentId;
    @SerializedName("before_or_after_eat")
    private String mBeforeOrAfterEat;
    @SerializedName("date")
    private String mDate;
    @SerializedName("how_many_days")
    private String mHowManyDays;
    @SerializedName("id")
    private String mId;
    @SerializedName("medicine_name")
    private String mMedicineName;
    @SerializedName("morning")
    private String mMorning;
    @SerializedName("night")
    private String mNight;
    @SerializedName("noon")
    private String mNoon;
    @SerializedName("user_id")
    private String mUserId;

    public String getAppointmentId() {
        return mAppointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        mAppointmentId = appointmentId;
    }

    public String getBeforeOrAfterEat() {
        return mBeforeOrAfterEat;
    }

    public void setBeforeOrAfterEat(String beforeOrAfterEat) {
        mBeforeOrAfterEat = beforeOrAfterEat;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getHowManyDays() {
        return mHowManyDays;
    }

    public void setHowManyDays(String howManyDays) {
        mHowManyDays = howManyDays;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getMedicineName() {
        return mMedicineName;
    }

    public void setMedicineName(String medicineName) {
        mMedicineName = medicineName;
    }

    public String getMorning() {
        return mMorning;
    }

    public void setMorning(String morning) {
        mMorning = morning;
    }

    public String getNight() {
        return mNight;
    }

    public void setNight(String night) {
        mNight = night;
    }

    public String getNoon() {
        return mNoon;
    }

    public void setNoon(String noon) {
        mNoon = noon;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
