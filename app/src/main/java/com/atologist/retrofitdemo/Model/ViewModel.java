package com.app.retrofitdemo.Model;

/**
 * Created by admin on 11/17/2016.
 */

public class ViewModel {
    private static ViewModel dataObject = null;

    private String firstname;
    private String lastname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public ViewModel() {

    }
}
