package com.example.eslamshahr.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.SeekBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.eslamshahr.adapter.MoviesAdapter
import com.example.eslamshahr.R
import com.example.eslamshahr.databinding.FragmentFilterBinding
import com.example.eslamshahr.model.Movie
import com.example.eslamshahr.mymovies.*
import com.example.eslamshahr.repositories.Repository


class FilterFragment : Fragment(R.layout.fragment_filter) {

    var startPoint = 0
    var endPoint = 0
    var regionIndex = 0
    var genreIndex=0
    lateinit var year : String

    private lateinit var filterMovieAdapter: MoviesAdapter
    private lateinit var filterMoviesLayoutMgr: LinearLayoutManager

    private var filterMoviesPage = 1

    private var binding: FragmentFilterBinding? = null

    private val regionsById = arrayOf("DE","ES","FR","GB","IN","IT","IR","JP","KR","US")
    private val regions = arrayOf("Germany","Spain","France","England","India","Italy","Iran","Japan","Korea","United states")

    val genreById = arrayOf(12,35,80,10751,36,9648,10749,10770,53,10752)
    val genre = arrayOf("Adventure","Comedy","Crime","Family","History","Mystery","Romance","TV Movie","Thriller","War")


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFilterBinding.bind(view)

        filterMovieAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        filterMoviesLayoutMgr = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding!!.filterRecyclerView.layoutManager = filterMoviesLayoutMgr
        filterMovieAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        binding!!.filterRecyclerView.adapter = filterMovieAdapter

        getFilterMovies()

        binding!!.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding!!.volume.text = progress.toString()
                year = binding!!.volume.text.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    startPoint = seekBar.progress
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    endPoint = seekBar.progress
                }
            }

        })


        val arrayAdapter = ArrayAdapter<String>(requireContext(),R.layout.spinner_item, regions)
        binding!!.regionSpinner.adapter = arrayAdapter
        binding!!.regionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                for(index in 0..regions.size ){
                    if(binding!!.regionSpinner.selectedItem.toString() == regions[index]){

                        regionIndex = index
                        break;
                    }
                }

                for(index in 0..genre.size ){
                    if(binding!!.genreSpinner.selectedItem.toString() == genre[index]){

                        genreIndex = index
                        break;
                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        val array2Adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, genre)
        binding!!.genreSpinner.adapter = array2Adapter
        binding!!.genreSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }


            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

    }

    private fun getFilterMovies() {
        binding!!.apply.setOnClickListener{
            Repository.filterMovie(
                regionsById[regionIndex],
                filterMoviesPage,
                year.toInt(),
                genreById[genreIndex].toString(),
                ::onFilterMoviesFetched,
                ::onError
            )
        }
    }

    private fun attachFilterMoviesOnScrollListener() {
        binding!!.filterRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = filterMoviesLayoutMgr.itemCount
                val visibleItemCount = filterMoviesLayoutMgr.childCount
                val firstVisibleItem = filterMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    binding!!.filterRecyclerView.removeOnScrollListener(this)
                    filterMoviesPage++
                    getFilterMovies()
                }
            }
        })
    }

    private fun onFilterMoviesFetched(movies: List<Movie>) {
        filterMovieAdapter.appendMovies(movies)
        attachFilterMoviesOnScrollListener()
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
        startActivity(intent)
    }
}