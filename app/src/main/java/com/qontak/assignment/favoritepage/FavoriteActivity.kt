package com.qontak.assignment.favoritepage

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import com.qontak.assignment.Constants
import com.qontak.assignment.R
import com.qontak.assignment.datamodel.Movie
import com.qontak.assignment.mainpage.MovieListAdapter

import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.content_favorite.*

interface FavoriteView {
    fun showList(movieList: ArrayList<Movie>)
    fun showProgressBar()
    fun hideProgressBar()
}

class FavoriteActivity : AppCompatActivity(), FavoriteView {

    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: MovieListAdapter
    private lateinit var favoritePresenter: FavoriteActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //instantiate layout manager and attach to recycler view
        gridLayoutManager = GridLayoutManager(this, 2)
        favoriteRecyclerView.layoutManager = gridLayoutManager
        favoriteRecyclerView.isNestedScrollingEnabled = false

        //instantiate Presenter
        favoritePresenter = FavoriteActivityPresenter(this, FavoriteActivityInteractor())
    }

    override fun showList(movieList: ArrayList<Movie>) {
        Log.d("total favs", movieList.size.toString())
        runOnUiThread {
            // Stuff that updates the UI
            adapter = MovieListAdapter(this, movieList)
            favoriteRecyclerView.adapter = adapter
        }
    }

    override fun showProgressBar() {
        runOnUiThread {
            favoriteProgressBar.visibility = View.VISIBLE
        }
    }

    override fun hideProgressBar() {
        runOnUiThread {
            favoriteProgressBar.visibility = View.GONE
        }
    }

    override fun onStart() {

        val prefs = getSharedPreferences(Constants.PREF_NAME_AUTH, Context.MODE_PRIVATE)
        val sessionId = prefs.getString(Constants.PREF_KEY_SESSION_ID, null)
        if (sessionId != null) {
            Log.d("session id", sessionId)
            favoritePresenter.getAccountDetail(sessionId)
        }
        super.onStart()
    }

    override fun onDestroy() {
        favoritePresenter.onDestroy()
        super.onDestroy()
    }
}
