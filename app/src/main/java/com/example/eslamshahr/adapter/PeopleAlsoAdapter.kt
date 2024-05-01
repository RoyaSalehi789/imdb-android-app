package com.example.eslamshahr.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.eslamshahr.R
import com.example.eslamshahr.entities.FavoriteList
import com.example.eslamshahr.model.Movie
import com.example.eslamshahr.model.Storage

class PeopleAlsoAdapter(

    private var favoriteLists: MutableList<FavoriteList>,
    private val onMovieClick: (favoriteList: FavoriteList) -> Unit
) : RecyclerView.Adapter<PeopleAlsoAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_people_also, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = favoriteLists.size

    fun appendMovies(favoriteLists: List<FavoriteList>) {
        this.favoriteLists.addAll(favoriteLists)
        notifyItemRangeInserted(
            this.favoriteLists.size,
            favoriteLists.size - 1
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(favoriteLists[position])
    }

    fun updateMovies(favoriteList: List<FavoriteList>) {
        this.favoriteLists = favoriteLists as MutableList<FavoriteList>
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val fileName: TextView = itemView.findViewById(R.id.file_name)
        private  val user: TextView = itemView.findViewById(R.id.user_of__people_also)

        fun bind(favoriteList: FavoriteList) {
            fileName.text = favoriteList.favoriteList_name
            user.text = favoriteList.username
            itemView.setOnClickListener { onMovieClick.invoke(favoriteList) }
        }
    }
}