
package com.example.glucoserival.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Datum {

    @SerializedName("date")
    private String mDate;
    @SerializedName("id")
    private String mId;
    @SerializedName("user_id")
    private String mUserId;
    @SerializedName("value_of_glucose")
    private String mValueOfGlucose;

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getValueOfGlucose() {
        return mValueOfGlucose;
    }

    public void setValueOfGlucose(String valueOfGlucose) {
        mValueOfGlucose = valueOfGlucose;
    }

}
