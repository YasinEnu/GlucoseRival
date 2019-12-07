
package com.example.glucoserival.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SuggestionModel {

    @Expose
    private String status;
    @SerializedName("data")
    private List<SuggestionDatum> suggestionData;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SuggestionDatum> getSuggestionData() {
        return suggestionData;
    }

    public void setSuggestionData(List<SuggestionDatum> suggestionData) {
        this.suggestionData = suggestionData;
    }

}
