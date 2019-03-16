package com.qontak.assignment.datamodel

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val vote_average: Float
)