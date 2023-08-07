package com.example.csc306_newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.csc306_newsapp.databinding.ActivityMainBinding
import com.example.csc306_newsapp.fragments.ExploreFragment
import com.example.csc306_newsapp.fragments.NewsFragment
import com.example.csc306_newsapp.fragments.SavedFragment
import com.example.csc306_newsapp.models.NewsModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding : ActivityMainBinding

    /**
     * Inflating the main page and setting up navigation bar functionality
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(NewsFragment())
        mAuth = Firebase.auth
        val currentUser = mAuth.currentUser

        //val recyclerView : RecyclerView = findViewById(R.id.recycler_newsList)
        //recyclerView.layoutManager = LinearLayoutManager(this)
        //val tabLayout = findViewById<TabLayout>(R.id.tabs)
        //val viewPager = findViewById<ViewPager2>(R.id.mainViewPager)

        if(currentUser != null){
            supportFragmentManager.beginTransaction()
                .add(R.id.main_frameLayout, NewsFragment()).addToBackStack(null)
                .commit()

        }else{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            //supportFragmentManager.beginTransaction()
            //    .add(R.id.main_container, LoginFragment())
            //    .commit()
        }
/*
        val tabTitles = resources.getStringArray(R.array.categories)
        viewPager.adapter = TabAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = tabTitles[0]
                1 -> tab.text = tabTitles[1]
                2 -> tab.text = tabTitles[2]
            }
        }.attach()*/

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){

                R.id.home -> replaceFragment(NewsFragment())
                R.id.explore -> replaceFragment(ExploreFragment())
                R.id.saved -> replaceFragment(SavedFragment())
                R.id.settings -> replaceFragment(SettingsActivity.SettingsFragment())

                else ->{
                }
            }
            true
        }
        //mAdaptor = NewsAdapter()
        //recyclerView.adapter = mAdaptor
    }


/*
    // now in a our main activity we made api calls
    fun fetchNews(){

        val queue = Volley.newRequestQueue(this)
        val url =
            "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json" // api url
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            {
                val newsJsonArray = it.getJSONArray("articies")
                val newsArray = ArrayList<NewsData>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = NewsData(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("source"),
                        newsJsonObject.getString("description"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("imageurl")
                    )
                    newsArray.add(news)
                }
                mAdaptor.updateData(newsArray)
            },
            {

            }
        )
        queue.add(jsonObjectRequest)
    }*/

    /**
     * Navigation functionality method
     */
    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_frameLayout,fragment)
        fragmentTransaction.commit()
    }

    /*override fun navigateFrag(fragment: Fragment, addToStack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, fragment)
        if (addToStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }*/
}