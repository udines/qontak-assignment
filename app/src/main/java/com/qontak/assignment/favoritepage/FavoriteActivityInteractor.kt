package com.qontak.assignment.favoritepage

class FavoriteActivityInteractor {

    interface OnFinishedListener {
        fun onGetMoviesSuccess(jsonData: String)
        fun onAddMovieSuccess()
        fun onRemoveMovieSuccess()
        fun onCheckMovieSuccess(exist: Boolean)
        fun onResultFail()
    }

    fun getFavoriteMovies(onFinishedListener: OnFinishedListener) {
        //get movie from local database
    }

    fun addMovie(onFinishedListener: OnFinishedListener) {
        //add movie to local database
    }

    fun removeMovie(onFinishedListener: OnFinishedListener) {
        //delete movie from local database
    }

    fun checkIsMovieFavorited(onFinishedListener: OnFinishedListener) {
        //check whether movie is favorite or not
    }
}