package com.sudhindra.spiderinductionstask2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.sudhindra.spiderinductionstask2.R
import com.sudhindra.spiderinductionstask2.apis.APODApi
import com.sudhindra.spiderinductionstask2.databinding.FragmentPictureBinding
import com.sudhindra.spiderinductionstask2.models.APOD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class PictureFragment : Fragment() {

    private lateinit var binding: FragmentPictureBinding

    private lateinit var picker: MaterialDatePicker<Long>

    private lateinit var api: APODApi
    private lateinit var apiKey: String

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
        handleRequest { api.getAPODForToday(apiKey) }
    }

    private fun getAPOD(day: String) = handleRequest { api.getAPOD(day, apiKey) }

    private fun handleRequest(requestCode: suspend () -> APOD) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val res = requestCode()
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    binding.scrollView.visibility = View.VISIBLE
                    binding.apod = res
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Failed to fetch Image", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun showDatePicker() {
        picker.show(requireActivity().supportFragmentManager, "picker")
    }
}
