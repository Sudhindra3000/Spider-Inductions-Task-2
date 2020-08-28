package com.sudhindra.spiderinductionstask2.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sudhindra.spiderinductionstask2.databinding.SearchItemBinding;
import com.sudhindra.spiderinductionstask2.models.SearchItem;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchItemViewHolder> {

    private ArrayList<SearchItem.Data> data;
    private SearchListener listener;

    public SearchAdapter(ArrayList<SearchItem.Data> data) {
        this.data = data;
    }

    public void setListener(SearchListener listener) {
        this.listener = listener;
    }

    public interface SearchListener {
        void onItemClick(int pos);
    }

    public static class SearchItemViewHolder extends RecyclerView.ViewHolder {

        private SearchItemBinding binding;

        public SearchItemViewHolder(@NonNull SearchItemBinding searchItemBinding, SearchListener listener) {
            super(searchItemBinding.getRoot());
            binding = searchItemBinding;

            binding.getRoot().setOnClickListener(view -> listener.onItemClick(getAdapterPosition()));
        }

        public void setDetails(SearchItem.Data details) {
            binding.setData(details);
        }
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SearchItemBinding binding = SearchItemBinding.inflate(inflater, parent, false);
        return new SearchItemViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemViewHolder holder, int position) {
        holder.setDetails(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
