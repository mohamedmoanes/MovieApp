package com.moanes.myapplication.movieapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.moanes.myapplication.movieapp.TestCoroutineRule
import com.moanes.myapplication.movieapp.data.models.Movie
import com.moanes.myapplication.movieapp.data.repositories.MoviesRepo
import com.moanes.myapplication.movieapp.getMovies
import com.moanes.myapplication.movieapp.getMoviesEmpty
import com.moanes.myapplication.movieapp.ui.home.HomeViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class HomeViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var moviesRepo: MoviesRepo

    @MockK
    private lateinit var loadingObserver: Observer<Boolean>

    @MockK
    private lateinit var noDataObserver: Observer<Boolean>

    @MockK
    private lateinit var errorsObserver: Observer<String>

    @MockK
    private lateinit var moviesObserver: Observer<List<Movie>>

    private lateinit var subject: HomeViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        subject = HomeViewModel(moviesRepo)
    }

    @Test
    fun `getMovies success`() {
        // given
        val page: Int = 1
        coEvery { moviesRepo.getMovies(page) } returns getMovies()

        subject.moviesLiveData.observeForever(moviesObserver)
        subject.showLoading.observeForever(loadingObserver)
        subject.showNoData.observeForever(noDataObserver)

        //when
        subject.loadNextPage()

        // then
        verify { loadingObserver.onChanged(true) }
        coVerify { moviesRepo.getMovies(page) }
        verify { noDataObserver.onChanged(false) }
        verify { moviesObserver.onChanged(getMovies().results) }
        verify { loadingObserver.onChanged(false) }
    }

    @Test
    fun `getMovies Empty`() {
        // given
        val page: Int = 1
        coEvery { moviesRepo.getMovies(page) } returns getMoviesEmpty()

        subject.moviesLiveData.observeForever(moviesObserver)
        subject.showLoading.observeForever(loadingObserver)
        subject.showNoData.observeForever(noDataObserver)

        //when
        subject.loadNextPage()

        // then
        verify { loadingObserver.onChanged(true) }
        coVerify { moviesRepo.getMovies(page) }
        verify { noDataObserver.onChanged(true) }
        verify { moviesObserver.onChanged(getMoviesEmpty().results) }
        verify { loadingObserver.onChanged(false) }
    }

    @Test
    fun `getMovies failure`() {
        // given
        val page: Int = 1
        coEvery { moviesRepo.getMovies(page) } throws IOException()

        subject.errorLiveData.observeForever(errorsObserver)
        subject.showLoading.observeForever(loadingObserver)

        //when
        subject.loadNextPage()

        // then
        verify { loadingObserver.onChanged(true) }
        verify { loadingObserver.onChanged(false) }
        verify { errorsObserver.onChanged(IOException().localizedMessage) }

    }
}