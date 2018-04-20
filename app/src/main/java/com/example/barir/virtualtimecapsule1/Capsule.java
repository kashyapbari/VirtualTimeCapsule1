package com.example.barir.virtualtimecapsule1;

import java.util.HashMap;
import java.util.Map;

public class Capsule {
    private String title;
    private String to;
    private String content;

    Capsule(){}

    public Capsule(String title, String to, String content){
        this.title = title;
        this.to = to;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getTo() {
        return to;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("title",this.title);
        result.put("to",this.to);
        result.put("content",this.content);
        return result;
    }
}
