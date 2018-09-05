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
import com.ssowens.android.homefornow.databinding.FragmentFavoriteHotelsBinding;
import com.ssowens.android.homefornow.db.AppDatabase;
import com.ssowens.android.homefornow.db.Favorite;
import com.ssowens.android.homefornow.viewModels.HotelDetailViewModel;

import java.util.Collections;
import java.util.List;

import timber.log.Timber;

import static com.ssowens.android.homefornow.view.HotelDetailActivity.ARG_HOTEL_ID;
import static com.ssowens.android.homefornow.view.HotelDetailActivity.ARG_PHOTO_ID;
import static com.ssowens.android.homefornow.view.HotelDetailFragment.FAVORITES_KEY;
import static com.ssowens.android.homefornow.view.PhotoFragment.EXTRA_CURRENT_TOOLBAR_TITLE;


public class FavoritesFragment extends Fragment {

    private static final String DATABASE_NAME = "favorites_db";
    public static final String ARG_ONLINE = "online";
    private AppDatabase appDatabase;
    private Toolbar toolbar;
    private FavoriteAdapter favoriteAdapter;
    private int currentToolbarTitle;
    private String hotelId;
    private String photoId;
    private boolean isOnline;


    public static FavoritesFragment newInstance(String hotelId,
                                                String photoId,
                                                boolean isOnline)

    {
        Bundle args = new Bundle();
        args.putString(ARG_HOTEL_ID, hotelId);
        args.putString(ARG_PHOTO_ID, photoId);
        args.putBoolean(ARG_ONLINE, isOnline);
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
            hotelId = args.getString(ARG_HOTEL_ID);
            photoId = args.getString(ARG_PHOTO_ID);
            isOnline = args.getBoolean(ARG_ONLINE); // Set a default value
        }
        setHasOptionsMenu(true);
        appDatabase = AppDatabase.getInstance(getContext());
        setupViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        FragmentFavoriteHotelsBinding favoriteHotelsBinding = DataBindingUtil.inflate(inflater, R.layout
                .fragment_favorite_hotels, container, false);
        toolbar = favoriteHotelsBinding.toolbar;
        if (savedInstanceState == null) {
            toolbar.setTitle(R.string.favorites);
        } else {
            int currentToolbarTitle = savedInstanceState.getInt(EXTRA_CURRENT_TOOLBAR_TITLE,
                    R.string.toolbar_title);
        }
        if (toolbar != null && isOnline) {
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_photo, menu);
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
    public void onResume() {
        super.onResume();

    }

    private void setupViewModel() {
        HotelDetailViewModel viewModel = ViewModelProviders.of(this).get(HotelDetailViewModel
                .class);
        viewModel.getFavorites().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable List<Favorite> favorites) {
                Timber.d("Updating list of favorites from LiveData in ViewModel");
                saveFavoritesToSharedPreferences(favorites);
                favoriteAdapter.setFavorites(favorites);
            }
        });
    }

    public void saveFavoritesToSharedPreferences(List<Favorite> favs) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        int favCount = favs.size();
        editor.putInt(FAVORITES_KEY, favCount);
        editor.apply();
    }
}