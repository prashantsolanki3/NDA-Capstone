package com.prashantsolanki.blackshift.trans.model;

/**
 * Created by prsso on 30-01-2017.
 */

public class Quote {

    protected String id;
    protected String speech;
    protected String output;

    public Quote() {
    }

    public Quote(String id, String speech, String output) {
        this.id = id;
        this.speech = speech;
        this.output = output;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

}
