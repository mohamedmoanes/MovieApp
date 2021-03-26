package com.moanes.myapplication.movieapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.moanes.myapplication.movieapp.R
import com.moanes.myapplication.movieapp.utils.views.ProgressDialog
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModel()
    private var loadingDialog: ProgressDialog? = null

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

        handleMoviesLiveData()
    }

    fun handleMoviesLiveData() {
        viewModel.moviesLiveData.observe(viewLifecycleOwner, {

        })
    }

    fun handleProgress() {
        viewModel.showLoading.observe(viewLifecycleOwner, {

            swipeToRefresh?.let { swipeRefreshLayout ->
                swipeRefreshLayout.isRefreshing = it
            }

            if (it)
                loadingDialog?.show()
            else
                loadingDialog?.dismiss()
        })
    }

    fun handleError() {
        viewModel.errorLiveData.observe(viewLifecycleOwner, {
            view?.let { it1 -> Snackbar.make(it1, it, Snackbar.LENGTH_SHORT).show() }
        })
    }

    fun handleNoData() {
        viewModel.showNoData.observe(viewLifecycleOwner, {
            if (it)
                noData.visibility = View.VISIBLE
            else
                noData.visibility = View.GONE
        })
    }

    override fun onPause() {
        super.onPause()
        if (loadingDialog != null && loadingDialog?.isShowing!!) {
            loadingDialog?.dismiss()
        }
    }

    override fun onStop() {
        super.onStop()
        loadingDialog = null
    }
}