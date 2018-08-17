package com.ssowens.android.homefornow.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ssowens.android.homefornow.databinding.TopRatedCardViewItemBinding;
import com.ssowens.android.homefornow.models.Data;
import com.ssowens.android.homefornow.models.Hotel;
import com.ssowens.android.homefornow.models.Offers;

import java.util.List;

/**
 * Created by Sheila Owens on 8/8/18.
 */
public class TopRatedHotelsAdapter extends RecyclerView.Adapter<TopRatedHotelsAdapter.MyViewHolder> {

    private List<Hotel> hotelTopRatedHotelsList;
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
//        holder.binding.mediaImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null) {
//                   // listener.onPhotoClicked(hotelTopRatedHotelsList.get(position));
//                }
//            }
//        });
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
        }
    }

    public interface TopRatedHotelsAdapterListener {
        void onPhotoClicked(Offers hotelTopRatedHotesl);
    }

    public void setTopRatedHotelsList(List<Hotel> hotelTopRatedHotelsList) {
        this.hotelTopRatedHotelsList = hotelTopRatedHotelsList;
        notifyDataSetChanged();
    }
}
