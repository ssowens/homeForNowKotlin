package com.ssowens.android.homefornow.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ssowens.android.homefornow.databinding.FavoritesCardViewItemBinding;
import com.ssowens.android.homefornow.db.Favorite;

import java.util.List;

/**
 * Created by Sheila Owens on 8/30/18.
 */
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<Favorite> favorites;

    FavoriteAdapter(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from((parent.getContext()));
        FavoritesCardViewItemBinding binding = FavoritesCardViewItemBinding.inflate
                (layoutInflater, parent, false);
        return new FavoriteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        holder.binding.setModel(favorites.get(position));

    }

    @Override
    public int getItemCount() {
        if (favorites == null) {
            return 0;
        }
        return favorites.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {

        private FavoritesCardViewItemBinding binding;

        FavoriteViewHolder(FavoritesCardViewItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;

        }
    }

    public void setFavorites(List<Favorite> favoriteList) {
        this.favorites = favoriteList;
        notifyDataSetChanged();
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

}
