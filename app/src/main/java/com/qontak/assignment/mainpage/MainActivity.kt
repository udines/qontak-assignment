package com.qontak.assignment.mainpage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.qontak.assignment.Constants
import com.qontak.assignment.datamodel.Movie
import kotlinx.android.synthetic.main.activity_main.*
import com.qontak.assignment.R
import kotlinx.android.synthetic.main.content_main.*


interface MainView {
    fun showList(movieList: ArrayList<Movie>)
}

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: MovieListAdapter
    private lateinit var mainPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.qontak.assignment.R.layout.activity_main)
        setSupportActionBar(main_toolbar)

        //instantiate layout manager and attach to recycler view
        gridLayoutManager = GridLayoutManager(this, 2)
        main_recycler_view.layoutManager = gridLayoutManager
        main_recycler_view.isNestedScrollingEnabled = false

        //instantiate Presenter
        mainPresenter = MainActivityPresenter(this, MainActivityInteractor())

        //handle filter button on clicks
        handleButtonOnClicks()
    }

    override fun showList(movieList: ArrayList<Movie>) {
        runOnUiThread {
            // Stuff that updates the UI
            adapter = MovieListAdapter(this, movieList)
            main_recycler_view.adapter = adapter
        }
    }

    //handle search input
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_search_view, menu)

        val item = menu?.findItem(R.id.action_search)
        main_search_view.setMenuItem(item)
        main_search_view.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {

                    //use query data as input to the getData method
                    mainPresenter.getData(1, query)
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
        mainPresenter.getData(1, Constants.FILTER_POPULAR)
        super.onStart()
    }

    override fun onDestroy() {
        mainPresenter.onDestroy()
        super.onDestroy()
    }

    private fun handleButtonOnClicks() {
        //get movie data based on filter
        //page is set to 1 as initial
        main_button_filter_popular.setOnClickListener {
            mainPresenter.getData(1, Constants.FILTER_POPULAR)
        }
        main_button_filter_top_rated.setOnClickListener {
            mainPresenter.getData(1, Constants.FILTER_TOP_RATED)
        }
    }
}
