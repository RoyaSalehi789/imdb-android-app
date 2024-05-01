package com.example.eslamshahr.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eslamshahr.HebDatabase
import com.example.eslamshahr.R
import com.example.eslamshahr.adapter.SelectWatchlistAdapter
import com.example.eslamshahr.databinding.ActivityMakeWatchlistBinding
import com.example.eslamshahr.model.Movie
import com.example.eslamshahr.model.Storage
import com.example.eslamshahr.repositories.MovieRepository
import com.example.eslamshahr.repositories.Repository
import com.example.eslamshahr.viewModel.MakeWatchlistViewModel
import com.example.eslamshahr.viewModel.MakeWatchlistViewModelFactory

class MakeWatchListActivity : AppCompatActivity() {

    private lateinit var selectAdapter: SelectWatchlistAdapter
    private lateinit var selectLayoutMgr: LinearLayoutManager

    private lateinit var makeWatchlistViewModel: MakeWatchlistViewModel


    private var selectPage = 1

    lateinit var binding: ActivityMakeWatchlistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMakeWatchlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val application = requireNotNull(this).application

        val dao = HebDatabase.getInstance(application).iDao

        val repository = MovieRepository(dao)

        val factory = MakeWatchlistViewModelFactory(repository, application)

        makeWatchlistViewModel = ViewModelProvider(this, factory).get(MakeWatchlistViewModel::class.java)

        binding.lifecycleOwner = this

        selectAdapter = SelectWatchlistAdapter(mutableListOf()) { movie -> selectItem(movie) }
        selectLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        binding.watchSelectRecyclerView.layoutManager = selectLayoutMgr
        selectAdapter = SelectWatchlistAdapter(mutableListOf()) { movie -> selectItem(movie) }
        binding.watchSelectRecyclerView.adapter = selectAdapter

        getTopRatedMovies()

        binding.createWatchlist.setOnClickListener {
            val name = binding.watchlistName.text.toString()
            makeWatchlistViewModel.createButton(name)
            binding.watchlistName.text = null
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getTopRatedMovies() {
        Repository.getTopRatedMovies(
            selectPage,
            ::onTopRatedMoviesFetched,
            ::onError
        )
    }

    private fun attachTopRatedMoviesOnScrollListener() {
        binding.watchSelectRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = selectLayoutMgr.itemCount
                val visibleItemCount = selectLayoutMgr.childCount
                val firstVisibleItem = selectLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    binding.watchSelectRecyclerView.removeOnScrollListener(this)
                    selectPage++
                    getTopRatedMovies()
                }
            }
        })
    }

    private fun onTopRatedMoviesFetched(movies: List<Movie>) {
        selectAdapter.appendMovies(movies)
        attachTopRatedMoviesOnScrollListener()
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    private fun selectItem(movie: Movie) {
        Storage.getInstance().movies.add(movie)
    }
}