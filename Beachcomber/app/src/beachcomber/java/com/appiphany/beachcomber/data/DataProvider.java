package com.appiphany.beachcomber.data;

import com.appiphany.beachcomber.models.TOC;
import com.appiphany.beachcomber.models.TOCHeader;
import com.appiphany.beachcomber.util.Config;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class DataProvider {
    @SuppressWarnings("unused")
    public static List<TOCHeader> getMainList(Realm realm) {
        List<TOCHeader> data = new ArrayList<>();
        for (String header : Config.BEACHCOMBER_HEADERS) {
            RealmQuery<TOC> query = realm.where(TOC.class);
            query.equalTo("header", header);
            RealmResults<TOC> realmResults = query.sort("startPageNumber").findAll();
            TOCHeader headerItem = new TOCHeader();
            headerItem.setHeader(header);
            List<TOC> items = new ArrayList<>(realmResults);
            headerItem.setTocList(new ArrayList<>(realm.copyFromRealm(items)));
            data.add(headerItem);
        }

        return data;
    }

    public static List<TOCHeader> getDetailList(Realm realm, TOC toc) {
        return null;
    }
}
