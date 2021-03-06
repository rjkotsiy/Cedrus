package com.cedrus.models;

import java.io.File;
import java.util.List;

public class Examination {

    private Integer recordId;
    private Integer customerId;
    private String doctor;
    private String summary;
    private String date;
    private String nextExaminationDateTime;
    private List<File> attachments;
    private Integer attachmentId;

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    public List<File> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<File> attachments) {
        this.attachments = attachments;
    }

    public String getNextExaminationDateTime() {
        return nextExaminationDateTime;
    }

    public void setNextExaminationDateTime(String nextExaminationDateTime) {
        this.nextExaminationDateTime = nextExaminationDateTime;
    }

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
