package com.qontak.assignment.mainpage

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.qontak.assignment.Constants
import com.qontak.assignment.GlideApp
import com.qontak.assignment.R
import com.qontak.assignment.datamodel.Movie
import com.qontak.assignment.detailpage.MovieDetailActivity
import kotlinx.android.synthetic.main.card_movie_grid.view.*

class MovieListAdapter(private val context: Context, private val movieList: ArrayList<Movie>) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.card_movie_grid, p0, false)
        val viewHolder = ViewHolder(view)

        //handle movie item on click
        view.setOnClickListener {
            val movie = movieList[viewHolder.adapterPosition]
            val intent = Intent(context, MovieDetailActivity::class.java)

            //pass movie id to movie detail activity
            intent.putExtra("movieId", movie.id)
            context.startActivity(intent)
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(movieList[p1], context)
    }

}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var title: TextView = itemView.card_movie_grid_title
    private var image: ImageView = itemView.card_movie_grid_image

    fun bind(movie: Movie, context: Context) {
        title.text = movie.title
        GlideApp.with(context)
            .load(Constants.BASE_URL_POSTER + "w300" + movie.posterPath)
            .transition(
                DrawableTransitionOptions
                    .withCrossFade()
            )
            .into(image)
    }
}