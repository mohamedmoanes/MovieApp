package com.moanes.myapplication.movieapp.ui.details

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.moanes.myapplication.movieapp.R
import com.moanes.myapplication.movieapp.utils.extensions.setImageURL
import kotlinx.android.synthetic.main.dialog_fragment_details.*

class DetailsDialogFragment : DialogFragment() {
    private val args: DetailsDialogFragmentArgs by navArgs()
    override fun getTheme(): Int {
        return R.style.FadeDialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.dialog_fragment_details, container, false)

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE);

            dialog?.setCanceledOnTouchOutside(true)

            val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
            val metrics = getWindowMetrics()
            params.width = (metrics.widthPixels * 0.8).toInt()
            params.height = (metrics.heightPixels * 0.7).toInt()
            dialog!!.window!!.attributes = params as WindowManager.LayoutParams
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title.text = args.movie.title
        rate.text = args.movie.voteAverage.toString()
        discretion.text = args.movie.overview
        lang.text = args.movie.originalLanguage
        releaseDate.text = args.movie.releaseDate

        if (!args.movie.backdropPath.isNullOrBlank())
            cover.setImageURL("https://image.tmdb.org/t/p/original${args.movie.backdropPath}")

        if (!args.movie.posterPath.isNullOrBlank())
            poster.setImageURL("https://image.tmdb.org/t/p/original${args.movie.posterPath}")
    }

    private fun getWindowMetrics(): DisplayMetrics {
        // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display = activity?.display
            display?.getRealMetrics(displayMetrics)
        } else {
            @Suppress("DEPRECATION")
            val display = activity?.windowManager?.defaultDisplay
            @Suppress("DEPRECATION")
            display?.getMetrics(displayMetrics)
        }
        return displayMetrics
    }

}