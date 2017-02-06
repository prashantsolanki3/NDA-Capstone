package com.prashantsolanki.blackshift.trans.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Success {

    public Success() {
    }

    @SerializedName("total")
    @Expose
    public Integer total;

}