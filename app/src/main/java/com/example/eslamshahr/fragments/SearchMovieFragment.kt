package com.example.eslamshahr.fragments


//class SearchMovieFragment : Fragment() {
//    lateinit var query: String
//
//    private lateinit var searchMoviesAdapter: MoviesAdapter
//    private lateinit var searchMoviesLayoutMgr: LinearLayoutManager
//
//    private var searchMoviesPage = 1
//
//    private var binding: FragmentSearchMovieBinding? = null
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding = FragmentSearchMovieBinding.bind(view)
//
//
//
//
//
//        searchMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
//        searchMoviesLayoutMgr = LinearLayoutManager(
//            requireContext(),
//            LinearLayoutManager.HORIZONTAL,
//            false
//        )
//
//        binding!!.resultsMovie.layoutManager = searchMoviesLayoutMgr
//        searchMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
//        binding!!.resultsMovie.adapter = searchMoviesAdapter
//
//        searchMovie()
//    }
//
//    private fun searchMovie() {
//        binding!!.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                this@SearchMovieFragment.query = binding!!.searchView.query.toString()
//                Repository.searchMovie(
//                    query!!,
//                    searchMoviesPage,
//                    ::onSearchMoviesFetched,
//                    ::onError
//                )
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return true
//            }
//
//        })
//
//    }
//
//    private fun attachSearchMoviesOnScrollListener() {
//        binding!!.resultsMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                val totalItemCount = searchMoviesLayoutMgr.itemCount
//                val visibleItemCount = searchMoviesLayoutMgr.childCount
//                val firstVisibleItem = searchMoviesLayoutMgr.findFirstVisibleItemPosition()
//
//                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
//                    binding!!.resultsMovie.removeOnScrollListener(this)
//                    searchMoviesPage++
//                    searchMovie()
//                }
//            }
//        })
//    }
//
//    private fun onSearchMoviesFetched(movies: List<Movie>) {
//        searchMoviesAdapter.appendMovies(movies)
//        attachSearchMoviesOnScrollListener()
//    }
//
//    private fun onError() {
//        Toast.makeText(requireContext(), getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
//    }
//
//    private fun showMovieDetails(movie: Movie) {
//        val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
//        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
//        intent.putExtra(MOVIE_POSTER, movie.posterPath)
//        intent.putExtra(MOVIE_TITLE, movie.title)
//        intent.putExtra(MOVIE_RATING, movie.rating)
//        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
//        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
//        startActivity(intent)
//    }
//}