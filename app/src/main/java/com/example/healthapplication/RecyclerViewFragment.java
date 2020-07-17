package com.example.healthapplication;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// A fragment to hold RecylcerView
public class RecyclerViewFragment extends Fragment implements GalleryItemClickListener{

    View mView;
    Context mContext;

    public static final String TAG = RecyclerViewFragment.class.getSimpleName();

    public RecyclerViewFragment() {
    }

    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        mContext = getContext();
        return mView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        // GalleryAdapter 사용
        super.onViewCreated(view, savedInstanceState);
        GalleryAdapter galleryAdapter = new GalleryAdapter(Utils.getData(), this);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(galleryAdapter);
    }

    @Override
    public void onGalleryItemClickListener(int position, ImageModel imageModel, ImageView imageView) {
        GalleryViewPagerFragment galleryViewPagerFragment = GalleryViewPagerFragment.newInstance(position, Utils.getData());

        getFragmentManager()
                .beginTransaction()
                .addSharedElement(imageView, ViewCompat.getTransitionName(imageView))
                .addToBackStack(TAG)
                .replace(android.R.id.content, galleryViewPagerFragment)
                .commit();
    }

}