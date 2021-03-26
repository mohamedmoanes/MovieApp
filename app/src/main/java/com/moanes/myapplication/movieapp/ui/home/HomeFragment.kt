package com.moanes.myapplication.movieapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.moanes.myapplication.movieapp.R
import com.moanes.myapplication.movieapp.adapters.MoviesAdapter
import com.moanes.myapplication.movieapp.data.models.Movie
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var adapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleError()
        handleNoData()
        handleProgress()

        initMoviesList()
        handlePagination()
        handleRefresh()

        handleMoviesLiveData()

        viewModel.loadNextPage()
    }

    private fun initMoviesList() {
        adapter = MoviesAdapter(::openDetails)
        moviesListRV.layoutManager = LinearLayoutManager(requireContext())
        moviesListRV.adapter = adapter
    }

    private fun handlePagination() {
        moviesListRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = recyclerView.layoutManager!!.childCount
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                val firstVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0
                ) {
                    viewModel.loadNextPage()
                }
            }
        })
    }

    private fun handleMoviesLiveData() {
        viewModel.moviesLiveData.observe(viewLifecycleOwner, {
            if (null != it && this::adapter.isInitialized)
                adapter.submitList(it.toMutableList())
        })
    }

    private fun handleRefresh() {
        swipeToRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun openDetails(movie: Movie) {

    }

    private fun handleProgress() {
        viewModel.showLoading.observe(viewLifecycleOwner, {
            swipeToRefresh.isRefreshing = it
        })
    }

    private fun handleError() {
        viewModel.errorLiveData.observe(viewLifecycleOwner, {
            view?.let { it1 -> Snackbar.make(it1, it, Snackbar.LENGTH_SHORT).show() }
        })
    }

    private fun handleNoData() {
        viewModel.showNoData.observe(viewLifecycleOwner, {
            if (it)
                noData.visibility = View.VISIBLE
            else
                noData.visibility = View.GONE
        })
    }
}