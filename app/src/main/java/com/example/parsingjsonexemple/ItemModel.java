package com.example.parsingjsonexemple;

public class ItemModel {
    String id;
    String name;
    String email;
    String job;
    String phone;
    String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setPhone(String phone){
        this.phone=phone;
    }
    public String getPhone(){
        return phone;
    }

}
