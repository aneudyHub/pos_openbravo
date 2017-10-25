package com.example.aneudy.myapplication.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.aneudy.myapplication.Adapter.SearchItem_Adapter;
import com.example.aneudy.myapplication.Adapter.ServersAdapter;
import com.example.aneudy.myapplication.Data.ServerContract;
import com.example.aneudy.myapplication.NET.ApiClient;
import com.example.aneudy.myapplication.NET.ApiInterface;
import com.example.aneudy.myapplication.ProductosResponse;
import com.example.aneudy.myapplication.R;
import com.example.aneudy.myapplication.ui.Begin_Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aneudy on 2/2/2017.
 */

public class ServerConfig_Fragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private ServersAdapter adapter;

    public ImageButton addServer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.server_config, container, false);



        addServer=(ImageButton)view.findViewById(R.id.addServer);
        addServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), ServerContract.Servers.URI_CONTENIDO,null,null,null,null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Bundle bundle = new Bundle();
        bundle.putLong(ServerContract.Servers._ID,id);
        ((Begin_Login) getActivity()).setFragment(2,bundle);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Iniciar adaptador
        adapter = new ServersAdapter(getActivity());

        setListAdapter(adapter);

        getLoaderManager().initLoader(0,null,this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
            getLoaderManager().destroyLoader(0);
            if(adapter !=null){
                adapter.changeCursor(null);
                adapter = null;
            }
        }catch (Throwable localThrowable){

        }
    }
}
