package com.appiphany.beachcomber;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.appiphany.beachcomber.adapter.ITocClickedListener;
import com.appiphany.beachcomber.adapter.TocItemAdapter;
import com.appiphany.beachcomber.models.TOC;
import com.appiphany.beachcomber.models.TOCHeader;
import com.artifex.mupdf.SafeAsyncTask;

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class DetailActivity extends BaseActivity implements ITocClickedListener {
    public static final String CATEGORY = "CATEGORY";

    private RecyclerView recycleView;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recycleView = (RecyclerView) findViewById(R.id.recycleView);

        recycleView.setLayoutManager(new StickyHeaderLayoutManager());
        TOC toc = (TOC) getIntent().getSerializableExtra(CATEGORY);
        new LoadDataTask(this, toc).safeExecute();
    }

    public void displayData(List<TOCHeader> data) {
        TocItemAdapter adapter = new TocItemAdapter(data, this);
        recycleView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onHeaderClicked(TOCHeader header) {

    }

    @Override
    public void onItemClicked(TOCHeader header, TOC item) {

    }

    private static class LoadDataTask extends SafeAsyncTask<Void, Void, List<TOCHeader>> {
        private WeakReference<DetailActivity> weakReference;
        private TOC toc;

        LoadDataTask(DetailActivity context, TOC toc) {
            this.weakReference = new WeakReference<>(context);
            this.toc = toc;
        }

        @Override
        protected List<TOCHeader> doInBackground(Void... params) {
            Realm realm = Realm.getInstance(GlobalApplication.getInstance().getRealmConfiguration());
            List<TOCHeader> data = new ArrayList<>();
            RealmQuery<TOC> query = realm.where(TOC.class);
            RealmResults<TOC> realmResults;
            switch (toc.getPageName()) {
                default:
                case "All":
                    realmResults = query.findAllSorted("startPageNumber");
                    break;
                case "Native":
                    realmResults = query.equalTo("type", "Native").findAllSorted("startPageNumber");
                    break;
                case "Weed":
                    realmResults = query.equalTo("type", "Weed").findAllSorted("startPageNumber");
                    break;
                case "Tree":
                    realmResults = query.contains("growth", "tree", Case.INSENSITIVE).findAllSorted("startPageNumber");
                    break;
                case "Shrub":
                    realmResults = query.contains("growth", "shrub", Case.INSENSITIVE).findAllSorted("startPageNumber");
                    break;
                case "Ground Cover":
                    realmResults = query.contains("growth", "ground cover", Case.INSENSITIVE).findAllSorted("startPageNumber");
                    break;
                case "Climber":
                    realmResults = query.contains("growth", "climber", Case.INSENSITIVE).findAllSorted("startPageNumber");
                    break;
                case "Grass":
                    realmResults = query.contains("growth", "grass", Case.INSENSITIVE).findAllSorted("startPageNumber");
                    break;
                case "Aboriginal Usage":
                    realmResults = query.contains("aboriginal", "yes", Case.INSENSITIVE).findAllSorted("startPageNumber");
                    break;
                case "Fore Dune":
                    realmResults = query.contains("location", "fore dune", Case.INSENSITIVE).findAllSorted("startPageNumber");
                    break;
                case "Mid Dune":
                    realmResults = query.contains("location", "mid dune", Case.INSENSITIVE).findAllSorted("startPageNumber");
                    break;
                case "Hind Dune":
                    realmResults = query.contains("location", "hind dune", Case.INSENSITIVE).findAllSorted("startPageNumber");
                    break;
                case "Limestone Cliffs":
                    realmResults = query.contains("location", "limestone cliffs", Case.INSENSITIVE).findAllSorted("startPageNumber");
                    break;
                case "Yellow":
                    realmResults = query.contains("colour", "yellow", Case.INSENSITIVE).findAllSorted("startPageNumber");
                    break;
                case "White":
                    realmResults = query.contains("colour", "white", Case.INSENSITIVE).findAllSorted("startPageNumber");
                    break;
                case "Grey":
                    realmResults = query.contains("colour", "grey", Case.INSENSITIVE).findAllSorted("startPageNumber");
                    break;
                case "Pink":
                    realmResults = query.contains("colour", "pink", Case.INSENSITIVE).findAllSorted("startPageNumber");
                    break;
                case "Orange":
                    realmResults = query.contains("colour", "orange", Case.INSENSITIVE).findAllSorted("startPageNumber");
                    break;
                case "Purple":
                    realmResults = query.contains("colour", "purple", Case.INSENSITIVE).findAllSorted("startPageNumber");
                    break;
                case "Blue":
                    realmResults = query.contains("colour", "blue", Case.INSENSITIVE).findAllSorted("startPageNumber");
                    break;
            }

            TOCHeader header = new TOCHeader();
            header.setHeader(toc.getPageName());
            if (realmResults != null) {
                List<TOC> items = new ArrayList<>(realmResults);
                header.setTocList(new ArrayList<>(realm.copyFromRealm(items)));
            }

            data.add(header);
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
