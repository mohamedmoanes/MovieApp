package com.moanes.myapplication.movieapp.utils.extensions

import androidx.appcompat.widget.AppCompatImageView
import com.moanes.myapplication.movieapp.R
import com.squareup.picasso.Picasso

fun AppCompatImageView.setImageURL(url: String, placeholder: Int= R.drawable.ic_local_movies_24) {
    Picasso.get().load(url).fit().placeholder(placeholder).into(this)
}