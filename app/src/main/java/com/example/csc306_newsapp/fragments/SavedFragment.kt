package com.example.csc306_newsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.csc306_newsapp.NewsAdapter
import com.example.csc306_newsapp.NewsData
import com.example.csc306_newsapp.R

/**
 * This class is responsible for the Saved News page
 */
class SavedFragment : Fragment() {

    //instance of the NewsAdapter
    private lateinit var mAdapter: NewsAdapter

    /**
     * Function to inflate the fragment layout
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    /**
     * Function to populate the recyclerView
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateNews()

        val recyclerView = view.findViewById<View>(R.id.recycler_savedList) as RecyclerView // Bind to the recyclerview in the layout
        val layoutManager = LinearLayoutManager(context) // Get the layout manager
        recyclerView.layoutManager = layoutManager
        mAdapter = NewsAdapter(view)
        recyclerView.adapter = mAdapter

    }

    /**
     * Function which parses data from the API and returns populated JSONObjects
     */
    private fun populateNews(){
        val queue = Volley.newRequestQueue(context)
        val apikey = getString(R.string.api_key)
        val url =
            "https://gnews.io/api/v4/search?q=example&token=$apikey&lang=en&country=us&max=10" // api url
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<NewsData>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = NewsData(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getJSONObject("source").getString("name"),
                        newsJsonObject.getString("description"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("image")
                    )
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)
            },
            {

            }
        )
        queue.add(jsonObjectRequest)
    }
}