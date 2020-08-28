package com.sudhindra.spiderinductionstask2.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchResult {

    private Collection collection;

    public class Collection {

        @SerializedName("items")
        private ArrayList<SearchItem> searchItems;

        public ArrayList<SearchItem> getSearchItems() {
            return searchItems;
        }
    }

    public Collection getCollection() {
        return collection;
    }
}
