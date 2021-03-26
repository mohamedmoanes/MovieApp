package com.moanes.myapplication.movieapp.data.network

import com.moanes.myapplication.movieapp.data.models.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Remote {
    @GET("discover/movie")
    suspend fun getMovies(@Query("page") page: Int, @Query("sort_by") sortBy: String): BaseResponse
}