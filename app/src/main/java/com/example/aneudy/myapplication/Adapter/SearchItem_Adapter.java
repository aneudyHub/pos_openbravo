package com.example.aneudy.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;

import com.example.aneudy.myapplication.Product;
import com.example.aneudy.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aneudy on 31/1/2017.
 */

public class SearchItem_Adapter extends ArrayAdapter<Product>{

    private final Context mContext;
    private final ArrayList<Product> mProducts;
    private final ArrayList<Product> mProducts_All;
    private final ArrayList<Product> mProducts_Suggestion;
    private final int mLayoutResourceId;
    private LayoutInflater layoutInflater;

    public SearchItem_Adapter(Context context, int resource, ArrayList<Product> Products) {
        super(context, resource, Products);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mProducts = new ArrayList<>(Products);
        this.mProducts_All = new ArrayList<>(Products);
        this.mProducts_Suggestion = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);

    }

    public int getCount() {
        return mProducts.size();
    }

    public Product getItem(int position) {
        return mProducts.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if (convertView == null) {
                convertView = this.layoutInflater.inflate(mLayoutResourceId, parent, false);
            }
            Product product = getItem(position);
            TextView code = (TextView) convertView.findViewById(R.id.CodeP);
            TextView name = (TextView) convertView.findViewById(R.id.NameP);
            name.setText(product.getNAME());
            code.setText(product.getCODE());
            Log.e("adp view","ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public String convertResultToString(Object resultValue) {
                return ((Product) resultValue).getCODE()+"-"+((Product) resultValue).getNAME();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    mProducts_Suggestion.clear();
                    for (Product product : mProducts_All) {
                        if (product.getNAME().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            mProducts_Suggestion.add(product);
                        }
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = mProducts_Suggestion;
                    filterResults.count = mProducts_Suggestion.size();
                    return filterResults;
                } else {
                    return new FilterResults();
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mProducts.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                    List<?> result = (List<?>) results.values;
                    for (Object object : result) {
                        if (object instanceof Product) {
                            mProducts.add((Product) object);
                        }
                    }
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    mProducts.addAll(mProducts_All);
                }
                notifyDataSetChanged();
            }
        };
    }
}
