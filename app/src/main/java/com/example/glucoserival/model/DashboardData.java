
package com.example.glucoserival.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class DashboardData implements Serializable {

    @SerializedName("total_accepted_appointment")
    private Long totalAcceptedAppointment;
    @SerializedName("total_appointment")
    private Long totalAppointment;
    @SerializedName("total_completed_appointment")
    private Long totalCompletedAppointment;
    @SerializedName("total_pending_appointment")
    private Long totalPendingAppointment;
    @SerializedName("total_rejected_appointment")
    private Long totalRejectedAppointment;

    public Long getTotalAcceptedAppointment() {
        return totalAcceptedAppointment;
    }

    public void setTotalAcceptedAppointment(Long totalAcceptedAppointment) {
        this.totalAcceptedAppointment = totalAcceptedAppointment;
    }

    public Long getTotalAppointment() {
        return totalAppointment;
    }

    public void setTotalAppointment(Long totalAppointment) {
        this.totalAppointment = totalAppointment;
    }

    public Long getTotalCompletedAppointment() {
        return totalCompletedAppointment;
    }

    public void setTotalCompletedAppointment(Long totalCompletedAppointment) {
        this.totalCompletedAppointment = totalCompletedAppointment;
    }

    public Long getTotalPendingAppointment() {
        return totalPendingAppointment;
    }

    public void setTotalPendingAppointment(Long totalPendingAppointment) {
        this.totalPendingAppointment = totalPendingAppointment;
    }

    public Long getTotalRejectedAppointment() {
        return totalRejectedAppointment;
    }

    public void setTotalRejectedAppointment(Long totalRejectedAppointment) {
        this.totalRejectedAppointment = totalRejectedAppointment;
    }

}
