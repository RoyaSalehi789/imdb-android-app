package com.example.eslamshahr.mymovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.eslamshahr.HebDatabase
import com.example.eslamshahr.adapter.PersonsAdapter
import com.example.eslamshahr.R
import com.example.eslamshahr.activities.FolderDetailActivity
import com.example.eslamshahr.adapter.CommentsAdapter
import com.example.eslamshahr.adapter.PeopleAlsoAdapter
import com.example.eslamshahr.databinding.ActivityMovieDetailsBinding
import com.example.eslamshahr.entities.FavoriteList
import com.example.eslamshahr.entities.MovieEntity
import com.example.eslamshahr.model.Cast
import com.example.eslamshahr.model.Storage
import com.example.eslamshahr.mypersons.*
import com.example.eslamshahr.repositories.MovieRepository
import com.example.eslamshahr.repositories.Repository
import com.example.eslamshahr.viewModel.MovieDetailViewModel
import com.example.eslamshahr.viewModel.MovieDetailViewModelFactory
import kotlinx.android.synthetic.main.item_select_watchlist.*

const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RATING = "extra_movie_rating"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"
const val MOVIE_ID = "extra_movie_id"
const val MOVIE_COUNT = "extra_movie_count"

class MovieDetailsActivity : AppCompatActivity() {


    lateinit var binding: ActivityMovieDetailsBinding


    private lateinit var getCastMoviesAdapter: PersonsAdapter
    private lateinit var getCastMoviesLayoutMgr: LinearLayoutManager

    private lateinit var getFavoritesLayoutMgr: LinearLayoutManager

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val application = requireNotNull(this).application

        val dao = HebDatabase.getInstance(application).iDao

        val repository = MovieRepository(dao)

        val factory = MovieDetailViewModelFactory(repository, application)

        movieDetailViewModel = ViewModelProvider(this, factory).get(MovieDetailViewModel::class.java)

        binding.lifecycleOwner = this

        backdrop = findViewById(R.id.person_backdrop)
        poster = findViewById(R.id.person_poster)
        title = findViewById(R.id.person_name)
        rating = findViewById(R.id.movie_rating)
        releaseDate = findViewById(R.id.movie_release_date)
        overview = findViewById(R.id.person_biography)


        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }

        val movieId1 = intent.extras!!.getInt(MOVIE_ID)
        val  title1 = intent.extras!!.getString(MOVIE_TITLE)
        val overview1 = intent.extras!!.getString(MOVIE_OVERVIEW)
        val posterPath1 = intent.extras!!.getString(MOVIE_POSTER)
        val backdropPath1 = intent.extras!!.getString(MOVIE_BACKDROP)
        val rating1 = intent.extras!!.getFloat(MOVIE_RATING)
        val count1 = intent.extras!!.getInt(MOVIE_COUNT)
        val releaseDate1 = intent.extras!!.getString(MOVIE_RELEASE_DATE)


        movieDetailViewModel.insertMovie(MovieEntity(movieId1, title1, overview1, posterPath1, backdropPath1, rating1,count1, releaseDate1))

        getCastMoviesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        getFavoritesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.people.layoutManager = getFavoritesLayoutMgr

        movieDetailViewModel.favoriteListLiveData.observe(this ) {
            binding.people.adapter = PeopleAlsoAdapter(it.toMutableList()) { favoriteList -> showFolderDetails(favoriteList) }
        }

        getCastMoviesAdapter =
            PersonsAdapter(mutableListOf()) { cast -> showPersonDetails(cast) }
        binding.casts.layoutManager = getCastMoviesLayoutMgr
        binding.casts.adapter = getCastMoviesAdapter

        getCredits()

        binding.comments.layoutManager = LinearLayoutManager(this)

        movieDetailViewModel.commentsLiveData.observe(this ) {
            binding.comments.adapter = CommentsAdapter(it.toMutableList())
        }

        binding.ratingBar2.rating = 0f
        binding.ratingBar2.stepSize = .5f
        binding.ratingBar2.setOnRatingBarChangeListener{ ratingBar, rating, fromUser ->
            movieDetailViewModel.updateRating(MovieEntity(movieId1, title1, overview1,
                posterPath1, backdropPath1, rating1,count1, releaseDate1), rating * 2f)
        }

         val rate = movieDetailViewModel.getRate(movieId1)
        binding.ratingBar3.rating = rate / 2f

        binding.sendButton.setOnClickListener {
            val comment = binding.write.text.toString()
            movieDetailViewModel.sendButton(comment)
            binding.write.text = null
        }

        binding.notMark.setOnClickListener{
            binding.notMark.isEnabled = false
            binding.marked.visibility = View.VISIBLE
            movieDetailViewModel
            movieDetailViewModel.addToWatchList(MovieEntity(movieId1, title1, overview1, posterPath1, backdropPath1, rating1,count1, releaseDate1))
        }
    }

    private fun getCredits() {
        val movie_id: Int = intent.extras!!.getInt(MOVIE_ID, 0)
        Repository.getCredits(
            movie_id,
            ::onCreditsFetched,
            ::onError
        )
    }

    private fun attachCreditsOnScrollListener() {
        binding.casts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = getCastMoviesLayoutMgr.itemCount
                val visibleItemCount = getCastMoviesLayoutMgr.childCount
                val firstVisibleItem = getCastMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    binding.casts.removeOnScrollListener(this)
                    getCredits()
                }
            }
        })
    }

    private fun onCreditsFetched(casts: List<Cast>) {
        getCastMoviesAdapter.appendPersons(casts)
        attachCreditsOnScrollListener()
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    private fun showPersonDetails(cast: Cast) {
        val intent = Intent(this, PersonDetailsActivity::class.java)
        intent.putExtra(PERSON_PROFILE, cast.profile_path)
        intent.putExtra(PERSON_NAME, cast.name)
        intent.putExtra(PERSON_DEPARTMENT, cast.known_for_department)
        intent.putExtra(PERSON_POPULARITY, cast.popularity)
        startActivity(intent)
    }

    private fun populateDetails(extras: Bundle) {
        extras.getString(MOVIE_BACKDROP)?.let { backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                .transform(CenterCrop())
                .into(backdrop)
        }

        extras.getString(MOVIE_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(poster)
        }

        title.text = extras.getString(MOVIE_TITLE, "")
        rating.rating = extras.getFloat(MOVIE_RATING, 0f) / 2
        releaseDate.text = extras.getString(MOVIE_RELEASE_DATE, "")
        overview.text = extras.getString(MOVIE_OVERVIEW, "")
        Storage.getInstance().movieId = extras.getInt(MOVIE_ID, 0)
    }

    private fun showFolderDetails(favoriteList: FavoriteList) {
        Storage.getInstance().favoriteListId = favoriteList.favoriteList_id
        val intent = Intent(this, FolderDetailActivity::class.java)
        startActivity(intent)
    }
}