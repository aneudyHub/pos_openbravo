package com.example.aneudy.myapplication;

/**
 * Created by aneudy on 30/8/2016.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;


/**
 * Created by aneudy on 8/4/2016.
 */
public class AdactadorMateriale extends ArrayAdapter<Product> implements Filterable,View.OnTouchListener {

    protected ArrayList<Product> items;

    private ArrayList<Product> itemMateriales;
    private Context context;
    private Filter materialesFilter;
    private List<Product> origitemMateriales;
    private View v;


    public AdactadorMateriale(ArrayList<Product> item, Context ctx) {
        super(ctx, R.layout.lista_materiales, item);
        this.itemMateriales = item;
        this.context = ctx;
        this.origitemMateriales = item;

    }

    public int getCount() {

        return itemMateriales.size();
    }

    public Product getItem(int position) {

        return itemMateriales.get(position);
    }

    public long getItemId(int position) {

        return itemMateriales.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        v = convertView;
        Product materiale = itemMateriales.get(position);
        //final ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();

            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_lista_materiales,null);
            holder.nombre = (TextView)v.findViewById(R.id.nombre_material);
            holder.Codigo = (TextView)v.findViewById(R.id.codigo);
            holder.cant = (EditText)v.findViewById(R.id.cantidad_usada);
        }else {
            holder = (ViewHolder) v.getTag();
        }

        Log.e("Esta es la posicon",""+position);

        holder.ref = position;

        holder.nombre.setText(itemMateriales.get(position).getNAME());
        holder.Codigo.setText(String.valueOf(itemMateriales.get(position).getCODE()));
        holder.cant.setOnTouchListener(this);
        v.setOnTouchListener(this);
        v.setTag(holder);

        return v;

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view instanceof EditText) {
            EditText editText = (EditText) view;
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
        } else {
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.cant.setFocusable(false);
            holder.cant.setFocusableInTouchMode(false);

        }
        return false;
    }
    private class ViewHolder {
        EditText cant;
        TextView Codigo;
        TextView nombre;
        int ref;

    }

    @Override
    public Filter getFilter() {
        if (materialesFilter == null)
            materialesFilter = new PlanetFilter();

        return materialesFilter;
    }

    private class PlanetFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = origitemMateriales;
                results.count = origitemMateriales.size();
            }
            else {
                // We perform filtering operation
                List<Product> nPlanetList = new ArrayList<Product>();

                for (Product p : itemMateriales) {
                    if (p.getNAME().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        nPlanetList.add(p);
                }

                results.values = nPlanetList;
                results.count = nPlanetList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                itemMateriales = (ArrayList<Product>) results.values;
                notifyDataSetChanged();
            }

        }

    }
}

