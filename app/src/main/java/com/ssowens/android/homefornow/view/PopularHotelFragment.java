package com.ssowens.android.homefornow.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ssowens.android.homefornow.R;
import com.ssowens.android.homefornow.databinding.FragmentPopularHotelsBinding;
import com.ssowens.android.homefornow.listeners.AccessTokenListener;
import com.ssowens.android.homefornow.listeners.HotelImageListener;
import com.ssowens.android.homefornow.listeners.HotelOffersSearchListener;
import com.ssowens.android.homefornow.models.Hotel;
import com.ssowens.android.homefornow.models.Photo;
import com.ssowens.android.homefornow.utils.DataManager;

import java.util.Collections;
import java.util.List;

import timber.log.Timber;

public class PopularHotelFragment extends Fragment
        implements HotelOffersSearchListener, HotelImageListener,
        AccessTokenListener {

    private PopularHotelsAdapter popularHotelsAdapter;
    private DataManager dataManager;
    private int currentToolbarTitle;
    private Toolbar toolbar;
    FragmentPopularHotelsBinding popularHotelsBinding;
    public static final String EXTRA_HOTEL_RATING = "hotelRating";
    public static final int POPULAR_HOTEL = 2;
    public static final int TOP_RATED_HOTEL = 4;
    public static final String SORTED_POPULAR = "popular";
    public static final String SORTED_TOP_RATED = "toprated";
    public static final String SORTED_FAVORITES = "favorites";
    public static final String EXTRA_HOTEL_SORTED = "sorted";
    private int hotelRating;
    private MenuItem menuItem;

    public static PopularHotelFragment newInstance(int hotelRating) {
        Bundle args = new Bundle();
        args.putInt(String.valueOf(EXTRA_HOTEL_RATING), hotelRating);

        PopularHotelFragment fragment = new PopularHotelFragment();
        fragment.setArguments(args);
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            hotelRating = args.getInt("hotelRating");
        }
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        popularHotelsBinding =
                DataBindingUtil.inflate(inflater, R.layout
                        .fragment_popular_hotels, container, false);
        toolbar = popularHotelsBinding.toolbar;

        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }

        popularHotelsBinding.loadingSpinner.setVisibility(View.VISIBLE);
        popularHotelsBinding.recyclerView.setLayoutManager(new GridLayoutManager
                (getActivity(), 1));
        popularHotelsAdapter = new PopularHotelsAdapter(Collections.EMPTY_LIST);
        popularHotelsBinding.recyclerView.setAdapter(popularHotelsAdapter);
        return popularHotelsBinding.getRoot();
    }

    private void setTitleToolbar() {
        if (toolbar != null) {
                toolbar.setTitle("Most Popular");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_photo, menu);
    }

    @Override
    public void onStart() {
        super.onStart();
        dataManager = DataManager.get(getContext());
        dataManager.addHotelImageListener(this);
        dataManager.fetchHotelPhotos();
    }

    @Override
    public void onStop() {
        super.onStop();
        dataManager.removeHotelOffersSearchListener(this);
        dataManager.removeHotelImageListener(this);
        dataManager.removeAccessTokenListener(this);
    }

    @Override
    public void onHotelOffersFinished() {
        List<Hotel> hotelTopRatedHotelList = dataManager.getPopularHotelsList();
        popularHotelsAdapter.setPopularHotelsList(hotelTopRatedHotelList);
        popularHotelsBinding.loadingSpinner.setVisibility(View.GONE);
    }

    @Override
    public void onAccessTokenFinished() {
        if (dataManager.getTokenString() == null) {
        } else {
            dataManager.addHotelOffersSearchListener(this);
            dataManager.fetchHotelOffers(hotelRating);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.most_popular:
                Toast.makeText(getActivity(), R.string.popular_currently_selected, Toast.LENGTH_SHORT)
                        .show();
                item.setVisible(false);
                break;
            case R.id.top_rated:
                Toast.makeText(getActivity(), R.string.top_rated_selected, Toast.LENGTH_SHORT)
                        .show();
                currentToolbarTitle = R.string.top_rated;
                intent = new Intent(getActivity(), TopRatedHotelActivity.class);
                intent.putExtra(EXTRA_HOTEL_RATING, TOP_RATED_HOTEL);
                intent.putExtra(EXTRA_HOTEL_SORTED, SORTED_TOP_RATED);
                startActivity(intent);
                break;
            case R.id.favorite:
                Toast.makeText(getActivity(), R.string.favorites_selected, Toast.LENGTH_SHORT)
                        .show();
                currentToolbarTitle = R.string.favorites;
                intent = new Intent(getActivity(), FavoritesActivity.class);
                intent.putExtra(EXTRA_HOTEL_SORTED, SORTED_FAVORITES);
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

    @Override
    public void onHotelImageFinished() {
        List<Photo> photoList = dataManager.getPhotoList();
        popularHotelsAdapter.setHotelPhotoList(photoList);

        if (dataManager.getTokenString() == null) {
            // Get Access Token
            dataManager.addAccessTokenListener(this);
            dataManager.fetchAccessToken();
        } else {
            dataManager.addHotelOffersSearchListener(this);
            dataManager.fetchHotelOffers(hotelRating);
        }

    }

}
