package lk.bsc212.pdsa.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.crowdfire.cfalertdialog.CFAlertDialog;

public class AlertDialog {
    CFAlertDialog cfAlertDialog;

    public void positiveAlert(Context context, String title, String description, String buttonTitle){
        cfAlertDialog = new CFAlertDialog.Builder(context)
            .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
            .setTitle(title + " \uD83C\uDF8A")
            .setMessage(description)
            .addButton(buttonTitle, -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            })
            .show();
    }

    public void positiveAlert(Context context, String title, String description, String buttonTitle, Intent intent){
        cfAlertDialog = new CFAlertDialog.Builder(context)
                .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setTitle(title + " \uD83C\uDF8A")
                .setMessage(description)
                .addButton(buttonTitle, -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        context.startActivity(intent);
                    }
                })
                .show();
    }

    public void negativeAlert(Context context, String title, String description, String buttonTitle){
        cfAlertDialog = new CFAlertDialog.Builder(context)
            .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
            .setTitle(title + " \uD83E\uDD2F")
            .setMessage(description)
            .addButton(buttonTitle, -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            })
            .show();
    }
}
