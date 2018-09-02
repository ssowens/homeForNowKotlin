package com.ssowens.android.homefornow.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssowens.android.homefornow.R;
import com.ssowens.android.homefornow.databinding.FragmentFavoriteHotelsBinding;
import com.ssowens.android.homefornow.db.AppDatabase;
import com.ssowens.android.homefornow.db.Favorite;

import java.util.Collections;
import java.util.List;

import timber.log.Timber;

import static com.ssowens.android.homefornow.view.HotelDetailActivity.ARG_HOTEL_ID;
import static com.ssowens.android.homefornow.view.HotelDetailActivity.ARG_PHOTO_ID;
import static com.ssowens.android.homefornow.view.PhotoFragment.EXTRA_CURRENT_TOOLBAR_TITLE;


public class FavoritesFragment extends Fragment {

    private static final String DATABASE_NAME = "favorites_db";
    private AppDatabase appDatabase;
    private FragmentFavoriteHotelsBinding favoriteHotelsBinding;
    private Toolbar toolbar;
    private FavoriteAdapter favoriteAdapter;


    public static FavoritesFragment newInstance(String hotelId, String photoId) {
        Bundle args = new Bundle();
        args.putString(ARG_HOTEL_ID, hotelId);
        args.putString(ARG_PHOTO_ID, photoId);

        FavoritesFragment fragment = new FavoritesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle args = getArguments();
        if (args != null) {
            String hotelId = args.getString(ARG_HOTEL_ID);
            String photoId = args.getString(ARG_PHOTO_ID);
        }
        appDatabase = AppDatabase.getInstance(getContext());
        retrieveFavorites();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        favoriteHotelsBinding =
                DataBindingUtil.inflate(inflater, R.layout
                        .fragment_favorite_hotels, container, false);
        toolbar = favoriteHotelsBinding.toolbar;
        if (savedInstanceState == null) {
            toolbar.setTitle(R.string.favorites);
        } else {
            int currentToolbarTitle = savedInstanceState.getInt(EXTRA_CURRENT_TOOLBAR_TITLE,
                    R.string.toolbar_title);
        }
        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled
                    (true);
        }
        favoriteHotelsBinding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),
                1));
        favoriteAdapter = new FavoriteAdapter(Collections.EMPTY_LIST);
        favoriteHotelsBinding.recyclerView.setAdapter(favoriteAdapter);
        return favoriteHotelsBinding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void retrieveFavorites() {
        Timber.d("Actively retrieving the favorite from the database");
        final LiveData<List<Favorite>> favs = appDatabase.favoriteDao().loadAllFavorites();
        favs.observe(getActivity(), new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable List<Favorite> favorites) {
                Timber.d("Receiving database update from LiveData");
                favoriteAdapter.setFavorites(favorites);
            }
        });
    }
}

