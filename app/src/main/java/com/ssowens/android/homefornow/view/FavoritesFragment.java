package com.ssowens.android.homefornow.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssowens.android.homefornow.R;
import com.ssowens.android.homefornow.databinding.FragmentFavoriteHotelsBinding;
import com.ssowens.android.homefornow.db.AppDatabase;

import java.util.Collections;

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
        favoriteHotelsBinding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),
                2));
        favoriteAdapter = new FavoriteAdapter(Collections.EMPTY_LIST);
        favoriteHotelsBinding.recyclerView.setAdapter(favoriteAdapter);
        return favoriteHotelsBinding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();
        favoriteAdapter.setFavorites(appDatabase.favoriteDao().loadAllFavorites());
    }
}
