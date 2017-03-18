package com.appiphany.beachcomber;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import com.appiphany.beachcomber.adapter.TocItemAdapter;
import com.appiphany.beachcomber.models.TOC;
import com.appiphany.beachcomber.models.TOCHeader;
import com.appiphany.beachcomber.util.Config;

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends Activity {

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recycleView = (RecyclerView) findViewById(R.id.recycleView);

        realm = Realm.getInstance(GlobalApplication.getInstance().getRealmConfiguration());

        List<TOCHeader> data = new ArrayList<>();
        if(Config.IS_BEACHCOMBER) {
            for (String header : Config.BEACHCOMBER_HEADERS) {
                RealmQuery<TOC> query = realm.where(TOC.class);
                query.equalTo("header", header);
                RealmResults<TOC> realmResults = query.findAllSorted("startPageNumber");
                TOCHeader headerItem = new TOCHeader();
                headerItem.setHeader(header);
                headerItem.setTocList(new ArrayList<>(realmResults));
                data.add(headerItem);
            }
        }else{
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
        }

        TocItemAdapter adapter = new TocItemAdapter(this, data);
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new StickyHeaderLayoutManager());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(realm != null && !realm.isClosed()){
            realm.close();
        }
    }

}
