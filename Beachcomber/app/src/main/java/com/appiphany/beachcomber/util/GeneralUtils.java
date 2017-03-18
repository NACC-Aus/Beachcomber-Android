package com.appiphany.beachcomber.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class GeneralUtils {
    public static AlertDialog showAlertDialog(final Context context, int titleId, final int messageId, boolean isCancel, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titleId);
        builder.setMessage(messageId);
        builder.setNeutralButton("OK",listener);
        if (isCancel) {
            builder.setCancelable(true);
        }
        return builder.create();
    }
}
