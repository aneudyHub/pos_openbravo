package com.example.aneudy.myapplication.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.aneudy.myapplication.Adapter.Clients_Adapter;
import com.example.aneudy.myapplication.NET.ApiClient;
import com.example.aneudy.myapplication.R;
import com.example.aneudy.myapplication.provider.Configs;
import com.example.aneudy.myapplication.provider.HelperTareas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Clients extends AppCompatActivity {

    private SearchView mSearchView;
    private RecyclerView recyclerView;
    private Clients_Adapter clients_adapter;
    public ProgressDialog progressDialog;
    public ConstraintLayout Error_Layout;
    public ImageView Error_Refresh;
    public String query;
    public ConstraintLayout Notfound_Layout;
    public ImageView Notfound_Refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients);
        agregarToolbar();

        HelperTareas helperTareas = new HelperTareas(this);
        HashMap<String,String> user = helperTareas.getUserDetails();
        if(user.isEmpty()){
            startActivity(new Intent(this,Login.class));
            finish();
        }
        Configs.ID_USER = user.get("id");
        Configs.MONEY = user.get("money");
        Configs.USER = user.get("name");
        Log.e("user",Configs.ID_USER+","+Configs.MONEY+","+Configs.USER);


        prepareView();


    }
    private void agregarToolbar() {
        //Toolbar toolbar = (Toolbar) findViewById(R.id.main_appbar);
        //setSupportActionBar(toolbar);
    }

    private void prepareView(){
        this.query=null;
        Error_Layout =(ConstraintLayout)findViewById(R.id.Error_layout);
        Error_Layout.setVisibility(View.GONE);
        Notfound_Layout = (ConstraintLayout)findViewById(R.id.NotFound_Layout);
        Notfound_Layout.setVisibility(View.GONE);
        recyclerView = (RecyclerView)findViewById(R.id.Clients_List);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        Error_Refresh = (ImageView)findViewById(R.id.Error_Refresh);
        Error_Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find(query);
            }
        });

        Notfound_Refresh =(ImageView)findViewById(R.id.Notfound_Resfresh);
        Notfound_Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find(query);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.logout) {
            HelperTareas helperTareas= new HelperTareas(this);
            helperTareas.logout();
            startActivity(new Intent(this,Login.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clientes_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setQueryHint("Search...");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchView.clearFocus();

                Log.e("query",query);
                Error_Layout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                find(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("newt",newText);


                return false;
            }


        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if(clients_adapter!=null){
                    clients_adapter.clientsList= new ArrayList<com.example.aneudy.myapplication.Clients>();
                    clients_adapter.notifyDataSetChanged();
                }

                mSearchView.clearFocus();
                return false;
            }
        });

        return true;
    }

    @Override
    public void onBackPressed() {

    }

    private void preFind(){
        Error_Layout.setVisibility(View.GONE);
        Notfound_Layout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        progressDialog = new ProgressDialog(Clients.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
    }


    private void find(String query){
        preFind();
        this.query=query;
        ApiClient
                .getClient()
                .getClientes(query)
                .enqueue(new Callback<List<com.example.aneudy.myapplication.Clients>>() {
                    @Override
                    public void onResponse(Call<List<com.example.aneudy.myapplication.Clients>> call, Response<List<com.example.aneudy.myapplication.Clients>> response) {
                        mSearchView.clearFocus();
                        progressDialog.hide();
                        if(response.isSuccessful()){

                            if(response.body().size()<=0){
                                recyclerView.setVisibility(View.GONE);
                                Notfound_Layout.setVisibility(View.VISIBLE);
                                return;
                            }

                            if(response.body().size()>0)
                                Log.e("p",response.body().get(0).getName());


                            clients_adapter = new Clients_Adapter(response.body(),R.layout.clients_item_layout,Clients.this);
                            recyclerView.setAdapter(clients_adapter);

                        }else{
                            recyclerView.setVisibility(View.GONE);
                            Notfound_Layout.setVisibility(View.GONE);
                            Error_Layout.setVisibility(View.VISIBLE);
                        }


                    }

                    @Override
                    public void onFailure(Call<List<com.example.aneudy.myapplication.Clients>> call, Throwable t) {
                        Log.e("error",t.getMessage());
                        //clients_adapter.clientsList.clear();
                        mSearchView.clearFocus();
                        recyclerView.setVisibility(View.GONE);
                        Notfound_Layout.setVisibility(View.GONE);
                        progressDialog.hide();
                        Error_Layout.setVisibility(View.VISIBLE);
                    }
                });
    }

}
