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
    fun showList(movieList: ArrayList<Movie>, isNextPage: Boolean)
    fun showProgressBar()
    fun hideProgressBar()
    fun showLoadMoreButton()
    fun hideLoadMoreButton()
    fun showRecyclerView()
    fun hideRecyclerView()
}

class FavoriteActivity : AppCompatActivity(), FavoriteView {

    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: MovieListAdapter
    private lateinit var favoritePresenter: FavoriteActivityPresenter
    private var globalMovieList = ArrayList<Movie>()

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

        //handle filter button on clicks
        handleButtonOnClicks()
    }

    override fun showList(movieList: ArrayList<Movie>, isNextPage: Boolean) {
        runOnUiThread {
            // Stuff that updates the UI

            if (isNextPage) {
                globalMovieList.addAll(movieList)
                adapter = MovieListAdapter(this, globalMovieList)
                favoriteRecyclerView.adapter = adapter

            } else {
                globalMovieList = movieList
                adapter = MovieListAdapter(this, globalMovieList)
                favoriteRecyclerView.adapter = adapter
            }
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

    override fun showLoadMoreButton() {
        runOnUiThread {
//            favoriteButtonLoadMore.visibility = View.VISIBLE
        }
    }

    override fun hideLoadMoreButton() {
        runOnUiThread {
            favoriteButtonLoadMore.visibility = View.GONE
        }
    }

    override fun showRecyclerView() {
        runOnUiThread {
            favoriteRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun hideRecyclerView() {
        runOnUiThread {
//            favoriteRecyclerView.visibility = View.GONE
        }
    }

    override fun onStart() {

        val prefs = getSharedPreferences(Constants.PREF_NAME_AUTH, Context.MODE_PRIVATE)
        val sessionId = prefs.getString(Constants.PREF_KEY_SESSION_ID, null)
        if (sessionId != null) {
            Log.d("session id", "exist")
            favoritePresenter.getAccountDetail(sessionId)
        }
        super.onStart()
    }

    override fun onDestroy() {
        favoritePresenter.onDestroy()
        super.onDestroy()
    }

    private fun handleButtonOnClicks() {
        //handle load more button
        favoriteButtonLoadMore.setOnClickListener {
            favoritePresenter.getMoreData()
        }
    }
}
