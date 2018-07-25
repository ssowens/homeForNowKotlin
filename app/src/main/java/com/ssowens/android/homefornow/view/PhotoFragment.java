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
import com.ssowens.android.homefornow.databinding.FragmentPhotosBinding;
import com.ssowens.android.homefornow.listeners.HotelSearchListener;
import com.ssowens.android.homefornow.models.PexelsImages;
import com.ssowens.android.homefornow.models.Photos;
import com.ssowens.android.homefornow.utils.DataManager;

import java.util.List;

public class PhotoFragment extends Fragment implements HotelSearchListener {

    private FragmentPhotosBinding fragmentPhotosBinding;
    private PhotosAdapter photosAdapter;
    private static final String PEXELS_ENDPOINT = "https://api.pexels.com";
    private List<Photos> photosList;
    private PexelsImages pexelsImages;
    private PhotosAdapter.PhotosAdapterListener listener;
    private DataManager dataManager;

    public static PhotoFragment newInstance() {
        PhotoFragment fragment = new PhotoFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(PEXELS_ENDPOINT)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ApiService service = retrofit.create(ApiService.class);
//        Call<PexelsImages> call = service.getImages("hotel");
//        photosAdapter = new PhotosAdapter(photos, listener);
//        call.enqueue(new Callback<PexelsImages>() {
//                         @Override
//                         public void onResponse(Call<PexelsImages> call,
//                                                Response<PexelsImages> response) {
//                             try {
//                                 pexelsImages = response.body();
//                             } catch (Exception e) {
//                                 Timber.e(getString(R.string.error_occurred));
//                                 e.printStackTrace();
//                             }
//                         }
//
//                         @Override
//                         public void onFailure(Call<PexelsImages> call, Throwable t) {
//                             Timber.e(getString(R.string.on_failure_error));
//                         }
//                     }
//
//        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentPhotosBinding = DataBindingUtil.inflate(inflater, R.layout
                .fragment_photos, container, false);
        Toolbar toolbar = fragmentPhotosBinding.toolbar;
        toolbar.setTitle("HomeForNow");
        initRecyclerView();
        return fragmentPhotosBinding.getRoot();
    }

    private void initRecyclerView() {
        fragmentPhotosBinding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),
                2));
    }

    @Override
    public void onStart() {
        super.onStart();
        dataManager = DataManager.get(getContext());
        dataManager.addHotelSearchListener(this);
        dataManager.fetchHotelSearch();
    }

    @Override
    public void onStop() {
        super.onStop();
        dataManager.removeHotelSearchListener(this);
    }

    @Override
    public void onHotelSearchFinished() {
        photosList = dataManager.getPhotosList();
        photosAdapter.setPhotosList(photosList);
    }
}
