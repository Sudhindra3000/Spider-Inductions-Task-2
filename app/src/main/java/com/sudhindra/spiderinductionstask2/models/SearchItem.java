package com.sudhindra.spiderinductionstask2.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchItem {

    private ArrayList<Data> data;

    public class Data {

        private String title;
        private String description;
        private String center;
        @SerializedName("nasa_id")
        private String nasaId;

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getCenter() {
            return center;
        }

        public String getNasaId() {
            return nasaId;
        }
    }

    public ArrayList<Data> getData() {
        return data;
    }
}
