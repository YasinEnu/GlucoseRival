
package com.example.glucoserival.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class MedicineModel {

    @SerializedName("data")
    private List<MedicineDatum> mMedicineData;
    @SerializedName("status")
    private String mStatus;

    public List<MedicineDatum> getMedicineData() {
        return mMedicineData;
    }

    public void setMedicineData(List<MedicineDatum> medicineData) {
        mMedicineData = medicineData;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
