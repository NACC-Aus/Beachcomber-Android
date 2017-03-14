package com.appiphany.beachcomber.models;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Text extends RealmObject {
    private long pageNumber;
    @Required
    private String pageText;

    public long getPagenumber() { return pageNumber; }

    public void setPagenumber(long pageNumber) { this.pageNumber = pageNumber; } 

    public String getPagetext() { return pageText; }

    public void setPagetext(String pageText) { this.pageText = pageText; } 

}
