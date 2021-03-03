package com.example.cs125_lifetuner;

public class UserModel {
    private int id;
    private String username;
    private String password;
    private String gender;

    //constructers
    public UserModel(int id, String username, String password, String gender) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.gender = gender;
    }

    public UserModel(){

    }

    //toString


    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
