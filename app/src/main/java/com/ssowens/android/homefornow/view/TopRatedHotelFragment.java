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
import com.ssowens.android.homefornow.databinding.FragmentTopRatedHotelsBinding;
import com.ssowens.android.homefornow.listeners.AccessTokenListener;
import com.ssowens.android.homefornow.listeners.HotelImageListener;
import com.ssowens.android.homefornow.listeners.HotelOffersSearchListener;
import com.ssowens.android.homefornow.models.Hotel;
import com.ssowens.android.homefornow.models.Photo;
import com.ssowens.android.homefornow.utils.DataManager;

import java.util.Collections;
import java.util.List;

import timber.log.Timber;

/**
 * Created by Sheila Owens on 8/8/18.
 */
public class TopRatedHotelFragment extends Fragment
        implements HotelOffersSearchListener, HotelImageListener,
        AccessTokenListener {

    private TopRatedHotelsAdapter topRatedHotelsAdapter;
    private DataManager dataManager;
    private int currentToolbarTitle;
    private Toolbar toolbar;
    FragmentTopRatedHotelsBinding fragmentTopRatedHotelsBinding;
    public static final String EXTRA_HOTEL_TYPE = "hotelType";
    public static final int POPULAR_HOTEL = 2;
    public static final int TOP_RATED_HOTEL = 4;
    public static final String SORTED_POPULAR = "popular";
    public static final String SORTED_TOP_RATED = "toprated";
    public static final String SORTED_FAVORITES = "favorites";
    public static final String EXTRA_HOTEL_SORTED = "sorted";
    public static final String EXTRA_CURRENT_TOOLBAR_TITLE = "";
    private int hotelRating;
    private String hotelSort;

    public static TopRatedHotelFragment newInstance(int hotel_rating) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_HOTEL_TYPE, hotel_rating);

        TopRatedHotelFragment fragment = new TopRatedHotelFragment();
        fragment.setArguments(args);
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            hotelRating = args.getInt(EXTRA_HOTEL_TYPE);
            hotelSort = args.getString(EXTRA_HOTEL_SORTED);
        }
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentTopRatedHotelsBinding =
                DataBindingUtil.inflate(inflater, R.layout
                        .fragment_top_rated_hotels, container, false);
        toolbar = fragmentTopRatedHotelsBinding.toolbar;


        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }


        fragmentTopRatedHotelsBinding.loadingSpinner.setVisibility(View.VISIBLE);
        fragmentTopRatedHotelsBinding.recyclerView.setLayoutManager(new GridLayoutManager
                (getActivity(), 1));
        topRatedHotelsAdapter = new TopRatedHotelsAdapter(Collections.EMPTY_LIST);
        fragmentTopRatedHotelsBinding.recyclerView.setAdapter(topRatedHotelsAdapter);
        return fragmentTopRatedHotelsBinding.getRoot();
    }

    private void setTitleToolbar() {
        if (!TextUtils.isEmpty(hotelSort) && toolbar != null) {
            if (hotelSort.equals(SORTED_POPULAR)) {
                toolbar.setTitle("Most Popular");
            } else if (hotelSort.equals(SORTED_TOP_RATED)) {
                toolbar.setTitle("Top Rated");
            } else toolbar.setTitle("Favorites");
        } else if (toolbar != null) {
            toolbar.setTitle("HomeForNow");
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
        List<Hotel> hotelTopRatedHotelList = dataManager.getTopRatedHotelsList();
        topRatedHotelsAdapter.setTopRatedHotelsList(hotelTopRatedHotelList);
        fragmentTopRatedHotelsBinding.loadingSpinner.setVisibility(View.GONE);
    }

    @Override
    public void onAccessTokenFinished() {
        if (dataManager.getTokenString() == null) {
            Timber.i("Sheila missing token");
        } else {
            Timber.i("Sheila got token");
            dataManager.addHotelOffersSearchListener(this);
            dataManager.fetchHotelOffers(hotelRating);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        setTitleToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.most_popular:
                Toast.makeText(getActivity(), "Most Popular selected", Toast.LENGTH_SHORT)
                        .show();
                currentToolbarTitle = R.string.most_popular;
                intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(EXTRA_HOTEL_TYPE, POPULAR_HOTEL);
                intent.putExtra(EXTRA_HOTEL_SORTED, SORTED_POPULAR);
                startActivity(intent);
                break;
            case R.id.top_rated:
                Toast.makeText(getActivity(), "Top Rated selected", Toast.LENGTH_SHORT)
                        .show();
                currentToolbarTitle = R.string.top_rated;
                intent = new Intent(getActivity(), TopRatedHotelActivity.class);
                intent.putExtra(EXTRA_HOTEL_TYPE, TOP_RATED_HOTEL);
                intent.putExtra(EXTRA_HOTEL_SORTED, SORTED_TOP_RATED);
                startActivity(intent);
                break;
            case R.id.favorite:
                Toast.makeText(getActivity(), "FavoritesActivity selected", Toast.LENGTH_SHORT)
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
        topRatedHotelsAdapter.setHotelPhotoList(photoList);

        if (dataManager.getTokenString() == null) {
            // Get Access Token
            dataManager.addAccessTokenListener(this);
            dataManager.fetchAccessToken();
        } else {
            Timber.i("Sheila got token");
            dataManager.addHotelOffersSearchListener(this);
            dataManager.fetchHotelOffers(hotelRating);
        }

    }
}
