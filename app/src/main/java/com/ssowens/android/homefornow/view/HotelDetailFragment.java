package com.ssowens.android.homefornow.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssowens.android.homefornow.R;
import com.ssowens.android.homefornow.databinding.FragmentHotelDetailBinding;
import com.ssowens.android.homefornow.listeners.HotelOffersSearchListener;
import com.ssowens.android.homefornow.models.Photo;
import com.ssowens.android.homefornow.utils.DataManager;

import static com.ssowens.android.homefornow.view.HotelDetailActivity.ARG_HOTEL_ID;

/**
 * Created by Sheila Owens on 8/2/18.
 */
public class HotelDetailFragment extends Fragment implements HotelOffersSearchListener {

    private String hotelId;
    private FragmentHotelDetailBinding fragmentHotelDetailBinding;
    private DataManager dataManager;
    private Photo photo;

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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentHotelDetailBinding = DataBindingUtil.inflate(inflater, R.layout
                .fragment_hotel_detail, container, false);
        return fragmentHotelDetailBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        hotelId = getArguments().getString(ARG_HOTEL_ID);
        dataManager = DataManager.get(getContext());
        photo = dataManager.getHotelPhoto(hotelId);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onHotelOffersFinished() {
        // TODO
    }
}
