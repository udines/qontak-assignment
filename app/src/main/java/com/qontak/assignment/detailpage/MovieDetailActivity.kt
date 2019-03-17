package com.qontak.assignment.detailpage

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
            movie_detail_title.text = movieDetail.title
            movie_detail_overview.text = movieDetail.overview
            movie_detail_subtitle.text = movieDetail.tagline

            GlideApp.with(this)
                .load("https://image.tmdb.org/t/p/w300" + movieDetail.poster_path)
                .into(movie_detail_poster_image)
        }

    }

    override fun onDestroy() {
        detailPresenter.onDestoy()
        super.onDestroy()
    }
}
