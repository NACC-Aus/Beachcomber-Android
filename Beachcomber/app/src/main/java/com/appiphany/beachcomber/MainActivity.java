package com.appiphany.beachcomber;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.appiphany.beachcomber.adapter.ITocClickedListener;
import com.appiphany.beachcomber.adapter.TocItemAdapter;
import com.appiphany.beachcomber.data.DataProvider;
import com.appiphany.beachcomber.models.TOC;
import com.appiphany.beachcomber.models.TOCHeader;
import com.appiphany.beachcomber.util.Config;
import com.appiphany.beachcomber.util.FileUtils;
import com.artifex.mupdf.SafeAsyncTask;

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.lang.ref.WeakReference;
import java.util.List;

import io.realm.Realm;

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
            List<TOCHeader> data = DataProvider.getMainList(realm);
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
