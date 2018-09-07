package com.ssowens.android.homefornow.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ssowens.android.homefornow.R;
import com.ssowens.android.homefornow.databinding.FragmentHotelDetailBinding;
import com.ssowens.android.homefornow.db.AppDatabase;
import com.ssowens.android.homefornow.db.Favorite;
import com.ssowens.android.homefornow.listeners.HotelDetailListener;
import com.ssowens.android.homefornow.listeners.PhotoByIdListener;
import com.ssowens.android.homefornow.models.HotelDetailData;
import com.ssowens.android.homefornow.models.Photo;
import com.ssowens.android.homefornow.utils.AppExecutors;
import com.ssowens.android.homefornow.utils.DataManager;
import com.ssowens.android.homefornow.viewModels.HotelDetailViewModel;

import java.util.List;

import timber.log.Timber;

import static com.ssowens.android.homefornow.view.HotelDetailActivity.ARG_HOTEL_ID;
import static com.ssowens.android.homefornow.view.HotelDetailActivity.ARG_PHOTO_ID;
import static com.ssowens.android.homefornow.view.TopRatedHotelFragment.EXTRA_HOTEL_RATING;
import static com.ssowens.android.homefornow.view.TopRatedHotelFragment.POPULAR_HOTEL;
import static com.ssowens.android.homefornow.view.TopRatedHotelFragment.TOP_RATED_HOTEL;

/**
 * Created by Sheila Owens on 8/2/18.
 */
public class HotelDetailFragment extends Fragment
        implements HotelDetailListener, PhotoByIdListener, OnMapReadyCallback {

    public static final String FAVORITES_KEY = "favCount";
    private String hotelId;
    private String photoId;
    private FragmentHotelDetailBinding detailBinding;
    private DataManager dataManager;
    private Photo photo;
    private HotelDetailData hotelDetailData;
    private AppDatabase appDatabase;
    private boolean isFavorite;
    protected MapView mapView;
    List<Favorite> favs;
    private int currentToolbarTitle;
    private Toolbar toolbar;

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
        appDatabase = AppDatabase.getInstance((getContext()));
        setupViewModel();
    }

    private void setFavorites(List<Favorite> favoriteList) {
        this.favs = favoriteList;
    }

    public List<Favorite> getFavorites() {
        return favs;
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
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled
                    (true);
        }

        mapView = detailBinding.mapView;
        mapView.onCreate(savedInstanceState);

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

                        isFavorite = isChecked;
                        if (isChecked) {
                            saveFavorite();
                        } else {
                            removeFavorite();
                        }
                    }
                });

        return detailBinding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(ARG_HOTEL_ID, hotelId);
        outState.putString(ARG_PHOTO_ID, photoId);
        super.onSaveInstanceState(outState);
        if (mapView != null) {
            mapView.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_photo, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.most_popular:
                Toast.makeText(getActivity(), R.string.popular_selected, Toast.LENGTH_SHORT)
                        .show();
                currentToolbarTitle = R.string.most_popular;
                intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(EXTRA_HOTEL_RATING, POPULAR_HOTEL);
                startActivity(intent);
                break;
            case R.id.top_rated:
                Toast.makeText(getActivity(), R.string.top_rated_selected, Toast.LENGTH_SHORT)
                        .show();
                currentToolbarTitle = R.string.top_rated;
                intent = new Intent(getActivity(), TopRatedHotelActivity.class);
                intent.putExtra(EXTRA_HOTEL_RATING, TOP_RATED_HOTEL);
                startActivity(intent);
                break;
            case R.id.favorite:
                Toast.makeText(getActivity(), R.string.favorites_selected, Toast.LENGTH_SHORT)
                        .show();
                currentToolbarTitle = R.string.favorites;
                intent = new Intent(getActivity(), FavoritesActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        updateToolbarTitle();
        return true;
    }

    private void updateToolbarTitle() {
        if (currentToolbarTitle != 0) {
            toolbar.setTitle(currentToolbarTitle);
        }
    }

    public void saveFavorite() {
        if (!TextUtils.isEmpty(hotelId)) {
            final Favorite favorite = new Favorite(
                    hotelId,
                    photoId,
                    photo.getPhotographer(),
                    hotelDetailData.getHotel().getName(),
                    photo.getPhotoUrl(),
                    hotelDetailData.getGuests(),
                    hotelDetailData.getType(),
                    hotelDetailData.getPrice(),
                    hotelDetailData.getDescription(),
                    "raters",
                    hotelDetailData.getOffers().get(0).getRoom().getType(),
                    hotelDetailData.getBedType(),
                    isFavorite
            );
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    appDatabase.favoriteDao().insertFavorite(favorite);
                }
            });

        }
    }

    public void removeFavorite() {
        if (!TextUtils.isEmpty(hotelId)) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < favs.size(); i++) {
                        if (hotelId.equals(favs.get(i).getHotelId())) {
                            appDatabase.favoriteDao().deleteFavorite(favs.get(i));
                        }
                    }
                }
            });
        }
    }

    private void setupViewModel() {
        HotelDetailViewModel viewModel = ViewModelProviders.of(this).get(HotelDetailViewModel
                .class);
        viewModel.getFavorites().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable List<Favorite> favorites) {
                Timber.d("Updating list of favorites from LiveData in ViewModel");
            }
        });
    }

    public boolean getFavorite() {
        boolean isFav = false;
        if (favs != null && !favs.isEmpty()) {
            saveFavoritesToSharedPreferences();
            for (int i = 0; i < favs.size(); i++) {
                if (hotelId.equals(favs.get(i).getHotelId())) {
                    isFav = favs.get(i).isFavorite();
                }
            }
        }
        return isFav;
    }

    public void saveFavoritesToSharedPreferences() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        int favCount = favs.size();
        editor.putInt(FAVORITES_KEY, favCount);
        editor.apply();
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
        if (mapView != null) {
            mapView.onStart();
        }
        dataManager.addPhotoByIdListener(this);
        dataManager.fetchPhotosById(photoId);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mapView != null) {
            mapView.onStop();
        }
        dataManager.removeHotelDetailListener(this);
        dataManager.removePhotoByIdListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        if (mapView != null) {
            mapView.onPause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mapView != null) {
            try {
                mapView.onDestroy();
            } catch (NullPointerException e) {
                Timber.e(e, getString(R.string.mapview_ondestroy_error));
            }
        }
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) {
            mapView.onLowMemory();
        }
    }

    @Override
    public void onHotelDetailFinished() {
        hotelDetailData = dataManager.getHotelDetailData();
        detailBinding.setModel(hotelDetailData);
        detailBinding.executePendingBindings();
        detailBinding.loadingSpinner.setVisibility(View.GONE);
        detailBinding.buttonFavorite.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(hotelId)) {
            boolean fav = getFavorite();
            if (fav) {
                detailBinding.buttonFavorite.setBackgroundDrawable(getResources().getDrawable(R.drawable
                        .ic_favorite));
            }
        }
        mapView.getMapAsync(this);
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

    public void onMapReady(GoogleMap googleMap) {
        LatLng selectedLocation = new LatLng(hotelDetailData.getLatitude(), hotelDetailData
                .getLongitude());
        googleMap.addMarker(new MarkerOptions().position(selectedLocation));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(selectedLocation));
        detailBinding.executePendingBindings();
    }
}
