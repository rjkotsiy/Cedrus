package com.cedrus.models;

public class Examination {

    private Integer customerId;
    private String doctor;
    private String summary;
    private String date;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {

        this.customerId = customerId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getSummary() {
        return summary;
    }

}
