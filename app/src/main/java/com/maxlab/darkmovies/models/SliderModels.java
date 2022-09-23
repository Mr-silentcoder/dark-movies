package com.maxlab.darkmovies.models;

public class SliderModels {

    private String sliderId, sliderName, sliderImage;

    public SliderModels(String sliderId, String sliderName, String sliderImage) {
        this.sliderId = sliderId;
        this.sliderName = sliderName;
        this.sliderImage = sliderImage;
    }

    public SliderModels() {
    }

    public String getSliderId() {
        return sliderId;
    }

    public void setSliderId(String sliderId) {
        this.sliderId = sliderId;
    }

    public String getSliderName() {
        return sliderName;
    }

    public void setSliderName(String sliderName) {
        this.sliderName = sliderName;
    }

    public String getSliderImage() {
        return sliderImage;
    }

    public void setSliderImage(String sliderImage) {
        this.sliderImage = sliderImage;
    }
}
