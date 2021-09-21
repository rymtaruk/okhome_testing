package com.okhome.testingapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class PhotoData implements Serializable {
    private int id;
    private String url;
    private String photographer;
    @SerializedName("photographer_url")
    private String photographer_url;
    @SerializedName("avg_color")
    private String avg_color;
    private SrcPhotoData src;
}
