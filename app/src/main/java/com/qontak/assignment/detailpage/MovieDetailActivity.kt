package com.qontak.assignment.detailpage

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import com.qontak.assignment.R
import com.qontak.assignment.datamodel.MovieDetail

import kotlinx.android.synthetic.main.activity_movie_detail.*

interface DetailView {
    fun displayMovieDetail(movieDetail: MovieDetail)
}

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
