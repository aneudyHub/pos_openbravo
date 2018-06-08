package com.example.aneudy.myapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aneudy.myapplication.ui.MainActivity;
import com.example.aneudy.myapplication.Product;
import com.example.aneudy.myapplication.R;

import java.util.ArrayList;


/**
 * Created by aneudy on 8/4/2016.
 */
public class DetalleProductos extends RecyclerView.Adapter<DetalleProductos.DetallesViewHolder> {

    private Context context;
    public ArrayList<Product> items;
    private int rowlayout;

    public DetalleProductos(Context context, ArrayList<Product> items,int rowLayout){
        this.context=context;
        this.items=items;
        this.rowlayout=rowLayout;

    }
    public DetalleProductos(Context context,int rowLayout){
        this.context=context;
        this.items=new ArrayList<Product>();
        this.rowlayout=rowLayout;

    }

    @Override
    public DetallesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DetallesViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(this.rowlayout, parent, false);
        vh = new DetallesViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final DetallesViewHolder holder, final int position) {
        holder.CodeItem.setText(items.get(position).getCODE());
        holder.NameItem.setText(items.get(position).getNAME());
        holder.CantItem.setText(String.valueOf(items.get(position).getCantidad()));
        Double f = (Double.parseDouble(items.get(position).getPRICESELL()));
        holder.PriceItem.setText(String.valueOf(f));
        holder.BtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetalleProductos.this.items.remove(position);
                notifyDataSetChanged();
                update();
            }
        });


    }

    @Override
    public long getItemId(int i) {
        return Long.parseLong(items.get(i).getREFERENCE());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void update(){
        Double total=0.00;
        Double monto=0.00;
        Double tax=0.00;
        Double taxC=0.00;
        Double TotalGeneral=0.00;
        for (int x = 0; x < items.size(); x++) {
            monto=Double.parseDouble(items.get(x).getPRICESELL())*items.get(x).getCantidad();
            tax=monto*0.10;
            taxC+=tax;
            total+=monto;
            Log.e("count",items.get(x).getNAME());
        }
        TotalGeneral=total;
        //MainActivity.Monto.setText(total.toString());
        //MainActivity.Tax.setText(taxC.toString());
        MainActivity.MontoGeneral.setText(TotalGeneral.toString());
    }


    public static class DetallesViewHolder extends RecyclerView.ViewHolder{
        TextView CodeItem;
        TextView NameItem;
        TextView CantItem;
        TextView PriceItem;
        ImageView BtnDelete;

        public DetallesViewHolder(View itemView) {
            super(itemView);
            CodeItem =(TextView)itemView.findViewById(R.id.CodItem);
            NameItem =(TextView)itemView.findViewById(R.id.NameItem);
            CantItem = (TextView)itemView.findViewById(R.id.CantItem);
            PriceItem = (TextView)itemView.findViewById(R.id.PrecioItem);
            BtnDelete =(ImageView)itemView.findViewById(R.id.BtnBorrar);
        }
    }
}