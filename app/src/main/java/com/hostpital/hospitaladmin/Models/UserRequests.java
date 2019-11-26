package com.hostpital.hospitaladmin.Models;

/**
 * Created by Arbab on 8/7/2019.
 */

public class UserRequests {

    private String id;
    private String medical_name;
    private String medical_name_specialties;
    private String address;
    private String price;
    private String start_time;
    private String end_time;


    private String request_admin_name;
    private String request_admin_phone;


    private String user_address;
    private String job_title;
    private String field;
    private String registration_number;
    private String graduation_date;
    private String years_of_experience;
    private String cv;

    public UserRequests() {

    }

    public UserRequests(String medical_name, String medical_name_specialties, String address, String price, String start_time, String end_time, String request_admin_name, String request_admin_phone, String id, String user_address, String job_title, String field, String registration_number, String graduation_date, String years_of_experience, String cv) {
        this.user_address = user_address;
        this.job_title = job_title;
        this.field = field;
        this.registration_number = registration_number;
        this.graduation_date = graduation_date;
        this.years_of_experience = years_of_experience;
        this.cv = cv;


        this.medical_name = medical_name;
        this.medical_name_specialties = medical_name_specialties;
        this.address = address;
        this.price = price;
        this.start_time = start_time;
        this.end_time = end_time;
        this.request_admin_name = request_admin_name;
        this.request_admin_phone = request_admin_phone;
        this.id = id;
    }

    public String getMedical_name() {
        return medical_name;
    }

    public String getMedical_name_specialties() {
        return medical_name_specialties;
    }

    public String getAddress() {
        return address;
    }

    public String getPrice() {
        return price;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getRequest_admin_name() {
        return request_admin_name;
    }

    public String getRequest_admin_phone() {
        return request_admin_phone;
    }

    public String getId() {
        return id;
    }

    public void setMedical_name(String medical_name) {
        this.medical_name = medical_name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMedical_name_specialties(String medical_name_specialties) {
        this.medical_name_specialties = medical_name_specialties;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setRequest_admin_name(String request_admin_name) {
        this.request_admin_name = request_admin_name;
    }

    public void setRequest_admin_phone(String request_admin_phone) {
        this.request_admin_phone = request_admin_phone;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
    }

    public String getGraduation_date() {
        return graduation_date;
    }

    public void setGraduation_date(String graduation_date) {
        this.graduation_date = graduation_date;
    }

    public String getYears_of_experience() {
        return years_of_experience;
    }

    public void setYears_of_experience(String years_of_experience) {
        this.years_of_experience = years_of_experience;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }
}
