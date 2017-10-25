package com.example.aneudy.myapplication.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by aneudy on 3/2/2017.
 */

import com.example.aneudy.myapplication.Data.ServerContract;
import com.example.aneudy.myapplication.R;

public class ServersAdapter extends CursorAdapter{

    public ServersAdapter(Context context) {
        super(context, null, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(R.layout.server_list_layout,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView Nombre = (TextView)view.findViewById(R.id.ServerName);
        Nombre.setText(cursor.getString(cursor.getColumnIndex(ServerContract.Servers.NAME)));

        TextView HostName = (TextView)view.findViewById(R.id.HostName);
        HostName.setText(cursor.getString(cursor.getColumnIndex(ServerContract.Servers.HOST_NAME)));

        TextView Port = (TextView) view.findViewById(R.id.Port);
        Port.setText(cursor.getString(cursor.getColumnIndex(ServerContract.Servers.PORT)));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
