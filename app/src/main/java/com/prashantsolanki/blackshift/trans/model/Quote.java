package com.prashantsolanki.blackshift.trans.model;

/**
 *
 * Created by prsso on 30-01-2017.
 */

public class Quote {

    private String id;
    private String speech;
    private String quote;

    public Quote(){}

    public Quote(String id, String speech, String quote) {
        this.id = id;
        this.speech = speech;
        this.quote = quote;
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

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

}
