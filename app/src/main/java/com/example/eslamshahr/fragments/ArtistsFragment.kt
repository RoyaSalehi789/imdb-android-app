package com.example.eslamshahr.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eslamshahr.adapter.ActorsAdapter
import com.example.eslamshahr.adapter.PopularActorsAdapter
import com.example.eslamshahr.model.Result
import com.example.eslamshahr.R
import com.example.eslamshahr.databinding.FragmentArtistsBinding
import com.example.eslamshahr.mypersons.*

import com.example.eslamshahr.repositories.Repository


class ArtistsFragment : Fragment(R.layout.fragment_artists) {

    private var binding: FragmentArtistsBinding? = null

    lateinit var query: String


    private lateinit var searchArtistsAdapter: ActorsAdapter
    private lateinit var searchArtistsLayoutMgr: LinearLayoutManager

    private lateinit var popularArtistsAdapter: PopularActorsAdapter
    private lateinit var popularArtistsLayoutMgr: LinearLayoutManager

    private var popularArtistsPage = 1
    private var searchArtistsPage = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArtistsBinding.bind(view)

        popularArtistsLayoutMgr = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        searchArtistsAdapter = ActorsAdapter(mutableListOf()) { result -> showPersonDetails(result) }
        searchArtistsLayoutMgr = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding!!.popularMovies.layoutManager = popularArtistsLayoutMgr
        popularArtistsAdapter = PopularActorsAdapter(mutableListOf()) { result -> showPersonDetails(result) }
        binding!!.popularMovies.adapter = popularArtistsAdapter

        binding!!.searchRecyclerView.layoutManager = searchArtistsLayoutMgr
        searchArtistsAdapter = ActorsAdapter(mutableListOf()) { result -> showPersonDetails(result) }
        binding!!.searchRecyclerView.adapter = searchArtistsAdapter

        binding!!.searchRecyclerView.visibility = View.GONE

        searchActor()
        getPopularPerson()
    }

    private fun searchActor() {
        binding!!.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding!!.searchRecyclerView.visibility = View.VISIBLE

                binding!!.nestedScrollView.visibility = View.GONE
                this@ArtistsFragment.query = binding!!.searchView.query.toString()
                Repository.actorSearch(
                    query!!,
                    searchArtistsPage,
                    ::onSearchArtistsFetched,
                    ::onError
                )
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

    }

    private fun getPopularPerson() {
        Repository.popularPerson(
            popularArtistsPage,
            ::onPopularArtistsFetched,
            ::onError
        )
    }

    private fun attachSearchArtistsOnScrollListener() {
        binding!!.searchRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = searchArtistsLayoutMgr.itemCount
                val visibleItemCount = searchArtistsLayoutMgr.childCount
                val firstVisibleItem = searchArtistsLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    binding!!.searchRecyclerView.removeOnScrollListener(this)
                    searchArtistsPage++
                    searchActor()
                }
            }
        })
    }

    private fun attachPopularArtistsOnScrollListener() {
        binding!!.popularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularArtistsLayoutMgr.itemCount
                val visibleItemCount = popularArtistsLayoutMgr.childCount
                val firstVisibleItem = popularArtistsLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    binding!!.popularMovies.removeOnScrollListener(this)
                    popularArtistsPage++
                    getPopularPerson()
                }
            }
        })
    }

    private fun onSearchArtistsFetched(results: List<Result>) {
        searchArtistsAdapter.appendPersons(results)
        attachSearchArtistsOnScrollListener()
    }

    private fun onPopularArtistsFetched(results: List<Result>) {
        popularArtistsAdapter.appendPersons(results)
        attachPopularArtistsOnScrollListener()
    }

    private fun onError() {
        Toast.makeText(requireContext(), getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    private fun showPersonDetails(result: Result) {
        val intent = Intent(requireContext(), PersonDetailsActivity::class.java)
        intent.putExtra(PERSON_PROFILE, result.profile_path)
        intent.putExtra(PERSON_NAME, result.name)
        intent.putExtra(PERSON_POPULARITY, result.popularity)
        startActivity(intent)
    }
}