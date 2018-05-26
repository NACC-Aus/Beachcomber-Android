package com.appiphany.beachcomber;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class PDFViewerActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(FileProvider.CONTENT_URI
                            + "Beachcomber Guide.pdf")));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "NO Pdf Viewer", Toast.LENGTH_SHORT).show();
        }
    }
}
