
package com.example.glucoserival.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DoctorCategory {

    @SerializedName("categorys")
    private List<Category> mCategorys;
    @SerializedName("status")
    private String mStatus;

    public List<Category> getCategorys() {
        return mCategorys;
    }

    public void setCategorys(List<Category> categorys) {
        mCategorys = categorys;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
