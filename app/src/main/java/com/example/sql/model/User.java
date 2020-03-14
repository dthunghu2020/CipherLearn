package com.example.sql.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String useID;
    private String userName;
    private String phone;

    public User(String useID, String userName, String phone) {
        this.useID = useID;
        this.userName = userName;
        this.phone = phone;
    }

    public String getUseID() {
        return useID;
    }

    public void setUseID(String useID) {
        this.useID = useID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
