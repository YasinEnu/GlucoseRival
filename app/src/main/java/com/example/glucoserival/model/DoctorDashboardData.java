
package com.example.glucoserival.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DoctorDashboardData {

    @SerializedName("dashboard_data")
    private DashboardData dashboardData;
    @SerializedName("pending_appointment_list")
    private List<PendingAppointmentList> pendingAppointmentList;
    @Expose
    private String status;
    @SerializedName("todays_appointment_list")
    private List<Object> todaysAppointmentList;
    @SerializedName("tomorrow_date")
    private String tomorrowDate;
    @SerializedName("tomorrows_appointment_list")
    private List<Object> tomorrowsAppointmentList;

    public DashboardData getDashboardData() {
        return dashboardData;
    }

    public void setDashboardData(DashboardData dashboardData) {
        this.dashboardData = dashboardData;
    }


    public List<PendingAppointmentList> getPendingAppointmentList() {
        return pendingAppointmentList;
    }

    public void setPendingAppointmentList(List<PendingAppointmentList> pendingAppointmentList) {
        this.pendingAppointmentList = pendingAppointmentList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Object> getTodaysAppointmentList() {
        return todaysAppointmentList;
    }

    public void setTodaysAppointmentList(List<Object> todaysAppointmentList) {
        this.todaysAppointmentList = todaysAppointmentList;
    }

    public String getTomorrowDate() {
        return tomorrowDate;
    }

    public void setTomorrowDate(String tomorrowDate) {
        this.tomorrowDate = tomorrowDate;
    }

    public List<Object> getTomorrowsAppointmentList() {
        return tomorrowsAppointmentList;
    }

    public void setTomorrowsAppointmentList(List<Object> tomorrowsAppointmentList) {
        this.tomorrowsAppointmentList = tomorrowsAppointmentList;
    }

}
