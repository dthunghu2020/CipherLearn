package com.example.sql.model;

public class Detail {
    private String useID;
    private String detailsId;
    private String day;
    private String number;
    private String content;

    public Detail(String useID, String detailsId, String day, String number, String content) {
        this.useID = useID;
        this.detailsId = detailsId;
        this.day = day;
        this.number = number;
        this.content = content;
    }

    public String getUseID() {
        return useID;
    }

    public void setUseID(String useID) {
        this.useID = useID;
    }

    public String getDetailsId() {
        return detailsId;
    }

    public void setDetailsId(String detailsId) {
        this.detailsId = detailsId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
