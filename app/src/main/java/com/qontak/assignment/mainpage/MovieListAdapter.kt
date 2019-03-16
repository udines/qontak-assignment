package com.qontak.assignment.mainpage

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.qontak.assignment.GlideApp
import com.qontak.assignment.R
import com.qontak.assignment.datamodel.Movie
import kotlinx.android.synthetic.main.card_movie_grid.view.*

class MovieListAdapter(private val context: Context, private val movieList : ArrayList<Movie>):
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.card_movie_grid, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.title.text = movieList[p1].title
        GlideApp.with(context)
            .load("https://image.tmdb.org/t/p/w300" + movieList[p1].poster_path)
            .into(p0.image)
    }

}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var title: TextView = itemView.card_movie_grid_title
    var image: ImageView = itemView.card_movie_grid_image
}