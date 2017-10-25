package com.example.aneudy.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aneudy.myapplication.Clients;
import com.example.aneudy.myapplication.R;
import com.example.aneudy.myapplication.Utils.Memory;
import com.example.aneudy.myapplication.ui.MainActivity;
import com.example.aneudy.myapplication.ui.Operations;

import java.util.List;

/**
 * Created by aneudy on 27/7/2017.
 */

public class Clients_Adapter extends RecyclerView.Adapter<Clients_Adapter.Clients_Item>{

    public List<Clients> clientsList;
    public int rowLayout;
    public Context ctx;

    public Clients_Adapter(List<Clients> clientses,int rowLayout,Context ctx){
        this.clientsList=clientses;
        this.rowLayout=rowLayout;
        this.ctx=ctx;
    }

    @Override
    public Clients_Item onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(this.rowLayout,parent,false);
        Clients_Item clients_item= new Clients_Item(view);
        return clients_item;
    }

    @Override
    public void onBindViewHolder(Clients_Item holder, int position) {
        final Clients clients = clientsList.get(position);
        holder.TAXID.setText(clients.getTAID());
        holder.Name.setText(clients.getName());
        holder.Address.setText(clients.getAddress());
        holder.Telephone.setText(clients.getTelephone());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Memory.client=clients;
                Intent intent=new Intent(ctx,Operations.class);
                //intent.putExtra("ID",clients.getId());
                //intent.putExtra("NAME",clients.getName());
                //intent.putExtra("TAX",clients.getTAID());
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.clientsList.size();
    }

    public static class Clients_Item extends RecyclerView.ViewHolder{
        public CardView layout;
        public TextView Name;
        public TextView TAXID;
        public TextView Telephone;
        public TextView Address;


        public Clients_Item(View itemView) {
            super(itemView);

            Name=(TextView)itemView.findViewById(R.id.Clients_Name);
            TAXID=(TextView)itemView.findViewById(R.id.Client_Taxid);
            Telephone=(TextView)itemView.findViewById(R.id.Clients_Telephone);
            Address=(TextView)itemView.findViewById(R.id.Clients_Address);
            layout=(CardView)itemView.findViewById(R.id.Clients_Item);
        }
    }
}
