package com.mcs.luel.itunesmusicpriview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mcs.luel.itunesmusicpriview.pojoClasses.ResultsPojo;

import java.net.MalformedURLException;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {


    private ResultsPojo dataSet;

    public void setDataSet(ResultsPojo dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_layout,
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        try {
            holder.bindItem(dataSet.getResults().get(position));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dataSet != null?
                dataSet.getResults().size() : 0;
    }
}
