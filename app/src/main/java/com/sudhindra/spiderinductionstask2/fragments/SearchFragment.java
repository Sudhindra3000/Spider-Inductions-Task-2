package com.sudhindra.spiderinductionstask2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.sudhindra.spiderinductionstask2.R;
import com.sudhindra.spiderinductionstask2.activities.DetailsActivity;
import com.sudhindra.spiderinductionstask2.adapters.SearchAdapter;
import com.sudhindra.spiderinductionstask2.apis.SearchApi;
import com.sudhindra.spiderinductionstask2.databinding.FragmentSearchBinding;
import com.sudhindra.spiderinductionstask2.models.SearchItem;
import com.sudhindra.spiderinductionstask2.models.SearchResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";
    private FragmentSearchBinding binding;

    private SearchApi searchApi;

    private SearchAdapter adapter;
    private ArrayList<SearchItem.Data> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!s.trim().isEmpty())
                    search(s);
                else {
                    data.clear();
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(getString(R.string.searchBaseUrl))
                .build();

        searchApi = retrofit.create(SearchApi.class);

        buildRecyclerView();
    }

    private void search(String query) {
        Call<SearchResult> call = searchApi.getSearchResult(query, 1);
        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "onResponse: " + response);
                    return;
                }

                SearchResult searchResult = response.body();
                for (SearchItem searchItem : searchResult.getCollection().getSearchItems()) {
                    data.addAll(searchItem.getData());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Toast.makeText(requireContext(), "Failed to Fetch Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buildRecyclerView() {
        data = new ArrayList<>();
        adapter = new SearchAdapter(data);
        adapter.setListener(this::showDetails);

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);
    }

    private void showDetails(int pos) {
        Intent intent = new Intent(requireContext(), DetailsActivity.class);
        intent.putExtra("data", new Gson().toJson(data.get(pos)));
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}