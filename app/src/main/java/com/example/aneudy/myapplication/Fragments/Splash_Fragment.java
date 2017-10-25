package com.example.aneudy.myapplication.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aneudy.myapplication.R;
import com.example.aneudy.myapplication.ui.Begin_Login;

import static java.lang.Thread.sleep;

/**
 * Created by aneudy on 3/2/2017.
 */

public class Splash_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.splash, container, false);

        ((Begin_Login) getActivity()).showLoad();
        try {
            sleep(3000);
            ((Begin_Login) getActivity()).setFragment(1,null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return view;
    }
}


