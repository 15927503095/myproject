package com.example.writetext.entity;

import java.util.List;

public class MeasApiResultEntity {


    private boolean success;
    private int statusCode;
    private String errorMessage;
    private int totalCounts;
    private List<MeasBean> resultList;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(int totalCounts) {
        this.totalCounts = totalCounts;
    }

    public List<MeasBean> getResultList() {
        return resultList;
    }

    public void setResultList(List<MeasBean> resultList) {
        this.resultList = resultList;
    }
}
