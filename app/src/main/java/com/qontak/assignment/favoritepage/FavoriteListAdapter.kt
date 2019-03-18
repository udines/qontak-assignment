package com.qontak.assignment.favoritepage

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.qontak.assignment.Constants
import com.qontak.assignment.GlideApp
import com.qontak.assignment.R
import com.qontak.assignment.datamodel.Movie
import com.qontak.assignment.detailpage.MovieDetailActivity
import kotlinx.android.synthetic.main.card_movie_favorite.view.*

class FavoriteListAdapter(private val context: Context, private val movieList: List<Movie>) :
    RecyclerView.Adapter<CardFavoriteViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CardFavoriteViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.card_movie_favorite, p0, false)
        val viewHolder = CardFavoriteViewHolder(view, context)

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

    override fun onBindViewHolder(p0: CardFavoriteViewHolder, p1: Int) {
        p0.bind(movieList[p1])
    }

}

class CardFavoriteViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {
    fun bind(movie: Movie) {
        itemView.cardMovieFavoriteTitle.text = movie.title
        GlideApp.with(context)
            .load(Constants.BASE_URL_IMAGE + "w300" + movie.posterPath)
            .transition(
                DrawableTransitionOptions
                    .withCrossFade()
            )
            .into(itemView.cardMovieFavoritePoster)
    }
}