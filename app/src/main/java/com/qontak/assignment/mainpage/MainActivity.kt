package com.qontak.assignment.mainpage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.qontak.assignment.Constants
import com.qontak.assignment.datamodel.Movie
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainView {

    private lateinit var linearLayoutManager: GridLayoutManager
    private lateinit var adapter: MovieListAdapter
    private lateinit var mainPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.qontak.assignment.R.layout.activity_main)

        mainPresenter = MainActivityPresenter(this, MainActivityInteractor())

        //get initial movie list
        mainPresenter.getData(1, Constants.FILTER_POPULAR)
    }

    override fun showList(movieList: ArrayList<Movie>) {
        linearLayoutManager = GridLayoutManager(this, 2)
        main_recycler_view.layoutManager = linearLayoutManager
        adapter = MovieListAdapter(this, movieList)
        main_recycler_view.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        mainPresenter.onDestroy()
        super.onDestroy()
    }

}

interface MainView {
    fun showList(movieList: ArrayList<Movie>)
}
