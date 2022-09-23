package com.maxlab.darkmovies.models;

public class DetailsModel {

    String detailsId, detailsName, detailsValue;

    public DetailsModel(String detailsId, String detailsName, String detailsValue) {
        this.detailsId = detailsId;
        this.detailsName = detailsName;
        this.detailsValue = detailsValue;
    }

    public DetailsModel() {
    }

    public String getDetailsId() {
        return detailsId;
    }

    public void setDetailsId(String detailsId) {
        this.detailsId = detailsId;
    }

    public String getDetailsName() {
        return detailsName;
    }

    public void setDetailsName(String detailsName) {
        this.detailsName = detailsName;
    }

    public String getDetailsValue() {
        return detailsValue;
    }

    public void setDetailsValue(String detailsValue) {
        this.detailsValue = detailsValue;
    }
}
