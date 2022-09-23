package com.maxlab.darkmovies.models;

public class SummaryModel {

    String summaryId, summary;

    public SummaryModel(String summaryId, String summary) {
        this.summaryId = summaryId;
        this.summary = summary;
    }

    public SummaryModel() {
    }

    public String getSummaryId() {
        return summaryId;
    }

    public void setSummaryId(String summaryId) {
        this.summaryId = summaryId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
