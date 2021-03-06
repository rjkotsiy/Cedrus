package com.cedrus.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {

    private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String birthday;
    private String registration_date;

    private String gender;
    private String doctor;
    private String direction;
    private String nextExaminationDateTime;

    public String getNextExaminationDateTime() {
        if (nextExaminationDateTime == null || nextExaminationDateTime.isEmpty()) {
            nextExaminationDateTime = "N/A";
        }
        return nextExaminationDateTime;
    }

    public void setNextExaminationDateTime(String nextExaminationDateTime) {
        this.nextExaminationDateTime = nextExaminationDateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public ObjectProperty getCustomer() {
        return new SimpleObjectProperty(this);
    }

    public SimpleStringProperty getNameProperty() {
        return new SimpleStringProperty(firstName + " " + lastName);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public SimpleStringProperty getPhoneProperty() {
        return new SimpleStringProperty(phone);
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(String registration_date) {
        this.registration_date = registration_date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDoctor() {
        if (doctor == null) {
            doctor = "";
        }
        return doctor;
    }

    public String getDirection() {
        if (direction == null) {
            direction = "";
        }
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

}
