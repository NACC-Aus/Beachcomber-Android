package com.appiphany.beachcomber.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.artifex.mupdf.MuPDFActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {
    public static void viewPdf(Context context, String fileName, int page){
        File file = new File(fileName);

        Intent intent = new Intent(context, MuPDFActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.putExtra(Config.INDEX_PAGE, page);

        try {
            context.startActivity(intent);
        } catch (Throwable e) {
            Toast.makeText(context, "NO Pdf Viewer", Toast.LENGTH_SHORT).show();
        }
    }

    public static String getPath(String filename){
        return new File(Environment.getExternalStorageDirectory(),  filename).getAbsolutePath();
    }

    public static boolean isExist(String path){
        return new File(path).exists();
    }

    public static void copyFromAsset(Context context, String filename) {
        AssetManager assetManager = context.getAssets();
        InputStream in;
        OutputStream out;
        try {
            in = assetManager.open(filename);
            out = new FileOutputStream(getPath(filename));
            copyFile(in, out);
            in.close();
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

}
