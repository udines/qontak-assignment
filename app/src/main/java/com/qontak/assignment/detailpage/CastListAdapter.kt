package com.qontak.assignment.detailpage

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.qontak.assignment.Constants
import com.qontak.assignment.GlideApp
import com.qontak.assignment.R
import com.qontak.assignment.datamodel.Cast
import kotlinx.android.synthetic.main.card_movie_cast.view.*

class CastListAdapter(private val context: Context, private val castList: List<Cast>) :
    RecyclerView.Adapter<CastViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CastViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.card_movie_cast, p0, false)
        return CastViewHolder(view)
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    override fun onBindViewHolder(p0: CastViewHolder, p1: Int) {
        p0.bind(castList[p1], context)
    }

}

class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(cast: Cast, context: Context) {
        itemView.cardMovieCastCharacter.text = cast.character
        itemView.cardMovieCastName.text = cast.name
        GlideApp.with(context)
            .load(Constants.BASE_URL_IMAGE + "w185" + cast.profilePath)
            .transition(
                DrawableTransitionOptions
                    .withCrossFade()
            )
            .into(itemView.cardMovieCastPhoto)
    }
}