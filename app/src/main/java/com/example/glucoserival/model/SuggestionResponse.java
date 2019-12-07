
package com.example.glucoserival.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SuggestionResponse {

    @SerializedName("data_suggestion")
    private List<DataSuggestion> dataSuggestion;
    @Expose
    private String status;

    public List<DataSuggestion> getDataSuggestion() {
        return dataSuggestion;
    }

    public void setDataSuggestion(List<DataSuggestion> dataSuggestion) {
        this.dataSuggestion = dataSuggestion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
