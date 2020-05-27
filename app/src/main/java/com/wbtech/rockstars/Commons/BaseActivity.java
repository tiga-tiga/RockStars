package com.wbtech.rockstars.Commons;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {


    /*
    Helper Activity for shared user interface
     */
    private ProgressDialog mProgressDialog;

    public void showProgressDialog(String message) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void showAlertDialog(Context context, String message, final AlertDialogListener alertDialogListener){
        new AlertDialog.Builder(context)
                .setTitle("Alert")
                .setMessage(message)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialogListener.onConfirm();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    public interface AlertDialogListener{
        void onConfirm();
    }


}
