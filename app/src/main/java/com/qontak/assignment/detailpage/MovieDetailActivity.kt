package com.qontak.assignment.detailpage

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
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
    fun changeFavIconColor(isFavorite: Boolean)
    fun showProgress()
    fun hideProgress()
}

class MovieDetailActivity : AppCompatActivity(), DetailView {

    private lateinit var detailPresenter: DetailActivityPresenter
    private var mediaId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //instantiate Presenter
        detailPresenter = DetailActivityPresenter(this, DetailActivityInteractor())

        handleButtonOnclick()

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

    override fun changeFavIconColor(isFavorite: Boolean) {
        if (isFavorite) {
            movieDetailImageFavorite.setImageResource(R.drawable.ic_favorite_pink_32dp)
        } else {
            movieDetailImageFavorite.setImageResource(R.drawable.ic_favorite_grey_32dp)
        }
    }

    override fun onStart() {
        //get movie data based on id from previous activity
        val id = intent.getIntExtra("movieId", 0)
        this.mediaId = id
        detailPresenter.getData(id)
        detailPresenter.getCreditData(id)
        super.onStart()
    }

    override fun onDestroy() {
        detailPresenter.onDestroy()
        super.onDestroy()
    }


    override fun showProgress() {
        runOnUiThread {
            movieDetailProgressbar.visibility = View.VISIBLE
        }
    }

    override fun hideProgress() {
        runOnUiThread {
            movieDetailProgressbar.visibility = View.GONE
        }

    }

    private fun handleButtonOnclick() {
        movieDetailImageFavorite.setOnClickListener {
            Log.d("fav button", "clicked")
            val prefs = getSharedPreferences(Constants.PREF_NAME_AUTH, Context.MODE_PRIVATE)
            val sessionId = prefs.getString(Constants.PREF_KEY_SESSION_ID, null)
            if (sessionId != null) {
                Log.d("session Id", "not null")
                detailPresenter.getAccountDetail(sessionId, true, mediaId)
            }
        }
    }
}
