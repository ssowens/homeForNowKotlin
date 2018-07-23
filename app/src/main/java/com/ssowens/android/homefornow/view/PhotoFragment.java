package com.ssowens.android.homefornow.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssowens.android.homefornow.R;
import com.ssowens.android.homefornow.databinding.FragmentPhotosBinding;

public class PhotoFragment extends Fragment {

    private FragmentPhotosBinding fragmentPhotosBinding;
    private PhotosAdapter photosAdapter;

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
        fragmentPhotosBinding = DataBindingUtil.inflate(inflater, R.layout
                .fragment_photos, container, false);
        Toolbar toolbar = fragmentPhotosBinding.toolbar;
        toolbar.setTitle("HomeForNow");
        initRecyclerView();
        return fragmentPhotosBinding.getRoot();
    }

    private void initRecyclerView() {
        fragmentPhotosBinding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),
                2));
    }

}
