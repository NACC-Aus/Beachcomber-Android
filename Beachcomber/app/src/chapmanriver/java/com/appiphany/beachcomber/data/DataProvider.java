package com.appiphany.beachcomber.data;

import com.appiphany.beachcomber.models.TOC;
import com.appiphany.beachcomber.models.TOCHeader;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class DataProvider {
    @SuppressWarnings("unused")
    public static List<TOCHeader> getMainList(Realm realm) {
        List<TOCHeader> data = new ArrayList<>();

        // all
        TOCHeader content = new TOCHeader("Table of Contents");
        content.getTocList().add(new TOC("All", "Cover.jpg"));
        data.add(content);

        //growth
        TOCHeader growth = new TOCHeader("Plant Habit");
        growth.getTocList().add(new TOC("Tree - Large Shrub", "Tree.jpg"));
        growth.getTocList().add(new TOC("Shrub", "Shrub.jpg"));
        growth.getTocList().add(new TOC("Small Shrub", "Small-shrub.jpg"));
        growth.getTocList().add(new TOC("Climber - Creeper", "Climber.Creeper.jpg"));
        growth.getTocList().add(new TOC("Grass - Herb - Sedge", "Herb.grass.sedge.jpg"));
        growth.getTocList().add(new TOC("Orchid", "Orchid.jpg"));
        data.add(growth);

        //Location
        TOCHeader location = new TOCHeader("Flowering Season");
        location.getTocList().add(new TOC("September - October - November", "Spring.jpg"));
        location.getTocList().add(new TOC("December - January - February", "Summer.jpg"));
        location.getTocList().add(new TOC("March - April - May", "Autumn.jpg"));
        location.getTocList().add(new TOC("June - July -August", "WINTER.jpg"));
        data.add(location);

        //Flower Colour
        TOCHeader flowerColour = new TOCHeader("Flower Colour");
        flowerColour.getTocList().add(new TOC("White - Cream - Grey", "White.jpg"));
        flowerColour.getTocList().add(new TOC("Yellow", "Yellow.jpg"));
        flowerColour.getTocList().add(new TOC("Orange", "Orange.jpg"));
        flowerColour.getTocList().add(new TOC("Red", "Red.jpg"));
        flowerColour.getTocList().add(new TOC("Pink", "Pink.jpg"));
        flowerColour.getTocList().add(new TOC("Mauve - Purple", "Mauve-Purple.jpg"));
        flowerColour.getTocList().add(new TOC("Blue", "Blue.jpg"));
        flowerColour.getTocList().add(new TOC("Green", "Green.jpg"));
        flowerColour.getTocList().add(new TOC("Brown", "Brown.jpg"));
        data.add(flowerColour);

        return data;
    }

    public static List<TOCHeader> getDetailList(Realm realm, TOC toc) {
        List<TOCHeader> data = new ArrayList<>();
        RealmQuery<TOC> query = realm.where(TOC.class);
        RealmResults<TOC> realmResults;
        switch (toc.getPageName()) {
            default:
            case "All":
                realmResults = query.sort("startPageNumber").findAll();
                break;
            case "Tree - Large Shrub":
                realmResults = query.contains("growthForm", "Tree").sort("startPageNumber").findAll();
                break;
            case "Shrub":
                realmResults = query.equalTo("growthForm", "Shrub").sort("startPageNumber").findAll();
                break;
            case "Small Shrub":
                realmResults = query.contains("growthForm", "Small", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Climber - Creeper":
                realmResults = query.in("growthForm", new String[]{"Climber", "Creeper"}, Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Grass - Herb - Sedge":
                realmResults = query.in("growthForm", new String[]{"Grass", "Herb", "Sedge"}, Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Orchid":
                realmResults = query.equalTo("growthForm", "Orchid", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "September - October - November":
                realmResults = query.contains("floweringTime", "Spring", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "December - January - February":
                realmResults = query.contains("floweringTime", "Summer", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "March - April - May":
                realmResults = query.contains("floweringTime", "Autumn", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "June - July -August":
                realmResults = query.contains("floweringTime", "Winter", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "White - Cream - Grey":
                realmResults = query.contains("flowerColour", "White", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Yellow":
                realmResults = query.contains("flowerColour", "Yellow", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Red":
                realmResults = query.contains("flowerColour", "Red", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Pink":
                realmResults = query.contains("flowerColour", "Pink", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Mauve - Purple":
                realmResults = query.contains("flowerColour", "Purple", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Blue":
                realmResults = query.contains("flowerColour", "Blue", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Green":
                realmResults = query.contains("flowerColour", "Green", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Brown":
                realmResults = query.contains("flowerColour", "Brown", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
        }

        TOCHeader header = new TOCHeader();
        header.setHeader(toc.getPageName());
        if (realmResults != null) {
            List<TOC> items = new ArrayList<>(realmResults);
            header.setTocList(new ArrayList<>(realm.copyFromRealm(items)));
        }

        data.add(header);

        return data;
    }
}
