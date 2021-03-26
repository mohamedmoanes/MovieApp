package com.moanes.myapplication.movieapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moanes.myapplication.movieapp.data.models.Movie
import com.moanes.myapplication.movieapp.data.repositories.MoviesRepo
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class HomeViewModel(private val moviesRepo: MoviesRepo) : ViewModel(), CoroutineScope {

    // Coroutine's background job
    private val job = Job()

    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    val showLoading = MutableLiveData<Boolean>()
    val showNoData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()

    val moviesLiveData = MutableLiveData<MutableList<Movie>>()
    var mCurrentPage = 0
    var mTotalPage = 1

    init {
        moviesLiveData.value = ArrayList<Movie>()
    }

    override fun onCleared() {
        super.onCleared()
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
    }


    private fun getMovies() {
        launch {
            try {
                showLoading.postValue(true)

                val result = withContext(Dispatchers.IO) {
                    moviesRepo.getMovies(mCurrentPage)
                }
                if (mCurrentPage == 1)
                    showNoData.postValue(result.results.isEmpty())

                result.results.let { moviesLiveData.value?.addAll(it) }
                moviesLiveData.value = moviesLiveData.value

                mCurrentPage = result.page
                mTotalPage = result.totalPages

                showLoading.postValue(false)

            } catch (exception: Exception) {
                showLoading.postValue(false)
                errorLiveData.postValue(
                    exception.localizedMessage
                )
            }
        }
    }

    fun loadNextPage() {
        if (mCurrentPage < mTotalPage) {
            mCurrentPage++
            getMovies()
        }
    }

    fun refresh() {
        mCurrentPage = 1
        moviesLiveData.value?.clear()
        getMovies()
    }
}