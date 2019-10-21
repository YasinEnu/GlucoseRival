
package com.example.glucoserival.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ApplyAppointmentResponse {

    @SerializedName("appointment_id")
    private String mAppointmentId;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private String mStatus;

    public String getAppointmentId() {
        return mAppointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        mAppointmentId = appointmentId;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
