package com.example.catwiki.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catwiki.Activity.CatDetailActivity;
import com.example.catwiki.Model.Cat;
import com.example.catwiki.R;

import java.util.ArrayList;
import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder> implements Filterable {

    private ArrayList<Cat> cats;
    private List<Cat> catListFull;


    public void setData(ArrayList<Cat> catsToAdapt) {
        this.cats = catsToAdapt;
        catListFull = new ArrayList<>(catsToAdapt);
    }

    public static class CatViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView catName;

        public CatViewHolder(View v) {
            super(v);
            view = v;
            catName = v.findViewById(R.id.cat_name);

        }
    }
    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat, parent, false);

        CatViewHolder catViewHolder = new CatViewHolder(view);
        return catViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CatViewHolder holder, final int position) {
        final Cat currentCat = cats.get(position);
        holder.catName.setText(currentCat.getName());

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
    public int getItemCount() { return cats.size();}

    @Override
    public Filter getFilter() {
        return catFilter;
    }

    private Filter catFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Cat> filteredCatList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredCatList.addAll(catListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Cat catFilters : catListFull) {
                    if (catFilters.getName().toLowerCase().contains(filterPattern)) {
                        filteredCatList.add(catFilters);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredCatList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            cats.clear();
            cats.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
}
