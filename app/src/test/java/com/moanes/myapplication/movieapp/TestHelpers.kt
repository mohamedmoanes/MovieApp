package com.moanes.myapplication.movieapp

import com.moanes.myapplication.movieapp.data.models.BaseResponse
import com.moanes.myapplication.movieapp.data.models.Movie

fun getMovies(): BaseResponse {
    val list = ArrayList<Movie>()
    for (i in 1..10) {
        val movie = Movie(id = i)
        list.add(movie)
    }
    return BaseResponse(1,list,1,10)
}
fun getMoviesEmpty(): BaseResponse {
    val list = ArrayList<Movie>()
    return BaseResponse(1,list,1,10)
}