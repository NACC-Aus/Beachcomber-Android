package com.appiphany.beachcomber.models;

import java.io.Serializable;

import io.realm.RealmObject;

@SuppressWarnings("unused")
public class TOC extends RealmObject implements Serializable{
    private String pageName;
    private long startPageNumber;
    private long endPageNumber;
    private String thumb;
    private String type;
    private String growth;
    private String aboriginal;
    private String location;
    private String colour;
    private String header;
    private String growthForm;
    private String floweringTime;
    private String flowerColour;
    private String family;

    public TOC() {
    }

    public TOC(String pageName, String thumb) {
        this.pageName = pageName;
        this.thumb = thumb;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public long getStartPageNumber() {
        return startPageNumber;
    }

    public void setStartPageNumber(long startPageNumber) {
        this.startPageNumber = startPageNumber;
    }

    public long getEndPageNumber() {
        return endPageNumber;
    }

    public void setEndPageNumber(long endPageNumber) {
        this.endPageNumber = endPageNumber;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGrowth() {
        return growth;
    }

    public void setGrowth(String growth) {
        this.growth = growth;
    }

    public String getAboriginal() {
        return aboriginal;
    }

    public void setAboriginal(String aboriginal) {
        this.aboriginal = aboriginal;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getGrowthForm() {
        return growthForm;
    }

    public void setGrowthForm(String growthForm) {
        this.growthForm = growthForm;
    }

    public String getFloweringTime() {
        return floweringTime;
    }

    public void setFloweringTime(String floweringTime) {
        this.floweringTime = floweringTime;
    }

    public String getFlowerColour() {
        return flowerColour;
    }

    public void setFlowerColour(String flowerColour) {
        this.flowerColour = flowerColour;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }
}
