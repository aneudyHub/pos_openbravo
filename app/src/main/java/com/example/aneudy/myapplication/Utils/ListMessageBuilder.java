package com.example.aneudy.myapplication.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Emigdio on 1/20/2015.
 */
public class ListMessageBuilder extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final String Identificador = getArguments().getString("Id", "N");
        Log.e("4 Vengo desde CLiMB.oCD",Identificador);

        try {
            builder.setTitle(getArguments().getString("title"));
        } catch (Exception e){}


        builder.setItems(getArguments().getCharSequenceArray("lista"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                mListener.onListDialogListenerItemClickListener(ListMessageBuilder.this, which, Identificador);
            }
        });
        return builder.create();
    }

    public interface ListDialogListener {
        void onListDialogListenerItemClickListener(DialogFragment dialog, int which, String Identificador);
    }

    // Use this instance of the interface to deliver action events
    ListDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (ListDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement ListMessageBuilder NoticeDialogListener");
        }
    }

}
