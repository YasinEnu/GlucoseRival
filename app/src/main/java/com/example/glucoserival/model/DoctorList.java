
package com.example.glucoserival.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DoctorList {

    @SerializedName("doctor_info")
    private List<DoctorInfo> doctorInfo;
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    public List<DoctorInfo> getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorInfo(List<DoctorInfo> doctorInfo) {
        this.doctorInfo = doctorInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
