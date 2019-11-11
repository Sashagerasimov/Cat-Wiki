package com.example.catwiki.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catwiki.Adapter.FavAdapter;
import com.example.catwiki.Model.Cat;
import com.example.catwiki.R;

import java.util.ArrayList;

public class FavouriteRecyclerFragment extends Fragment {

    public RecyclerView recyclerView;
    public static TextView status;

    public FavouriteRecyclerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        recyclerView = view.findViewById(R.id.fav_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        final FavAdapter favAdapter = new FavAdapter();
        recyclerView.setAdapter(favAdapter);

        status = view.findViewById(R.id.empty);

        if (FavAdapter.favCats.size() == 0){
            status.setText("No Favourites");
        } else {
            status.setText("");
        }

        return view;
    }
}
