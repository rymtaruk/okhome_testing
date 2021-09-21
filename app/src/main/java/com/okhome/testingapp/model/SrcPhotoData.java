package com.okhome.testingapp.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class SrcPhotoData implements Serializable {
    private String original;
    private String landscape;
    private String tiny;
}
