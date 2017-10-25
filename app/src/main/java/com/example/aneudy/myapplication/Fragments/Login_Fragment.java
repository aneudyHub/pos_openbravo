package com.example.aneudy.myapplication.Fragments;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.aneudy.myapplication.Data.DataGeneral_config;
import com.example.aneudy.myapplication.Data.ServerContract;
import com.example.aneudy.myapplication.ui.MainActivity;
import com.example.aneudy.myapplication.NET.ApiClient;
import com.example.aneudy.myapplication.R;

/**
 * Created by aneudy on 3/2/2017.
 */

public class Login_Fragment extends Fragment{

    public long server_uri;

    TextView username;
    TextView password;
    Button login;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.login, container, false);

        Bundle bundle = getArguments();
        server_uri= bundle.getLong(ServerContract.Servers._ID);
        Log.e("id",String.valueOf(server_uri));


        getData();

        username=(TextView)view.findViewById(R.id.UserName);
        password=(TextView)view.findViewById(R.id.Password);
        login=(Button)view.findViewById(R.id.login);

        Log.e("url",ApiClient.BASE_URL);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((Begin_Login) getActivity()).setFragment(3,null);
                getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });



        return view;
    }

    public void getData(){
        if(server_uri == -1){
            return;
        }

        Uri uri = ContentUris.withAppendedId(ServerContract.Servers.URI_CONTENIDO,server_uri);
        Cursor c = getActivity().getContentResolver().query(uri,null,null,null,null);

        if(!c.moveToFirst())
            return;

        DataGeneral_config.HOST_NAME=c.getString(c.getColumnIndex(ServerContract.Servers.HOST_NAME));
        DataGeneral_config.PORT=c.getString(c.getColumnIndex(ServerContract.Servers.PORT));

        c.close();


        c.close();
    }
}
