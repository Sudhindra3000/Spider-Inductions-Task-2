package com.sudhindra.spiderinductionstask2.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.sudhindra.spiderinductionstask2.R
import com.sudhindra.spiderinductionstask2.apis.APODApi
import com.sudhindra.spiderinductionstask2.databinding.FragmentPictureBinding
import com.sudhindra.spiderinductionstask2.models.APOD
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class PictureFragment : Fragment() {

    private lateinit var binding: FragmentPictureBinding

    private lateinit var picker: MaterialDatePicker<Long>

    private var api: APODApi? = null
    private var apiKey: String? = null

    private val apodCallback: Callback<APOD?> = object : Callback<APOD?> {
        override fun onResponse(call: Call<APOD?>, response: Response<APOD?>) {
            if (!response.isSuccessful) {
                Log.i(TAG, "onResponse: $response")
                return
            }
            binding.progressBar.visibility = View.GONE
            binding.scrollView.visibility = View.VISIBLE
            binding.apod = response.body()
        }

        override fun onFailure(call: Call<APOD?>, t: Throwable) {
            Toast.makeText(requireContext(), "Failed to fetch Image", Toast.LENGTH_SHORT).show()
            Log.i(TAG, "onFailure: ")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(getString(R.string.apodBaseUrl))
            .build()
        picker = MaterialDatePicker.Builder.datePicker().setTitleText("Pick a Date").build()
        picker.addOnPositiveButtonClickListener { selection: Any? ->
            binding.dateBt.text = picker.headerText
            binding.progressBar.visibility = View.VISIBLE
            binding.scrollView.visibility = View.GONE
            val fmt = SimpleDateFormat("yyyy-MM-dd")
            val date = Date((selection as Long?)!!)
            getAPOD(fmt.format(date))
        }
        api = retrofit.create(APODApi::class.java)
        apiKey = getString(R.string.apiKey)
        getAPODForToday()
        binding.date = "Today"
        binding.dateBt.setOnClickListener { showDatePicker() }
    }

    private fun getAPODForToday() {
        val call = api!!.getAPODForToday(apiKey)
        call!!.enqueue(apodCallback)
    }

    private fun getAPOD(day: String) {
        val call = api!!.getAPOD(day, apiKey)
        call!!.enqueue(apodCallback)
    }

    private fun showDatePicker() {
        picker.show(requireActivity().supportFragmentManager, "picker")
    }

    companion object {
        private const val TAG = "PictureFragment"
    }
}
