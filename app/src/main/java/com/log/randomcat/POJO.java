package com.log.randomcat;

import com.google.gson.annotations.SerializedName;

public class POJO {

    @SerializedName("text")
    private String text_body;

    public String getText_body() {
        return text_body;
    }

}
