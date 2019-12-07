
package com.example.glucoserival.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PatientDashboard implements Serializable {

    public PatientDashboard() {
    }

    @SerializedName("accepted_appointment")
    private List<AcceptedAppointment> acceptedAppointment;
    @SerializedName("completed_appointment")
    private List<AcceptedAppointment> completedAppointment;
    @SerializedName("dashboard_data")
    private DashboardData dashboardData;
    @SerializedName("next_appointment_date")
    private String nextAppointmentDate;
    @SerializedName("last_glucose_level")
    private String last_glucose_level;
    @SerializedName("pending_appointment")
    private List<PendingAppointment> pendingAppointment;
    @Expose
    private String status;
    @SerializedName("todays_appointment")
    private List<Object> todaysAppointment;
    @SerializedName("user_info")
    private UserInfo userInfo;

    public List<AcceptedAppointment> getAcceptedAppointment() {
        return acceptedAppointment;
    }

    public void setAcceptedAppointment(List<AcceptedAppointment> acceptedAppointment) {
        this.acceptedAppointment = acceptedAppointment;
    }

    public List<AcceptedAppointment> getCompletedAppointment() {
        return completedAppointment;
    }

    public void setCompletedAppointment(List<AcceptedAppointment> completedAppointment) {
        this.completedAppointment = completedAppointment;
    }

    public DashboardData getDashboardData() {
        return dashboardData;
    }

    public void setDashboardData(DashboardData dashboardData) {
        this.dashboardData = dashboardData;
    }

    public String getNextAppointmentDate() {
        return nextAppointmentDate;
    }

    public void setNextAppointmentDate(String nextAppointmentDate) {
        this.nextAppointmentDate = nextAppointmentDate;
    }

    public List<PendingAppointment> getPendingAppointment() {
        return pendingAppointment;
    }

    public void setPendingAppointment(List<PendingAppointment> pendingAppointment) {
        this.pendingAppointment = pendingAppointment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Object> getTodaysAppointment() {
        return todaysAppointment;
    }

    public void setTodaysAppointment(List<Object> todaysAppointment) {
        this.todaysAppointment = todaysAppointment;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getLast_glucose_level() {
        return last_glucose_level;
    }

    public void setLast_glucose_level(String last_glucose_level) {
        this.last_glucose_level = last_glucose_level;
    }

}
