package com.appiphany.beachcomber;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.appiphany.beachcomber.adapter.ITocClickedListener;
import com.appiphany.beachcomber.adapter.TocItemAdapter;
import com.appiphany.beachcomber.models.TOC;
import com.appiphany.beachcomber.models.TOCHeader;
import com.appiphany.beachcomber.util.Config;
import com.appiphany.beachcomber.util.FileUtils;
import com.artifex.mupdf.SafeAsyncTask;

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends BaseActivity implements ITocClickedListener {
    private RecyclerView recycleView;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recycleView = findViewById(R.id.recycleView);
        recycleView.setLayoutManager(new StickyHeaderLayoutManager());

        new LoadDataTask(this).safeExecute();
        initFile();
    }

    private void initFile(){
        if(!verifyPermissionGranted(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE})){
            checkForPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
            return;
        }

        String pdfPath = FileUtils.getPath(Config.PDF_FILE_NAME);
        if(!FileUtils.isExist(pdfPath)){
            FileUtils.copyFromAsset(this, Config.PDF_FILE_NAME);
        }
    }

    @Override
    protected void executeTaskAfterPermission(String[] permissions) {
        initFile();
    }

    @Override
    public void onHeaderClicked(TOCHeader header) {

    }

    @Override
    public void onItemClicked(TOCHeader header, TOC item) {
        if (!Config.IS_BEACHCOMBER) {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.CATEGORY, item);
            startActivity(intent);
        }else{
            String pdfFilePath = FileUtils.getPath(Config.PDF_FILE_NAME);
            FileUtils.viewPdf(this, pdfFilePath, (int)item.getStartPageNumber());
        }
    }

    private void displayData(List<TOCHeader> data) {
        TocItemAdapter adapter = new TocItemAdapter(data, this);
        recycleView.setAdapter(adapter);
    }

    private static class LoadDataTask extends SafeAsyncTask<Void, Void, List<TOCHeader>> {
        private WeakReference<MainActivity> weakReference;

        LoadDataTask(MainActivity context) {
            this.weakReference = new WeakReference<>(context);
        }

        @Override
        protected List<TOCHeader> doInBackground(Void... params) {
            Realm realm = Realm.getInstance(GlobalApplication.getInstance().getRealmConfiguration());
            List<TOCHeader> data = new ArrayList<>();
            if (Config.IS_BEACHCOMBER) {
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
            } else {
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

            realm.close();
            return data;
        }

        @Override
        protected void onPostExecute(List<TOCHeader> tocHeaders) {
            if (weakReference.get() != null) {
                weakReference.get().displayData(tocHeaders);
            }
        }
    }
}
