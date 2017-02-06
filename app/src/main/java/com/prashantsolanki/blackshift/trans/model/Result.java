package com.prashantsolanki.blackshift.trans.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("success")
    @Expose
    public Success success;

    @SerializedName("contents")
    @Expose
    public Contents contents;

    public Error error;

    public Result() {
    }
}