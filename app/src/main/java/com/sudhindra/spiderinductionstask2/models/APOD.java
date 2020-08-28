package com.sudhindra.spiderinductionstask2.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class APOD {

    private String title;
    private String explanation;
    private String url;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getUrl() {
        return url;
    }

    @BindingAdapter("android:loadImage")
    public static void loadImage(ImageView imageView, String url) {
        Picasso.get()
                .load(url)
                .into(imageView);
    }
}
