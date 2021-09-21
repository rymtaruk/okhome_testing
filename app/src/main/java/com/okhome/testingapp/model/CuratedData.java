package com.okhome.testingapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CuratedData implements Serializable {
    private int page;
    @SerializedName("per_page")
    private int perPage;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("next_page")
    private String nextPage;
    @SerializedName("prev_page")
    private String prevPage;
    private List<PhotoData> photos;
}
