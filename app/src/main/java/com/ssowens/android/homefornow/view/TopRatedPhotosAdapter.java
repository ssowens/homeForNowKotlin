package com.ssowens.android.homefornow.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssowens.android.homefornow.databinding.TopRatedCardViewItemBinding;
import com.ssowens.android.homefornow.models.HotelTopRatedPhoto;

import java.util.List;

/**
 * Created by Sheila Owens on 8/8/18.
 */
public class TopRatedPhotosAdapter extends RecyclerView.Adapter<TopRatedPhotosAdapter.MyViewHolder> {

    private List<HotelTopRatedPhoto> hotelTopRatedPhotoList;
    private TopRatedPhotosAdapterListener listener;

    public TopRatedPhotosAdapter(List<HotelTopRatedPhoto> hotelTopRatedPhotoList) {
        this.hotelTopRatedPhotoList = hotelTopRatedPhotoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TopRatedCardViewItemBinding topRatedCardViewItemBinding =
                TopRatedCardViewItemBinding.inflate(layoutInflater, parent, false);
        return new MyViewHolder(topRatedCardViewItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.binding.setModel(hotelTopRatedPhotoList.get(position));
        holder.binding.mediaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onPhotoClicked(hotelTopRatedPhotoList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotelTopRatedPhotoList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TopRatedCardViewItemBinding binding;

        public MyViewHolder(TopRatedCardViewItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public interface TopRatedPhotosAdapterListener {
        void onPhotoClicked(HotelTopRatedPhoto hotelTopRatedPhoto);
    }

    public void setTopRatedPhotoList(List<HotelTopRatedPhoto> hotelTopRatedPhotoList) {
        this.hotelTopRatedPhotoList = hotelTopRatedPhotoList;
        notifyDataSetChanged();
    }
}
