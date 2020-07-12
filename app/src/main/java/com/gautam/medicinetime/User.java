package com.gautam.medicinetime;

public class User {
    private String fName;
    private String Username;
    private String email;
    private String Mobile;
    private String Location;
    private String type;

    public User() {
    }

    public User(String fName, String username, String email, String mobile, String location, String type) {
        this.fName = fName;
        Username = username;
        this.email = email;
        Mobile = mobile;
        Location = location;
        this.type = type;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
