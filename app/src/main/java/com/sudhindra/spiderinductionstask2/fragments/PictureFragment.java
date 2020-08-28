package com.sudhindra.spiderinductionstask2.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.sudhindra.spiderinductionstask2.R;
import com.sudhindra.spiderinductionstask2.apis.APODApi;
import com.sudhindra.spiderinductionstask2.databinding.FragmentPictureBinding;
import com.sudhindra.spiderinductionstask2.models.APOD;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PictureFragment extends Fragment {

    private static final String TAG = "PictureFragment";
    private FragmentPictureBinding binding;

    private MaterialDatePicker picker;

    private APODApi api;

    private String apiKey;

    private Callback<APOD> apodCallback = new Callback<APOD>() {
        @Override
        public void onResponse(Call<APOD> call, Response<APOD> response) {
            if (!response.isSuccessful()) {
                Log.i(TAG, "onResponse: " + response);
                return;
            }

            binding.progressBar.setVisibility(View.GONE);
            binding.scrollView.setVisibility(View.VISIBLE);
            binding.setApod(response.body());
        }

        @Override
        public void onFailure(Call<APOD> call, Throwable t) {
            Toast.makeText(requireContext(), "Failed to fetch Image", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "onFailure: ");
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPictureBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(getString(R.string.apodBaseUrl))
                .build();

        picker = MaterialDatePicker.Builder.datePicker().setTitleText("Pick a Date").build();
        picker.addOnPositiveButtonClickListener(selection -> {
            binding.dateBt.setText(picker.getHeaderText());
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.scrollView.setVisibility(View.GONE);
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date((Long) selection);
            getAPOD(fmt.format(date));
        });

        api = retrofit.create(APODApi.class);

        apiKey = getString(R.string.apiKey);

        getAPODForToday();

        binding.setDate("Today");

        binding.dateBt.setOnClickListener(v -> showDatePicker());
    }

    private void getAPODForToday() {
        Call<APOD> call = api.getAPODForToday(apiKey);
        call.enqueue(apodCallback);
    }

    private void getAPOD(String day) {
        Call<APOD> call = api.getAPOD(day, apiKey);
        call.enqueue(apodCallback);
    }

    private void showDatePicker() {
        picker.show(requireActivity().getSupportFragmentManager(), "picker");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}