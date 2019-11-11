package com.example.catwiki.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.catwiki.Adapter.CatAdapter;
import com.example.catwiki.Database;
import com.example.catwiki.Model.Cat;
import com.example.catwiki.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchRecyclerFragment extends Fragment {

    private RecyclerView recyclerView;
    private SearchView catSearchView;


    public SearchRecyclerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.search_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        final CatAdapter catAdapter = new CatAdapter();

        catSearchView = view.findViewById(R.id.search_bar);
        catSearchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        catSearchView.setQueryHint("Search");

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        catSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                catAdapter.getFilter().filter(newText);
                return false;
            }
        });

        String catUrl = "https://api.thecatapi.com/v1/breeds?api_key=780d9802-86a8-4f99-b377-4f4b6baf5280";

        Context context = getContext();
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();

                Cat[] catsArray = gson.fromJson(response, Cat[].class);
                ArrayList<Cat> catArrayList = new ArrayList<Cat>(Arrays.asList(catsArray));

                catAdapter.setData(catArrayList);
                Database.saveCatsToFakeDatabase(catArrayList);
                recyclerView.setAdapter(catAdapter);


            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError error) {
                System.out.println(error.toString());
            }
        };
        StringRequest stringRequest = new StringRequest(Request.Method.GET, catUrl, responseListener, errorListener);
        requestQueue.add(stringRequest);

        return view;
    }
}
