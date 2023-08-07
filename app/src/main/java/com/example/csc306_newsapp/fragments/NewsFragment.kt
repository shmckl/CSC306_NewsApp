package com.example.csc306_newsapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.csc306_newsapp.*

/**
 * This class is responsible for the main news page
 */
class NewsFragment : Fragment() {
    private lateinit var mAdapter: NewsAdapter

    /**
     * Function to inflate the fragment layout
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_news, container, false)
        view.findViewById<Button>(R.id.btn_account).setOnClickListener{
            val intent = Intent (requireActivity(), AccountActivity::class.java)
            requireActivity().startActivity(intent)
        }
        //var newsList = activity as FragmentNavigation
        //newsList.navigateFrag(NewsListFragment(), false)

        //val tabLayout = view.findViewById<TabLayout>(R.id.tabs)
        //val viewPager = view.findViewById<ViewPager2>(R.id.mainViewPager)

        //val tabTitles = resources.getStringArray(R.array.categories)

        //viewPager.adapter = TabsPagerAdapter()
        /*TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                for (i in 0..5) {
                    i -> tab.text = R.array.categories[i]
                }
            }
        }.attach()
        view.findViewById<Button>(R.id.btn_preferences).setOnClickListener{
            val intent = Intent (requireActivity(), CategoryActivity::class.java)
            requireActivity().startActivity(intent)
        }
*/
        // Return the fragment view/layout
        return view
    }

    /**
     * Function to populate the recyclerView
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateNews()

        val recyclerView = view.findViewById<View>(R.id.recycler_newsList) as RecyclerView // Bind to the recyclerview in the layout
        val layoutManager = LinearLayoutManager(context) // Get the layout manager
        recyclerView.layoutManager = layoutManager
        mAdapter = NewsAdapter(view)
        recyclerView.adapter = mAdapter
        
    }
    /*
    private fun populateNews():ArrayList<NewsModel> {
        val list = ArrayList<NewsModel>()

        val newsImages = arrayOf(R.drawable.ic_baseline_error_24, R.drawable.ic_baseline_notifications_24, R.drawable.ic_baseline_settings_24)
        val newsTitles = arrayOf(R.string.test1, R.string.test2, R.string.test3)
        //val newsDescription =
        //val newsSource =

        for(i in 0..2){
            val newsModel = NewsModel()
            newsModel.setTitle(getString(newsTitles[i]))
            newsModel.setImage(newsImages[i])
            list.add(newsModel)
        }
        // Sorting the list
        //list.sortBy { list -> list.modelTitle }
        return list
    }*/

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