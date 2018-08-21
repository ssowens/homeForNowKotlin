package com.ssowens.android.homefornow.view;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ssowens.android.homefornow.R;
import com.ssowens.android.homefornow.databinding.FragmentTopRatedHotelsBinding;
import com.ssowens.android.homefornow.listeners.HotelOffersSearchListener;
import com.ssowens.android.homefornow.models.Hotel;
import com.ssowens.android.homefornow.utils.DataManager;

import java.util.Collections;
import java.util.List;

import timber.log.Timber;

import static com.ssowens.android.homefornow.view.PhotoFragment.EXTRA_CURRENT_TOOLBAR_TITLE;

/**
 * Created by Sheila Owens on 8/8/18.
 */
public class TopRatedHotelFragment extends Fragment implements HotelOffersSearchListener {

    private TopRatedHotelsAdapter topRatedHotelsAdapter;
    private DataManager dataManager;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private int currentToolbarTitle;
    private ProgressDialog progressDialog;

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
        FragmentTopRatedHotelsBinding fragmentTopRatedHotelsBinding =
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

        fragmentTopRatedHotelsBinding.recyclerView.setLayoutManager(new GridLayoutManager
                (getActivity(), 2));
        topRatedHotelsAdapter = new TopRatedHotelsAdapter(Collections.EMPTY_LIST);
        fragmentTopRatedHotelsBinding.recyclerView.setAdapter(topRatedHotelsAdapter);

        return fragmentTopRatedHotelsBinding.getRoot();
    }

    private void displayProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMax(25);
        progressDialog.setMessage(getString(R.string.loading_text));
        progressDialog.setTitle(getString(R.string.top_rated_hotels));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        final Handler handle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progressDialog.incrementProgressBy(1);
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (progressDialog.getProgress() <= progressDialog
                            .getMax()) {
                        Thread.sleep(100);
                        handle.sendMessage(handle.obtainMessage());
                        if (progressDialog.getProgress() == progressDialog
                                .getMax()) {
                            progressDialog.dismiss();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    public void onStart() {
        super.onStart();
        dataManager = DataManager.get(getContext());
        displayProgressDialog();
        dataManager.addHotelOffersSearchListener(this);
        dataManager.fetchHotelOffers();
    }

    @Override
    public void onStop() {
        super.onStop();
        dataManager.removeHotelOffersSearchListener(this);
    }

    @Override
    public void onHotelOffersFinished() {
        Timber.i("Sheila ~ onHotelOffersFinished");
        List<Hotel> hotelTopRatedHotelList = dataManager.getTopRatedHotelsList();
        topRatedHotelsAdapter.setTopRatedHotelsList(hotelTopRatedHotelList);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
