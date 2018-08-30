package com.ssowens.android.homefornow.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.ssowens.android.homefornow.R;
import com.ssowens.android.homefornow.databinding.FragmentHotelDetailBinding;
import com.ssowens.android.homefornow.databinding.PhotoImageBinding;
import com.ssowens.android.homefornow.db.AppDatabase;
import com.ssowens.android.homefornow.db.Favorite;
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
    private FragmentHotelDetailBinding detailBinding;
    private PhotoImageBinding photoImageBinding;
    private DataManager dataManager;
    private Photo photo;
    private Toolbar toolbar;
    private HotelDetailData hotelDetailData;
    private Hotel hotel;
    private List<Photo> hotelPhotoList = new ArrayList<>();
    private AppDatabase database;


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
        database = AppDatabase.getInstance((getContext()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        detailBinding = DataBindingUtil.inflate(inflater, R.layout
                .fragment_hotel_detail, container, false);
        toolbar = detailBinding.toolbar;


        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            //getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f,
                1.0f, 0.7f, 1.0f, Animation
                .RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);
        detailBinding.buttonFavorite.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        //animation
                        compoundButton.startAnimation(scaleAnimation);
                        Toast.makeText(getActivity(), "Favorite Button Clicked =>" + isChecked,
                                Toast
                                        .LENGTH_SHORT)
                                .show();
                    }
                });

        return detailBinding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(ARG_HOTEL_ID, hotelId);
        outState.putString(ARG_PHOTO_ID, photoId);
        super.onSaveInstanceState(outState);
    }

    public void saveFavorite() {
        if (TextUtils.isEmpty(hotelId)) {
            Toast.makeText(getContext(), "HotelID is null",
                    Toast.LENGTH_LONG).show();
        } else {
            Favorite favorite = new Favorite(
                    hotelId,
                    photoId,
                    photo.getPhotographer(),
                    hotelDetailData.getHotel().getName(),
                    photo.getPhotoUrl(),
                    hotelDetailData.guests,
                    hotelDetailData.getType(),
                    hotelDetailData.price,
                    hotelDetailData.getDescription(),
                    "raters",
                    hotelDetailData.getOffers().get(0).getRoom().getType(),
                    hotelDetailData.getBedType()
            );
            database.favoriteDao().insertFavorite(favorite);
            Toast.makeText(getContext(), "Favorite Saved",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void updateUI() {
        // Display progress bar for user
        detailBinding.loadingSpinner.setVisibility(View.VISIBLE);
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
        detailBinding.setModel(hotelDetailData);
        detailBinding.executePendingBindings();
        detailBinding.loadingSpinner.setVisibility(View.GONE);
        saveFavorite();
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @Override
    public void onPhotoByIdFinished() {
        photo = dataManager.getPhoto();
        detailBinding.setPhoto(photo);
        detailBinding.executePendingBindings();

    }
}
