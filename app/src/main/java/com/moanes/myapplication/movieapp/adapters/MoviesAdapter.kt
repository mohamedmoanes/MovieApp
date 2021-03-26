package com.moanes.myapplication.movieapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.moanes.myapplication.movieapp.R
import com.moanes.myapplication.movieapp.data.models.Movie
import com.moanes.myapplication.movieapp.utils.extensions.setImageURL

class MoviesAdapter(private val open: (data: Movie) -> Unit) :
    ListAdapter<Movie, MoviesAdapter.ViewHolder>(MoviesItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        if (!movie.posterPath.isNullOrBlank())
            holder.poster.setImageURL("https://image.tmdb.org/t/p/original${movie.posterPath}")

        holder.title.text = movie.originalTitle

        holder.rate.text = movie.voteAverage.toString()

        holder.itemView.setOnClickListener {
            open(movie)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val poster: ShapeableImageView = view.findViewById(R.id.poster)
        val title: MaterialTextView = view.findViewById(R.id.title)
        val rate: MaterialTextView = view.findViewById(R.id.rate)
    }


    class MoviesItemDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}