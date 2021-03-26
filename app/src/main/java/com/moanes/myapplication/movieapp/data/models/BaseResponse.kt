package com.moanes.myapplication.movieapp.data.models


import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var results: List<Movie>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
)