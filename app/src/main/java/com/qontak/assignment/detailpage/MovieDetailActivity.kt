package com.qontak.assignment.detailpage

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.qontak.assignment.Constants
import com.qontak.assignment.GlideApp
import com.qontak.assignment.R
import com.qontak.assignment.datamodel.Cast

import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.content_movie_detail.*

interface DetailView {
    fun showDetails(
        title: String,
        subtitle: String,
        rating: String,
        status: String,
        date: String,
        country: String,
        language: String,
        posterPath: String
    )

    fun showCastList(castList: List<Cast>)
    fun showCrew(director: String, writers: String)
    fun showStoryline(summary: String, tagline: String, genres: String)
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

    }

    override fun showDetails(
        title: String,
        subtitle: String,
        rating: String,
        status: String,
        date: String,
        country: String,
        language: String,
        posterPath: String
    ) {
        runOnUiThread {
            // Stuff that updates the UI
            movieDetailTitle.text = title
            movieDetailSubtitle.text = subtitle
            movieDetailRating.text = rating
            movieDetailStatus.text = status
            movieDetailDateReleased.text = date
            movieDetailLanguage.text = language
            movieDetailCountry.text = country

            GlideApp.with(this)
                .load(Constants.BASE_URL_IMAGE + "w500" + posterPath)
                .into(movieDetailPosterImage)
        }
    }

    override fun showCastList(castList: List<Cast>) {
        runOnUiThread {
            val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            val adapter = CastListAdapter(this, castList)
            movieDetailRecyclerViewCast.layoutManager = linearLayoutManager
            movieDetailRecyclerViewCast.adapter = adapter
        }
    }

    override fun showCrew(director: String, writers: String) {
        runOnUiThread {
            movieDetailDirector.text = director
            movieDetailWriters.text = writers
        }
    }

    override fun showStoryline(summary: String, tagline: String, genres: String) {
        runOnUiThread {
            movieDetailPlotSummary.text = summary
            movieDetailTagline.text = tagline
            movieDetailGenres.text = genres
        }
    }

    override fun onStart() {
        //get movie data based on id from previous activity
        val id = intent.getIntExtra("movieId", 0)
        detailPresenter.getData(id)
        detailPresenter.getCreditData(id)
        super.onStart()
    }

    override fun onDestroy() {
        detailPresenter.onDestroy()
        super.onDestroy()
    }
}
