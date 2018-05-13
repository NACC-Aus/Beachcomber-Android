package com.appiphany.beachcomber.data;

import com.appiphany.beachcomber.models.TOC;
import com.appiphany.beachcomber.models.TOCHeader;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class DataProvider {
    @SuppressWarnings("unused")
    public static List<TOCHeader> getMainList(Realm realm) {
        List<TOCHeader> data = new ArrayList<>();
        // all
        TOCHeader content = new TOCHeader("Table of Contents");
        content.getTocList().add(new TOC("All", "cover.jpg"));
        data.add(content);

        // type
        TOCHeader type = new TOCHeader("Type");
        type.getTocList().add(new TOC("Native", "Native Icon.jpg"));
        type.getTocList().add(new TOC("Weed", "Weed Icon.jpg"));
        data.add(type);

        //growth
        TOCHeader growth = new TOCHeader("Growth Habit");
        growth.getTocList().add(new TOC("Tree", "tree.png"));
        growth.getTocList().add(new TOC("Shrub", "Shrub Icon.jpg"));
        growth.getTocList().add(new TOC("Ground Cover", "Ground Cover Icon.jpg"));
        growth.getTocList().add(new TOC("Climber", "Climber Icon.jpg"));
        growth.getTocList().add(new TOC("Grass", "Grass Icon.jpg"));
        data.add(growth);

        //aboriginal
        TOCHeader aboriginal = new TOCHeader("Aboriginal Usage");
        aboriginal.getTocList().add(new TOC("Aboriginal Usage", "Aboriginal usage Icon.jpg"));
        data.add(aboriginal);

        //Location
        TOCHeader location = new TOCHeader("Location");
        location.getTocList().add(new TOC("Fore Dune", "Fore Dune Icon.jpg"));
        location.getTocList().add(new TOC("Mid Dune", "Mid Dune.jpg"));
        location.getTocList().add(new TOC("Hind Dune", "Hind Dune Icon.jpg"));
        location.getTocList().add(new TOC("Limestone Cliffs", "Limestone Cliffs Icon.jpg"));
        data.add(location);

        //Flower Colour
        TOCHeader flowerColour = new TOCHeader("Flower Colour");
        flowerColour.getTocList().add(new TOC("Yellow", "Flower Yellow Icon.jpg"));
        flowerColour.getTocList().add(new TOC("White", "Flower White Icon.jpg"));
        flowerColour.getTocList().add(new TOC("Grey", "Flower Grey Icon.jpg"));
        flowerColour.getTocList().add(new TOC("Pink", "Flower Pink Icon.jpg"));
        flowerColour.getTocList().add(new TOC("Orange", "Flower Orange Icon.jpg"));
        flowerColour.getTocList().add(new TOC("Purple", "Flower Purple Icon.jpg"));
        flowerColour.getTocList().add(new TOC("Blue", "Flower Blue Icon.jpg"));
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
            case "Native":
                realmResults = query.equalTo("type", "Native").sort("startPageNumber").findAll();
                break;
            case "Weed":
                realmResults = query.equalTo("type", "Weed").sort("startPageNumber").findAll();
                break;
            case "Tree":
                realmResults = query.contains("growth", "tree", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Shrub":
                realmResults = query.contains("growth", "shrub", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Ground Cover":
                realmResults = query.contains("growth", "ground cover", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Climber":
                realmResults = query.contains("growth", "climber", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Grass":
                realmResults = query.contains("growth", "grass", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Aboriginal Usage":
                realmResults = query.contains("aboriginal", "yes", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Fore Dune":
                realmResults = query.contains("location", "fore dune", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Mid Dune":
                realmResults = query.contains("location", "mid dune", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Hind Dune":
                realmResults = query.contains("location", "hind dune", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Limestone Cliffs":
                realmResults = query.contains("location", "limestone cliffs", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Yellow":
                realmResults = query.contains("colour", "yellow", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "White":
                realmResults = query.contains("colour", "white", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Grey":
                realmResults = query.contains("colour", "grey", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Pink":
                realmResults = query.contains("colour", "pink", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Orange":
                realmResults = query.contains("colour", "orange", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Purple":
                realmResults = query.contains("colour", "purple", Case.INSENSITIVE).sort("startPageNumber").findAll();
                break;
            case "Blue":
                realmResults = query.contains("colour", "blue", Case.INSENSITIVE).sort("startPageNumber").findAll();
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
