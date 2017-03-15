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
                headerItem.setName(header);
                headerItem.setTocList(realmResults);
                data.add(headerItem);
            }
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
