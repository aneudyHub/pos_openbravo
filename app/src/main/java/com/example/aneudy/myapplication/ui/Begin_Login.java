package com.example.aneudy.myapplication.ui;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aneudy.myapplication.Fragments.DownloadConfig_Fragment;
import com.example.aneudy.myapplication.Fragments.Login_Fragment;
import com.example.aneudy.myapplication.Fragments.ServerConfig_Fragment;
import com.example.aneudy.myapplication.Fragments.Splash_Fragment;
import com.example.aneudy.myapplication.R;

import static android.R.attr.id;

public class Begin_Login extends AppCompatActivity {



    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin__login);

        progressBar=(ProgressBar)findViewById(R.id.loading);
        progressBar.setVisibility(View.GONE);

        setFragment(0,null);
    }

    public void showLoad(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void noShowLoad(){
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_begin__login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void setFragment(int position, Bundle bundle) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Splash_Fragment main_fragment = new Splash_Fragment();
                fragmentTransaction.replace(R.id.Frame, main_fragment);
                fragmentTransaction.commit();

                break;

            case 1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                ServerConfig_Fragment cuadreCobrador_fragment = new ServerConfig_Fragment();
                fragmentTransaction.replace(R.id.Frame, cuadreCobrador_fragment);
                fragmentTransaction.commit();

                break;

            case 2:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Login_Fragment login_fragment = new Login_Fragment();
                login_fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.Frame, login_fragment);
                fragmentTransaction.commit();
                break;

            case 3:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                DownloadConfig_Fragment downloadConfig_fragment = new DownloadConfig_Fragment();
                fragmentTransaction.replace(R.id.Frame, downloadConfig_fragment);
                fragmentTransaction.commit();
                break;



        }
    }
}
