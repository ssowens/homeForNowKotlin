package com.ssowens.android.homefornow.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssowens.android.homefornow.R;
import com.ssowens.android.homefornow.databinding.FragmentPhotosBinding;

public class PhotoFragment extends Fragment {

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
        FragmentPhotosBinding binding = DataBindingUtil.inflate(inflater, R.layout
                .fragment_photos, container, false);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return binding.getRoot();
    }
}
