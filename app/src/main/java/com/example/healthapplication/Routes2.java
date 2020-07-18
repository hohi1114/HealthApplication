package com.example.healthapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */


public class Routes2 extends Fragment {

    View mView;
    Context mContext;
    Animation fab_open, fab_close;
    FloatingActionButton fab, fab2, fab3;
    Boolean openFlag=false;

    public Routes2() {
        // Required empty public constructor
    }
    public static Routes2 newInstance(){
        return new Routes2();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_routes_2, container, false);
        mContext=getContext();
        return mView;
    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        // GalleryAdapter 사용
        super.onViewCreated(view, savedInstanceState);
        GalleryAdapterRoutes galleryAdapter = new GalleryAdapterRoutes(RUtils1.getData());
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(galleryAdapter);
    }

}
