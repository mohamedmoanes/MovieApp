package com.moanes.myapplication.movieapp.data.repositories

import com.moanes.myapplication.movieapp.data.models.BaseResponse
import com.moanes.myapplication.movieapp.data.network.Remote

interface MoviesRepo {
    suspend fun getMovies(page: Int): BaseResponse
}

class MoviesRepoImpl(private val remote: Remote) : MoviesRepo {
    override suspend fun getMovies(page: Int): BaseResponse {
        return remote.getMovies(page, "popularity.desc")
    }
}