package com.example.eslamshahr.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eslamshahr.adapter.MoviesAdapter
import com.example.eslamshahr.R

import com.example.eslamshahr.databinding.FragmentMoviesBinding
import com.example.eslamshahr.model.Movie
import com.example.eslamshahr.mymovies.*
import com.example.eslamshahr.repositories.Repository


class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private var binding: FragmentMoviesBinding? = null

    private lateinit var horrorMovieAdapter: MoviesAdapter
    private lateinit var horrorMoviesLayoutMgr: LinearLayoutManager

    private lateinit var dramaMovieAdapter: MoviesAdapter
    private lateinit var dramaMoviesLayoutMgr: LinearLayoutManager

    private lateinit var animationMovieAdapter: MoviesAdapter
    private lateinit var animationMoviesLayoutMgr: LinearLayoutManager

    private lateinit var searchMoviesAdapter: MoviesAdapter
    private lateinit var searchMoviesLayoutMgr: LinearLayoutManager

    private var horrorMoviesPage = 1
    private var dramaMoviesPage = 1
    private var animationMoviesPage = 1
    private var searchMoviesPage = 1

    lateinit var query: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoviesBinding.bind(view)

        searchMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        searchMoviesLayoutMgr = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        horrorMovieAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        horrorMoviesLayoutMgr = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        dramaMovieAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        dramaMoviesLayoutMgr = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        animationMovieAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        animationMoviesLayoutMgr = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding!!.searchRecyclerView.layoutManager = searchMoviesLayoutMgr
        searchMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        binding!!.searchRecyclerView.adapter = searchMoviesAdapter

        binding!!.topRatedMovies.layoutManager = horrorMoviesLayoutMgr
        horrorMovieAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        binding!!.topRatedMovies.adapter = horrorMovieAdapter

        binding!!.popularMovies.layoutManager = dramaMoviesLayoutMgr
        dramaMovieAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        binding!!.popularMovies.adapter = dramaMovieAdapter

        binding!!.upcomingMovies.layoutManager = animationMoviesLayoutMgr
        animationMovieAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        binding!!.upcomingMovies.adapter = animationMovieAdapter

        searchMovie()
        getHorrorMovies()
        getDramaMovies()
        getAnimationMovies()
        binding!!.searchRecyclerView.visibility = View.GONE
    }

    private fun searchMovie() {
        binding!!.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding!!.searchRecyclerView.visibility = View.VISIBLE
                binding!!.nestedScrollView.visibility = View.GONE
                this@MoviesFragment.query = binding!!.searchView.query.toString()
                Repository.searchMovie(
                    query!!,
                    searchMoviesPage,
                    ::onSearchMoviesFetched,
                    ::onError
                )
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }

        })

    }

    private fun getHorrorMovies() {
        Repository.getHorror(
            horrorMoviesPage,
            ::onHorrorMoviesFetched,
            ::onError
        )
    }

    private fun getDramaMovies() {
        Repository.dramaMovie(
            dramaMoviesPage,
            ::onDramaMoviesFetched,
            ::onError
        )
    }

    private fun getAnimationMovies() {
        Repository.animationMovie(
            animationMoviesPage,
            ::onAnimationMoviesFetched,
            ::onError
        )
    }

    private fun attachSearchMoviesOnScrollListener() {
        binding!!.searchRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = searchMoviesLayoutMgr.itemCount
                val visibleItemCount = searchMoviesLayoutMgr.childCount
                val firstVisibleItem = searchMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    binding!!.searchRecyclerView.removeOnScrollListener(this)
                    searchMoviesPage++
                    searchMovie()
                }
            }
        })
    }

    private fun attachHorrorMoviesOnScrollListener() {
        binding!!.topRatedMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = horrorMoviesLayoutMgr.itemCount
                val visibleItemCount = horrorMoviesLayoutMgr.childCount
                val firstVisibleItem = horrorMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    binding!!.topRatedMovies.removeOnScrollListener(this)
                    horrorMoviesPage++
                    getHorrorMovies()
                }
            }
        })
    }

    private fun attachDramaMoviesOnScrollListener() {
        binding!!.popularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = dramaMoviesLayoutMgr.itemCount
                val visibleItemCount = dramaMoviesLayoutMgr.childCount
                val firstVisibleItem = dramaMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    binding!!.popularMovies.removeOnScrollListener(this)
                    dramaMoviesPage++
                    getDramaMovies()
                }
            }
        })
    }

    private fun attachAnimationMoviesOnScrollListener() {
        binding!!.upcomingMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = animationMoviesLayoutMgr.itemCount
                val visibleItemCount = animationMoviesLayoutMgr.childCount
                val firstVisibleItem = animationMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    binding!!.upcomingMovies.removeOnScrollListener(this)
                    animationMoviesPage++
                    getAnimationMovies()
                }
            }
        })
    }

    private fun onSearchMoviesFetched(movies: List<Movie>) {
        searchMoviesAdapter.appendMovies(movies)
        attachSearchMoviesOnScrollListener()
    }

    private fun onHorrorMoviesFetched(movies: List<Movie>) {
        horrorMovieAdapter.appendMovies(movies)
        attachHorrorMoviesOnScrollListener()
    }

    private fun onDramaMoviesFetched(movies: List<Movie>) {
        dramaMovieAdapter.appendMovies(movies)
        attachDramaMoviesOnScrollListener()
    }

    private fun onAnimationMoviesFetched(movies: List<Movie>) {
        animationMovieAdapter.appendMovies(movies)
        attachAnimationMoviesOnScrollListener()
    }

    private fun onError() {
        Toast.makeText(requireContext(), getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        intent.putExtra(MOVIE_ID, movie.id)
        intent.putExtra(MOVIE_COUNT, movie.count)
        startActivity(intent)
    }
}