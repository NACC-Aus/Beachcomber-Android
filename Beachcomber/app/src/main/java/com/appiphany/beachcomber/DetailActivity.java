package com.appiphany.beachcomber;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

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

public class DetailActivity extends BaseActivity implements ITocClickedListener {
    public static final String CATEGORY = "CATEGORY";

    private RecyclerView recycleView;
    private TocItemAdapter adapter;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recycleView = findViewById(R.id.recycleView);

        recycleView.setLayoutManager(new StickyHeaderLayoutManager());
        TOC toc = (TOC) getIntent().getSerializableExtra(CATEGORY);
        setTitle(toc.getPageName());
        TextView tvTitle = findViewById(R.id.tvTitle);
        if(!TextUtils.isEmpty(toc.getPageName())) {
            tvTitle.setText(toc.getPageName());
        }

        new LoadDataTask(this, toc).safeExecute();
    }

    public void displayData(List<TOCHeader> data) {
        adapter = new TocItemAdapter(data, this);
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
    public void onHeaderClicked(TOCHeader header, int sectionIndex) {

    }

    @Override
    public void onItemClicked(TOCHeader header, TOC item, int sectionIndex, int itemIndex) {
        adapter.setItemSelectedIndex(itemIndex);
        adapter.setHeaderSelectedIndex(sectionIndex);
        adapter.notifyDataSetChanged();
        String pdfFilePath = FileUtils.getPath(Config.PDF_FILE_NAME);
        FileUtils.viewPdf(this, pdfFilePath, (int)item.getStartPageNumber());
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
            List<TOCHeader> data = DataProvider.getDetailList(realm, toc);
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
