package com.appiphany.beachcomber;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.appiphany.beachcomber.adapter.TocAdapter;
import com.appiphany.beachcomber.util.Config;
import com.artifex.mupdf.MuPDFActivity;

public class MainActivity extends Activity {

    public final String mExtStoragePath = Environment.getExternalStorageDirectory().getPath();
    private ListView mLvContent;
    private TocAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<String[]> listContent = parseCSVFile();
        mLvContent = (ListView) findViewById(R.id.lvContent);
        mAdapter = new TocAdapter(this, listContent);
        mLvContent.setAdapter(mAdapter);

        mLvContent.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file = new File(mExtStoragePath + "/" + Config.PDF_FILE_NAME);

                Intent intent = new Intent(MainActivity.this, MuPDFActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                intent.putExtra(Config.INDEX_PAGE, Integer.parseInt(listContent.get(position)[2]));

                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(MainActivity.this, "NO Pdf Viewer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        File pdfFile = new File(mExtStoragePath + "/" + Config.PDF_FILE_NAME);
        if (!pdfFile.exists()) {
            copyAssetFileToSd();
        }
    }

    // use this method to write the PDF file to sdcard
    private void copyAssetFileToSd() {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        for (int i = 0; i < files.length; i++) {
            String fStr = files[i];
            if (fStr.equalsIgnoreCase(Config.PDF_FILE_NAME)) {
                InputStream in = null;
                OutputStream out = null;
                try {
                    in = assetManager.open(files[i]);
                    out = new FileOutputStream(mExtStoragePath + "/" + files[i]);
                    copyFile(in, out);
                    in.close();
                    in = null;
                    out.flush();
                    out.close();
                    out = null;
                    break;
                } catch (Exception e) {
                    Log.d("KienLT", "copy file to sdcard error: " + e.getMessage());
                }
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    private List<String[]> parseCSVFile() {
        String next[] = {};
        List<String[]> list = new ArrayList<String[]>();

        try {
            CSVReader reader = new CSVReader(new InputStreamReader(getAssets().open(Config.CSV_FILE_NAME)));
            for (;;) {
                next = reader.readNext();
                if (next != null) {
                    list.add(next);
                } else {
                    break;
                }
            }

            list.remove(0);
            list.add(0, new String[] { "Introduction", "", "1" });
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
