package com.artifex.mupdf;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.appiphany.beachcomber.BaseActivity;
import com.appiphany.beachcomber.R;
import com.appiphany.beachcomber.util.Config;
import com.crashlytics.android.Crashlytics;

public class MuPDFActivity extends BaseActivity implements ReaderView.OnSingleTapListener {
    private MuPDFCore core;
    private ReaderView mDocView;
    private AlertDialog.Builder mAlertBuilder;
    private Toolbar toolbar;
    private boolean isShowingToolbar;

    private int mPageIndex;

    private MuPDFCore openFile(String path) {
        Log.d("KienLT", "[MuPDFActivity] file path = " + path);
        try {
            core = new MuPDFCore(path);
            // New file: drop the old outline data
//            OutlineActivityData.set(null);
        } catch (Exception e) {
            Crashlytics.logException(e);
            return null;
        }
        return core;
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAlertBuilder = new AlertDialog.Builder(this);

        if (core == null) {
            core = (MuPDFCore) getLastNonConfigurationInstance();
        }

        if (core == null) {
            Intent intent = getIntent();
            if (intent != null) {
                if (Intent.ACTION_VIEW.equals(intent.getAction())) {
                    Uri uri = intent.getData();
                    if (uri.toString().startsWith("content://media/external/file")) {
                        // Handle view requests from the Transformer Prime's file manager
                        // Hopefully other file managers will use this same scheme, if not
                        // using explicit paths.
                        Cursor cursor = getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
                        if (cursor.moveToFirst()) {
                            uri = Uri.parse(cursor.getString(0));
                        }
                    }

                    core = openFile(Uri.decode(uri.getEncodedPath()));
                }

                // minus 1 because pdf page count from 0
                mPageIndex = intent.getIntExtra(Config.INDEX_PAGE, 0) - 1;
                Log.d("KienLT", "test page index = " + mPageIndex);
            }
        }

        if (core == null) {
            AlertDialog alert = mAlertBuilder.create();
            alert.setTitle(R.string.open_failed);
            alert.setButton(AlertDialog.BUTTON_POSITIVE, "Dismiss",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            alert.show();
            return;
        }

        createUI(savedInstanceState);
    }

    @SuppressWarnings("ConstantConditions")
    public void createUI(Bundle savedInstanceState) {
        if (core == null)
            return;
        // Now create the UI.
        // First create the document view making use of the ReaderView's internal
        // gesture recognition
        mDocView = new ReaderView(this) {
            @Override
            protected void onSettle(View v) {
                // When the layout has settled ask the page to render
                // in HQ
                ((PageView) v).addHq();
            }

            @Override
            protected void onUnsettle(View v) {
                // When something changes making the previous settled view
                // no longer appropriate, tell the page to remove HQ
                ((PageView) v).removeHq();
            }

            @Override
            protected void onNotInUse(View v) {
                ((PageView) v).releaseResources();
            }
        };
        mDocView.setAdapter(new MuPDFPageAdapter(this, core));

        mDocView.setDisplayedViewIndex(mPageIndex);

        // Stick the document view and the buttons overlay into a parent view
        RelativeLayout layout = (RelativeLayout) View.inflate(this, R.layout.layout_pdf_viewer, null);
        toolbar = (Toolbar) layout.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        layout.addView(mDocView, 0);
        layout.setBackgroundColor(ContextCompat.getColor(this, R.color.pdf_bg));
        setContentView(layout);

        mDocView.setOnSingleTapListener(this);
        showHideToolbar(false);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode >= 0)
            mDocView.setDisplayedViewIndex(resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        MuPDFCore mycore = core;
        core = null;
        return mycore;
    }

    @Override
    public void onDestroy() {
        if (core != null)
            core.onDestroy();
        core = null;
        super.onDestroy();
    }

    @SuppressWarnings("ConstantConditions")
    private void showHideToolbar(boolean isShow) {
        isShowingToolbar = isShow;
        if (isShow) {
            getSupportActionBar().show();
        } else {
            getSupportActionBar().hide();
        }

    }

    @Override
    public void onSingleTap(View v, MotionEvent event) {
        isShowingToolbar = !isShowingToolbar;
        showHideToolbar(isShowingToolbar);
    }
}
