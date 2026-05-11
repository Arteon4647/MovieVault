package com.example.movievault.domain.usecase

import androidx.paging.PagingSource
import com.example.movievault.domain.model.Movie
import com.example.movievault.domain.repository.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): PagingSource<Int, Movie> {
        return repository.getPopularMoviesPagingSource()
    }
}