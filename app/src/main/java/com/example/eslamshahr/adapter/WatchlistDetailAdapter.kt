package com.example.eslamshahr.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.eslamshahr.R
import com.example.eslamshahr.entities.MovieEntity

class WatchlistDetailAdapter(

    private var movies: MutableList<MovieEntity>,
) : RecyclerView.Adapter<WatchlistDetailAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_select_watchlist, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    fun appendMovies(movies: List<MovieEntity>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(
            this.movies.size,
            movies.size - 1
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun updateMovies(movies: List<MovieEntity>) {
        this.movies = movies as MutableList<MovieEntity>
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.imageView5)
        private val movieName: TextView = itemView.findViewById(R.id.movie_name)
        private val rating: RatingBar = itemView.findViewById(R.id.ratingBar)


        fun bind(movie: MovieEntity) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .transform(CenterCrop())
                .into(poster)
            movieName.text = movie.title
            rating.rating = movie.rating!! / 2
        }
    }
}