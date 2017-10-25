package com.example.aneudy.myapplication.Utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;


/**
 * Created by Emigdio on 1/12/2015.
 */
public class AlertBuilder extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getArguments().getString("message"));
        final String Identificador = getArguments().getString("Id", "N");
        Log.e("11 Entre CAB.oCD", Identificador);

        try {
            builder.setTitle(getArguments().getString("title"));
        } catch (Exception e){}

        try {
            builder.setPositiveButton(getArguments().getString("btn_positivo"), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    mListener.onAlertPositiveClick(AlertBuilder.this, Identificador);
                }
            });
        } catch (Exception e) {}

        try {
            builder.setNegativeButton(getArguments().getString("btn_negativo"), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    mListener.onAlertNegativeClick(AlertBuilder.this, Identificador);
                }
            });
        } catch (Exception e) {}

        try {
            builder.setNeutralButton(getArguments().getString("btn_neutro"), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mListener.onAlertNeutralClick(AlertBuilder.this, Identificador);
                }
            });
        } catch (Exception e) {}

        // Create the AlertDialog object and return it
        return builder.create();
    }

    public interface NoticeDialogListener {
        void onAlertPositiveClick(DialogFragment dialog, String Identificador);
        void onAlertNegativeClick(DialogFragment dialog, String Identificador);
        void onAlertNeutralClick(DialogFragment dialog, String Identificador);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement AlerBuilder NoticeDialogListener");
        }
    }


}
