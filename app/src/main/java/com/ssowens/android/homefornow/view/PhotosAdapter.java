package com.ssowens.android.homefornow.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssowens.android.homefornow.databinding.CardViewItemBinding;
import com.ssowens.android.homefornow.models.Photos;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.MyViewHolder> {

    private List<Photos> photosList;
    private PhotosAdapterListener listener;

    public PhotosAdapter(List<Photos> photosList, PhotosAdapterListener listener) {
        this.photosList = photosList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CardViewItemBinding cardViewItemBinding =
                CardViewItemBinding.inflate(layoutInflater, parent, false);
        return new MyViewHolder(cardViewItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.binding.setModel(photosList.get(position));
        holder.binding.mediaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onPhotoClicked(photosList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private CardViewItemBinding binding;

        public MyViewHolder(CardViewItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public interface PhotosAdapterListener {
        void onPhotoClicked(Photos photos);
    }

    public void setPhotosList(List<Photos> photosList) {
        this.photosList = photosList;
        notifyDataSetChanged();
    }
}
