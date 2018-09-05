package com.ssowens.android.homefornow.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
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

import static com.ssowens.android.homefornow.utils.DataManager.AMADEUS_ACCESS_TOKEN;
import static com.ssowens.android.homefornow.view.PhotoFragment.EXTRA_CURRENT_TOOLBAR_TITLE;

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

    public static TopRatedHotelFragment newInstance() {
        TopRatedHotelFragment fragment = new TopRatedHotelFragment();
        fragment.setRetainInstance(true);
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
        fragmentTopRatedHotelsBinding =
                DataBindingUtil.inflate(inflater, R.layout
                        .fragment_top_rated_hotels, container, false);
        toolbar = fragmentTopRatedHotelsBinding.toolbar;
        if (savedInstanceState == null) {
            toolbar.setTitle(R.string.top_rated);
        } else {
            currentToolbarTitle = savedInstanceState.getInt(EXTRA_CURRENT_TOOLBAR_TITLE,
                    R.string.toolbar_title);
        }
        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }

        fragmentTopRatedHotelsBinding.loadingSpinner.setVisibility(View.VISIBLE);
        fragmentTopRatedHotelsBinding.recyclerView.setLayoutManager(new GridLayoutManager
                (getActivity(), 2));
        topRatedHotelsAdapter = new TopRatedHotelsAdapter(Collections.EMPTY_LIST);
        fragmentTopRatedHotelsBinding.recyclerView.setAdapter(topRatedHotelsAdapter);
        return fragmentTopRatedHotelsBinding.getRoot();
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
            dataManager.fetchHotelOffers();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
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
                startActivity(intent);
                break;
            case R.id.top_rated:
                Toast.makeText(getActivity(), "Top Rated selected", Toast.LENGTH_SHORT)
                        .show();
                currentToolbarTitle = R.string.top_rated;
                intent = new Intent(getActivity(), TopRatedHotelActivity.class);
                startActivity(intent);
                break;
            case R.id.favorite:
                Toast.makeText(getActivity(), "FavoritesActivity selected", Toast.LENGTH_SHORT)
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
            dataManager.fetchHotelOffers();
        }

        // TODO NEEDS TO BE HANDLED
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString
                (AMADEUS_ACCESS_TOKEN, dataManager.getTokenString()).apply();
    }
}
