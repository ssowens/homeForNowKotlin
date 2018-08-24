package com.ssowens.android.homefornow.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ssowens.android.homefornow.databinding.CardViewItemBinding;
import com.ssowens.android.homefornow.models.Photo;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.MyViewHolder> {

    private List<Photo> photoList;
    private PhotosAdapterListener listener;

    public PhotosAdapter(List<Photo> photoList, PhotosAdapterListener listener) {
        this.photoList = photoList;
        this.listener = listener;
    }

    public PhotosAdapter(List<Photo> photoList) {
        this.photoList = photoList;
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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.binding.setModel(photoList.get(position));
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CardViewItemBinding binding;

        public MyViewHolder(CardViewItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;

            binding.mediaImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Clicked ~ " +
                                    binding.getModel().getPhotographer(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public interface PhotosAdapterListener {
        void onPhotoClicked(Photo photo);
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
        notifyDataSetChanged();
    }
}
