package com.ssowens.android.homefornow.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssowens.android.homefornow.R;
import com.ssowens.android.homefornow.databinding.FragmentPhotosBinding;
import com.ssowens.android.homefornow.listeners.HotelSearchListener;
import com.ssowens.android.homefornow.models.PexelsImages;
import com.ssowens.android.homefornow.models.Photo;
import com.ssowens.android.homefornow.utils.DataManager;

import java.util.List;

import timber.log.Timber;

public class PhotoFragment extends Fragment implements HotelSearchListener {

    private FragmentPhotosBinding fragmentPhotosBinding;
    private PhotosAdapter photosAdapter;
    private List<Photo> photoList;
    private PexelsImages pexelsImages;
    private PhotosAdapter.PhotosAdapterListener listener;
    private DataManager dataManager;
    private RecyclerView recyclerView;

    public static PhotoFragment newInstance() {
        PhotoFragment fragment = new PhotoFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentPhotosBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_photos,
                container, false);
        Toolbar toolbar = fragmentPhotosBinding.toolbar;
        toolbar.setTitle("HomeForNow");
        fragmentPhotosBinding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),
                2));
        return fragmentPhotosBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        dataManager = DataManager.get(getContext());
        dataManager.addHotelSearchListener(this);
        dataManager.fetchHotelSearch();
    }

    @Override
    public void onStop() {
        super.onStop();
        dataManager.removeHotelSearchListener(this);
    }

    @Override
    public void onHotelSearchFinished() {
        Timber.i("Sheila");
        photoList = dataManager.getPhotoList();
        photosAdapter = new PhotosAdapter(photoList, listener);
        photosAdapter.setPhotoList(photoList);
        recyclerView.setAdapter(photosAdapter);
    }
}
