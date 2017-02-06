package com.prashantsolanki.blackshift.trans.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contents {

    @SerializedName("translation")
    @Expose
    public String translation;

    @SerializedName("text")
    @Expose
    public String text;

    @SerializedName("translated")
    @Expose
    public String translated;

    public Contents() {
    }
}