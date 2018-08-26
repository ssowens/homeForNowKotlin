package com.ssowens.android.homefornow.view;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ssowens.android.homefornow.R;
import com.ssowens.android.homefornow.databinding.FragmentPhotosBinding;
import com.ssowens.android.homefornow.listeners.AccessTokenListener;
import com.ssowens.android.homefornow.listeners.HotelImageListener;
import com.ssowens.android.homefornow.models.HotelTopRatedPhoto;
import com.ssowens.android.homefornow.models.PexelsImages;
import com.ssowens.android.homefornow.models.Photo;
import com.ssowens.android.homefornow.models.TokenStore;
import com.ssowens.android.homefornow.utils.DataManager;

import java.util.Collections;
import java.util.List;

import timber.log.Timber;

public class PhotoFragment extends Fragment implements
        HotelImageListener,
        AccessTokenListener {

    public static final String EXTRA_CURRENT_TOOLBAR_TITLE = "current_toolbar_title";
    private PhotosAdapter photosAdapter;
    private List<HotelTopRatedPhoto> hotelTopRatedPhotoList;
    private PexelsImages pexelsImages;
    private PhotosAdapter.PhotosAdapterListener listener;
    private DataManager dataManager;
    private RecyclerView recyclerView;
    private int currentToolbarTitle;
    private Toolbar toolbar;

    public static PhotoFragment newInstance() {
        PhotoFragment fragment = new PhotoFragment();
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
        FragmentPhotosBinding fragmentPhotosBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_photos,
                container, false);
        toolbar = fragmentPhotosBinding.toolbar;
        if (savedInstanceState == null) {
            toolbar.setTitle(R.string.most_popular);
        } else {
            currentToolbarTitle =
                    savedInstanceState.getInt(EXTRA_CURRENT_TOOLBAR_TITLE, R.string.toolbar_title);
        }
        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }
        fragmentPhotosBinding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),
                2));
        photosAdapter = new PhotosAdapter(Collections.EMPTY_LIST);
        fragmentPhotosBinding.recyclerView.setAdapter(photosAdapter);
        setHasOptionsMenu(true);
        return fragmentPhotosBinding.getRoot();
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
        dataManager.fetchHotelPopularSearch();
    }

    @Override
    public void onStop() {
        super.onStop();
        dataManager.removeHotelImageListener(this);
    }

    @Override
    public void onHotelImageFinished() {
        List<Photo> photoList = dataManager.getPhotoList();
        photosAdapter.setPhotoList(photoList);
    }

    @Override
    public void onAccessTokenFinished() {
        TokenStore tokenStore = TokenStore.get(getActivity());
        tokenStore.setAccessToken(dataManager.getAccessToken());
    }

    @Override
    public void onResume() {
        super.onResume();
        updateToolbarTitle();
        updateUI();
    }

    private void updateUI() {
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
                break;
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_CURRENT_TOOLBAR_TITLE, currentToolbarTitle);
    }


}
