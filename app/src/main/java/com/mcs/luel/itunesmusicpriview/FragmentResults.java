package com.mcs.luel.itunesmusicpriview;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcs.luel.itunesmusicpriview.pojoClasses.ResultsPojo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FragmentResults extends Fragment {

    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    private static ResultsPojo dataset;



    public static FragmentResults newInstance(ResultsPojo data) {

        FragmentResults instance = new FragmentResults();
        Bundle bundle = new Bundle();
        dataset = data;
        bundle.putParcelable("keyString", data);
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        @SuppressLint("ResourceType") View view = inflater.inflate(
                R.layout.fragment_layout,
                container,
                false
        );

        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new CustomAdapter();
        return view;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            dataset = savedInstanceState.getParcelable("keyString");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new CustomAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL));
        adapter.setDataSet(dataset); //add getActivity
        recyclerView.setAdapter(adapter);

    }
}
