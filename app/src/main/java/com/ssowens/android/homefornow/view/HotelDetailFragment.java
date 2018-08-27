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
import com.ssowens.android.homefornow.listeners.PhotoByIdListener;
import com.ssowens.android.homefornow.models.Hotel;
import com.ssowens.android.homefornow.models.HotelDetailData;
import com.ssowens.android.homefornow.models.Photo;
import com.ssowens.android.homefornow.utils.DataManager;

import java.util.ArrayList;
import java.util.List;

import static com.ssowens.android.homefornow.view.HotelDetailActivity.ARG_HOTEL_ID;
import static com.ssowens.android.homefornow.view.HotelDetailActivity.ARG_PHOTO_ID;

/**
 * Created by Sheila Owens on 8/2/18.
 */
public class HotelDetailFragment extends Fragment
        implements HotelDetailListener, PhotoByIdListener {

    private String hotelId;
    private String photoId;
    private FragmentHotelDetailBinding fragmentHotelDetailBinding;
    private PhotoImageBinding photoImageBinding;
    private DataManager dataManager;
    private Photo photo;
    private Toolbar toolbar;
    private HotelDetailData hotelDetailData;
    private Hotel hotel;
    private List<Photo> hotelPhotoList = new ArrayList<>();


    public static HotelDetailFragment newInstance(String hotelId, String photoId) {
        Bundle args = new Bundle();
        args.putString(ARG_HOTEL_ID, hotelId);
        args.putString(ARG_PHOTO_ID, photoId);

        HotelDetailFragment fragment = new HotelDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            hotelId = args.getString(ARG_HOTEL_ID);
            photoId = args.getString(ARG_PHOTO_ID);
        }
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentHotelDetailBinding = DataBindingUtil.inflate(inflater, R.layout
                .fragment_hotel_detail, container, false);
        toolbar = fragmentHotelDetailBinding.toolbar;


        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            //getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        return fragmentHotelDetailBinding.getRoot();
    }

    private void updateUI() {

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
        dataManager.addPhotoByIdListener(this);
        dataManager.fetchPhotosById(photoId);
    }

    @Override
    public void onStop() {
        super.onStop();
        dataManager.removeHotelDetailListener(this);
        dataManager.removePhotoByIdListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onHotelDetailFinished() {
        hotelDetailData = dataManager.getHotelDetailData();
        fragmentHotelDetailBinding.setModel(hotelDetailData);
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @Override
    public void onPhotoByIdFinished() {
        photo = dataManager.getPhoto();
      //  Timber.i("Sheila onPhotoByIdFinished photo ~ %s ", photo.toString());
       // Timber.i("Sheila Photo photo %s", photo.toString());
        fragmentHotelDetailBinding.setPhoto(photo);

    }
}
