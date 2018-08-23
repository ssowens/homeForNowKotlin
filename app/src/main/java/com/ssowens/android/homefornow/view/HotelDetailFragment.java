package com.ssowens.android.homefornow.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssowens.android.homefornow.R;
import com.ssowens.android.homefornow.databinding.FragmentHotelDetailBinding;
import com.ssowens.android.homefornow.databinding.PhotoImageBinding;
import com.ssowens.android.homefornow.listeners.HotelDetailListener;
import com.ssowens.android.homefornow.listeners.HotelImageListener;
import com.ssowens.android.homefornow.models.Hotel;
import com.ssowens.android.homefornow.models.HotelDetailData;
import com.ssowens.android.homefornow.models.Photo;
import com.ssowens.android.homefornow.utils.DataManager;

import java.util.List;

import timber.log.Timber;

import static com.ssowens.android.homefornow.view.HotelDetailActivity.ARG_HOTEL_ID;

/**
 * Created by Sheila Owens on 8/2/18.
 */
public class HotelDetailFragment extends Fragment
        implements HotelDetailListener, HotelImageListener {

    private String hotelId;
    private FragmentHotelDetailBinding fragmentHotelDetailBinding;
    private PhotoImageBinding photoImageBinding;
    private DataManager dataManager;
    private Photo photo;
    private Toolbar toolbar;
    private HotelDetailData hotelDetailData;
    private Hotel hotel;


    public static HotelDetailFragment newInstance(String hotelId) {
        Bundle args = new Bundle();
        args.putString(ARG_HOTEL_ID, hotelId);

        HotelDetailFragment fragment = new HotelDetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentHotelDetailBinding = DataBindingUtil.inflate(inflater, R.layout
                .fragment_hotel_detail, container, false);
        photoImageBinding = DataBindingUtil.inflate(inflater, R.layout.photo_image, container,
                false);
        toolbar = fragmentHotelDetailBinding.toolbar;


        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            //getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        return fragmentHotelDetailBinding.getRoot();
    }

    private void updateUI() {
        hotelId = getArguments().getString(ARG_HOTEL_ID);
        Timber.i("Sheila fetchHotelOffersById = hotelid = %s ", hotelId);
        dataManager = DataManager.get(getContext());
        dataManager.addHotelDetailListener(this);
        dataManager.fetchHotelOffersById(hotelId);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateUI();
    }

    @Override
    public void onStart() {
        super.onStart();
        dataManager.addHotelImageListener(this);
        dataManager.fetchHotelPhotos();
    }

    @Override
    public void onStop() {
        super.onStop();
        dataManager.removeHotelDetailListener(this);
        dataManager.removeHotelImageListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onHotelDetailFinished() {
        hotelDetailData = dataManager.getHotelDetailData();
        Timber.i("Sheila DATA hotelDetailData %s", hotelDetailData.toString());
        fragmentHotelDetailBinding.setModel(hotelDetailData);

    }

    @Override
    public void onHotelImageFinished() {
        List<Photo> photoList = dataManager.getPhotoList();
        Timber.i("Sheila onHotelImageFinished photoList ~ %s ", photoList.toString());
    }
}
