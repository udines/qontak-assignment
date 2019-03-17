package com.qontak.assignment.detailpage

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.qontak.assignment.Constants
import com.qontak.assignment.GlideApp
import com.qontak.assignment.R
import com.qontak.assignment.datamodel.MovieDetail

import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.content_movie_detail.*

interface DetailView {
    fun displayMovieDetail(movieDetail: MovieDetail)
}

class MovieDetailActivity : AppCompatActivity(), DetailView {

    private lateinit var detailPresenter: DetailActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //instantiate Presenter
        detailPresenter = DetailActivityPresenter(this, DetailActivityInteractor())

        //get movie data based on id from previous activity
        val id = intent.getIntExtra("movieId", 0)
        detailPresenter.getData(id)
    }

    override fun displayMovieDetail(movieDetail: MovieDetail) {

        runOnUiThread {
            // Stuff that updates the UI
            movieDetailTitle.text = movieDetail.title
            movieDetailOverview.text = movieDetail.overview
            movieDetailSubtitle.text = movieDetail.tagline

            GlideApp.with(this)
                .load(Constants.BASE_URL_POSTER + "w300" + movieDetail.posterPath)
                .into(movieDetailPosterImage)
        }

    }

    override fun onDestroy() {
        detailPresenter.onDestoy()
        super.onDestroy()
    }
}
