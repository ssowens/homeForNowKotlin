package com.ssowens.android.homefornow.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ssowens.android.homefornow.databinding.TopRatedCardViewItemBinding;
import com.ssowens.android.homefornow.models.Data;
import com.ssowens.android.homefornow.models.Hotel;
import com.ssowens.android.homefornow.models.Offers;
import com.ssowens.android.homefornow.models.Photo;

import java.util.ArrayList;
import java.util.List;

import static com.ssowens.android.homefornow.view.HotelDetailActivity.ARG_HOTEL_ID;
import static com.ssowens.android.homefornow.view.HotelDetailActivity.ARG_PHOTO_ID;

/**
 * Created by Sheila Owens on 8/8/18.
 */
public class TopRatedHotelsAdapter extends RecyclerView.Adapter<TopRatedHotelsAdapter.MyViewHolder> {

    private List<Hotel> hotelTopRatedHotelsList;
    private List<Photo> hotelPhotoList = new ArrayList<>();
    private TopRatedHotelsAdapterListener listener;
    private Data hotelData;
    private TopRatedCardViewItemBinding topRatedCardViewItemBinding;

    public TopRatedHotelsAdapter(List<Hotel> hotelTopRatedHotelsList) {
        this.hotelTopRatedHotelsList = hotelTopRatedHotelsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        topRatedCardViewItemBinding =
                TopRatedCardViewItemBinding.inflate(layoutInflater, parent, false);
        return new MyViewHolder(topRatedCardViewItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.binding.setModel(hotelTopRatedHotelsList.get(position));
        if (position < hotelPhotoList.size()) holder.binding.setPhoto((hotelPhotoList.get(position)));

    }

    private void updateUI() {
       //List<Hotel> hotelList = hotelData.getOffersList();
    }

    @Override
    public int getItemCount() {
        return hotelTopRatedHotelsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TopRatedCardViewItemBinding binding;

        public MyViewHolder(TopRatedCardViewItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
            binding.mediaImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Photo => " + binding.getPhoto().getId()
                            + "Hotel id=> " + binding.getModel().getHotelId(),
                            Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(v.getContext(), HotelDetailActivity.class);
                    intent.putExtra(ARG_HOTEL_ID, binding.getModel().getHotelId());
                    intent.putExtra(ARG_PHOTO_ID, binding.getPhoto().getId());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    public interface TopRatedHotelsAdapterListener {
        void onPhotoClicked(Offers hotelTopRatedHotesl);
    }

    public void setTopRatedHotelsList(List<Hotel> hotelTopRatedHotelsList) {
        this.hotelTopRatedHotelsList = hotelTopRatedHotelsList;
        notifyDataSetChanged();
    }

    public void setHotelPhotoList(List<Photo> hotelPhotoList) {
        this.hotelPhotoList = hotelPhotoList;
    }
}
