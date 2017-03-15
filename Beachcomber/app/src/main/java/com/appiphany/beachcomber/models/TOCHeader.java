package com.appiphany.beachcomber.models;

import io.realm.RealmResults;

public class TOCHeader {
    private String name;
    private RealmResults<TOC> tocList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmResults<TOC> getTocList() {
        return tocList;
    }

    public void setTocList(RealmResults<TOC> tocList) {
        this.tocList = tocList;
    }
}
