package com.appiphany.beachcomber.models;

import java.io.Serializable;
import java.util.ArrayList;

public class TOCHeader implements Serializable{
    private String header;
    private ArrayList<TOC> tocList = new ArrayList<>();

    public TOCHeader() {
    }

    public TOCHeader(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public ArrayList<TOC> getTocList() {
        return tocList;
    }

    public void setTocList(ArrayList<TOC> tocList) {
        this.tocList = tocList;
    }
}
