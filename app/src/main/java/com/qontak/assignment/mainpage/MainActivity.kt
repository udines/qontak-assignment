package com.qontak.assignment.mainpage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import com.qontak.assignment.Constants
import com.qontak.assignment.datamodel.Movie
import kotlinx.android.synthetic.main.activity_main.*
import com.qontak.assignment.R
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(), MainView {

    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: MovieListAdapter
    private lateinit var mainPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.qontak.assignment.R.layout.activity_main)
        setSupportActionBar(main_toolbar)

        gridLayoutManager = GridLayoutManager(this, 2)
        main_recycler_view.layoutManager = gridLayoutManager

        mainPresenter = MainActivityPresenter(this, MainActivityInteractor())

        //get initial movie list
        mainPresenter.getData(1, Constants.FILTER_POPULAR)
    }

    override fun showList(movieList: ArrayList<Movie>) {
        runOnUiThread {
            // Stuff that updates the UI
            adapter = MovieListAdapter(this, movieList)
            main_recycler_view.adapter = adapter
        }
    }

    override fun onDestroy() {
        mainPresenter.onDestroy()
        super.onDestroy()
    }

}

interface MainView {
    fun showList(movieList: ArrayList<Movie>)
}
