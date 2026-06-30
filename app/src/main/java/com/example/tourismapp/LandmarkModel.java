package com.example.tourismapp;

public class LandmarkModel {
    public String landmarkName;
    public String landmarkImage;
    public String landmarkLocation;
    public String landmarkDescription;

    public LandmarkModel(String landmarkName, String landmarkLocation, String landmarkDescription, String landmarkImage) {
        this.landmarkName = landmarkName;
        this.landmarkImage = landmarkImage;
        this.landmarkLocation = landmarkLocation;
        this.landmarkDescription = landmarkDescription;
    }

    public String getLandmarkName() {
        return landmarkName;
    }

    public void setLandmarkName(String landmarkName) {
        this.landmarkName = landmarkName;
    }

    public String getLandmarkImage() {
        return landmarkImage;
    }

    public void setLandmarkImage(String landmarkImage) {
        this.landmarkImage = landmarkImage;
    }

    public String getLandmarkLocation() {
        return landmarkLocation;
    }

    public void setLandmarkLocation(String landmarkLocation) {
        this.landmarkLocation = landmarkLocation;
    }

    public String getLandmarkDescription() {
        return landmarkDescription;
    }

    public void setGetLandmarkDescription(String landmarkDescription) {
        this.landmarkDescription = landmarkDescription;
    }
}
