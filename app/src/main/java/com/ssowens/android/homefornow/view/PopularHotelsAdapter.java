package com.ssowens.android.homefornow.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssowens.android.homefornow.databinding.PopularCardViewItemBinding;
import com.ssowens.android.homefornow.models.Hotel;
import com.ssowens.android.homefornow.models.Offers;
import com.ssowens.android.homefornow.models.Photo;

import java.util.ArrayList;
import java.util.List;

import static com.ssowens.android.homefornow.view.HotelDetailActivity.ARG_HOTEL_ID;
import static com.ssowens.android.homefornow.view.HotelDetailActivity.ARG_PHOTO_ID;

public class PopularHotelsAdapter extends RecyclerView.Adapter<PopularHotelsAdapter.MyViewHolder> {

    private List<Hotel> hotelPopularHotelsList;
    private List<Photo> hotelPhotoList = new ArrayList<>();
    private PopularHotelsAdapterListener listener;

    public PopularHotelsAdapter(List<Hotel> hotelTopRatedHotelsList) {
        this.hotelPopularHotelsList = hotelTopRatedHotelsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        PopularCardViewItemBinding popularCardViewItemBinding =
                PopularCardViewItemBinding.inflate(layoutInflater, parent, false);
        return new MyViewHolder(popularCardViewItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.setModel(hotelPopularHotelsList.get(position));
        if (position < hotelPhotoList.size())
            holder.binding.setPhoto((hotelPhotoList.get(position)));
    }

    @Override
    public int getItemCount() {
        return hotelPopularHotelsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private PopularCardViewItemBinding binding;

        public MyViewHolder(PopularCardViewItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
            binding.mediaImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), HotelDetailActivity.class);
                    intent.putExtra(ARG_HOTEL_ID, binding.getModel().getHotelId());
                    intent.putExtra(ARG_PHOTO_ID, binding.getPhoto().getId());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    public interface PopularHotelsAdapterListener {
        void onPhotoClicked(Offers hotelTopRatedHotesl);
    }

    public void setPopularHotelsList(List<Hotel> hotelPopularHotelsList) {
        this.hotelPopularHotelsList = hotelPopularHotelsList;
        notifyDataSetChanged();
    }

    public void setHotelPhotoList(List<Photo> hotelPhotoList) {
        this.hotelPhotoList = hotelPhotoList;
    }
}