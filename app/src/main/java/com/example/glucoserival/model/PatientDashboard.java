
package com.example.glucoserival.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PatientDashboard {

    @SerializedName("dashboard_data")
    private DashboardData mDashboardData;
    @SerializedName("next_appointment_date")
    private String next_appointment_date;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("user_info")
    private UserInfo mUserInfo;

    public DashboardData getDashboardData() {
        return mDashboardData;
    }

    public void setDashboardData(DashboardData dashboardData) {
        mDashboardData = dashboardData;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public UserInfo getUserInfo() {
        return mUserInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        mUserInfo = userInfo;
    }

    public String getNext_appointment_date() {
        return next_appointment_date;
    }

    public void setNext_appointment_date(String next_appointment_date) {
        this.next_appointment_date = next_appointment_date;
    }
}
