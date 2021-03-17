package com.sudhindra.spiderinductionstask2.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sudhindra.spiderinductionstask2.R
import com.sudhindra.spiderinductionstask2.activities.DetailsActivity
import com.sudhindra.spiderinductionstask2.adapters.SearchAdapter
import com.sudhindra.spiderinductionstask2.apis.SearchApi
import com.sudhindra.spiderinductionstask2.databinding.FragmentSearchBinding
import com.sudhindra.spiderinductionstask2.models.SearchItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchApi: SearchApi

    private var data = arrayListOf<SearchItem.Data>()
    private var adapter = SearchAdapter(data)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                if (s.isNotBlank()) search(s) else {
                    data.clear()
                    adapter.notifyDataSetChanged()
                }
                return false
            }
        })
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(getString(R.string.searchBaseUrl))
            .build()
        searchApi = retrofit.create(SearchApi::class.java)
        buildRecyclerView()
    }

    private fun search(query: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val searchResult = searchApi.getSearchResult(query, 1)
                searchResult.collection.searchItems.forEach { data.addAll(it.data) }
                withContext(Dispatchers.Main) { adapter.notifyDataSetChanged() }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Failed to Fetch Data", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun buildRecyclerView() {
        adapter.setListener { pos: Int -> showDetails(pos) }
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun showDetails(pos: Int) {
        val intent = Intent(requireContext(), DetailsActivity::class.java)
        intent.putExtra("data", Gson().toJson(data[pos]))
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
//        binding = null
    }

    companion object {
        private const val TAG = "SearchFragment"
    }
}
