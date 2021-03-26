package com.moanes.myapplication.movieapp.module

import com.moanes.myapplication.movieapp.data.repositories.MoviesRepo
import com.moanes.myapplication.movieapp.data.repositories.MoviesRepoImpl
import org.koin.dsl.module

val repoModule = module {
    factory<MoviesRepo> { MoviesRepoImpl(get()) }
}