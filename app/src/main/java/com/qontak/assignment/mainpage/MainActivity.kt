package com.qontak.assignment.mainpage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.View
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.qontak.assignment.Constants
import com.qontak.assignment.datamodel.Movie
import kotlinx.android.synthetic.main.activity_main.*
import com.qontak.assignment.R
import kotlinx.android.synthetic.main.content_main.*


interface MainView {
    fun showList(movieList: ArrayList<Movie>, isNextPage: Boolean)
    fun showProgressBar()
    fun hideProgressBar()
    fun showLoadMoreButton()
    fun hideLoadMoreButton()
    fun showRecyclerView()
    fun hideRecyclerView()
}

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: MovieListAdapter
    private lateinit var mainPresenter: MainActivityPresenter
    private var globalMovieList = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.qontak.assignment.R.layout.activity_main)
        setSupportActionBar(mainToolbar)

        //instantiate layout manager and attach to recycler view
        gridLayoutManager = GridLayoutManager(this, 2)
        mainRecyclerView.layoutManager = gridLayoutManager
        mainRecyclerView.isNestedScrollingEnabled = false

        //instantiate Presenter
        mainPresenter = MainActivityPresenter(this, MainActivityInteractor())

        //handle filter button on clicks
        handleButtonOnClicks()
    }

    override fun showList(movieList: ArrayList<Movie>, isNextPage: Boolean) {
        runOnUiThread {
            // Stuff that updates the UI

            if (isNextPage) {
                globalMovieList.addAll(movieList)
                adapter = MovieListAdapter(this, globalMovieList)
                mainRecyclerView.adapter = adapter

                //no need to show recycler view because it is not hidden

            } else {
                globalMovieList = movieList
                adapter = MovieListAdapter(this, globalMovieList)
                mainRecyclerView.adapter = adapter

                //show recycler view after adapter notified
                showRecyclerView()
            }
        }
    }

    override fun showProgressBar() {
        runOnUiThread {
            mainProgressBar.visibility = View.VISIBLE
        }
    }

    override fun hideProgressBar() {
        runOnUiThread {
            mainProgressBar.visibility = View.GONE
        }
    }

    override fun showLoadMoreButton() {
        runOnUiThread {
            mainButtonLoadMore.visibility = View.VISIBLE
        }
    }

    override fun hideLoadMoreButton() {
        runOnUiThread {
            mainButtonLoadMore.visibility = View.GONE
        }
    }

    override fun showRecyclerView() {
        runOnUiThread {
            mainRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun hideRecyclerView() {
        runOnUiThread {
            mainRecyclerView.visibility = View.GONE
        }
    }

    //handle search input
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_search_view, menu)

        val item = menu?.findItem(R.id.action_search)
        mainSearchView.setMenuItem(item)
        mainSearchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {

                    //use query data as input to the getData method
                    mainPresenter.getData(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        return true
    }

    override fun onStart() {
        //get initial movie list
        //page input set to 1 as initial
        mainPresenter.getData(Constants.FILTER_POPULAR)

        super.onStart()
    }

    override fun onDestroy() {
        mainPresenter.onDestroy()
        super.onDestroy()
    }

    private fun handleButtonOnClicks() {
        //get movie data based on filter
        //page is set to 1 as initial
        mainButtonFilterPopular.setOnClickListener {
            mainPresenter.getData(Constants.FILTER_POPULAR)
        }
        mainButtonFilterTopRated.setOnClickListener {
            mainPresenter.getData(Constants.FILTER_TOP_RATED)
        }

        //handle load more button
        mainButtonLoadMore.setOnClickListener {
            mainPresenter.getMoreData()
        }
    }
}
