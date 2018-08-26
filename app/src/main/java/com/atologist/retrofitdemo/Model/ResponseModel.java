package com.app.retrofitdemo.Model;

import java.util.ArrayList;

/**
 * Created by admin on 11/15/2016.
 */

public class ResponseModel<D> {
    private String error;

    private ArrayList<Data> data;

    private String error_code;

    private String success;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ClassPojo [error = " + error + ", data = " + data + ", error_code = " + error_code + ", success = " + success + "]";
    }
}
