package com.example.catwiki.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catwiki.Activity.CatDetailActivity;
import com.example.catwiki.Fragment.FavouriteRecyclerFragment;
import com.example.catwiki.Model.Cat;
import com.example.catwiki.R;

import java.util.ArrayList;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavouriteViewHolder>{
    public static ArrayList<Cat> favCats = new ArrayList<>();

    public void setData(ArrayList<Cat> favCatsToAdapt) {
        this.favCats = favCatsToAdapt;

    }

    public static class FavouriteViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView favCatName;
        public FavouriteViewHolder(View v) {
            super(v);
            view = v;
            favCatName = v.findViewById(R.id.cat_name);
        }
    }
        @NonNull
        @Override
        public FavAdapter.FavouriteViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_fav, parent, false);

            FavAdapter.FavouriteViewHolder favouriteViewHolder= new FavAdapter.FavouriteViewHolder(view);
            return favouriteViewHolder;
        }
        @Override
        public void onBindViewHolder(@NonNull final FavouriteViewHolder holder, final int position) {
        final Cat currentCat = favCats.get(position);
        holder.favCatName.setText(currentCat.getName());

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();

                    Intent explicitIntent = new Intent(context, CatDetailActivity.class);
                    explicitIntent.putExtra("id", currentCat.getId());
                    context.startActivity(explicitIntent);
                }
            });
        }
    @Override
    public int getItemCount() {
        return favCats.size();
    }
}
