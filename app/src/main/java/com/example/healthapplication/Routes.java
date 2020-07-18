package com.example.healthapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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

public class Routes extends Fragment implements View.OnClickListener {

    View mView;
    Context mContext;
    Animation fab_open, fab_close;
    FloatingActionButton fab, fab2, fab3;
    Boolean openFlag=false;

    public Routes() {
        // Required empty public constructor
    }
    public static Routes newInstance(){
        return new Routes();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    public void anim(){
        if (openFlag) {
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab2.setClickable(false);
            fab3.setClickable(false);
            openFlag = false;
        } else {
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab2.setClickable(true);
            fab3.setClickable(true);
            openFlag = true;
        }
    }

    @Override
    public void onClick(View v){
        int id = v.getId();

        switch (id) {
            case R.id.fab:
                anim();
                break;
            case R.id.fab2:
                /*새로운 fragement 창 띄우기 */
                Routes2 nextFrag= new Routes2();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .commit();
                //anim();
                break;
            case R.id.fab3:
                /*새로운 fragment 창 띄우기 */
                //anim();
                break;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_routes, container, false);
        mContext=getContext();

        fab_open = AnimationUtils.loadAnimation(mContext, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(mContext,R.anim.fab_close);
        fab = mView.findViewById(R.id.fab);
        fab2 = mView.findViewById(R.id.fab2);
        fab3 = mView.findViewById(R.id.fab3);

        //버튼 상태 초기화(닫혀있어라!)
        fab2.startAnimation(fab_close);
        fab3.startAnimation(fab_close);
        fab2.setClickable(false);
        fab3.setClickable(false);

        fab.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);



        return mView;
    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        // GalleryAdapter 사용
        super.onViewCreated(view, savedInstanceState);
        GalleryAdapterRoutes galleryAdapter = new GalleryAdapterRoutes(RUtils.getData());
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(galleryAdapter);
    }

}