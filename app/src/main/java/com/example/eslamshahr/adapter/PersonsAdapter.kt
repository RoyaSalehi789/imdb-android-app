package com.example.eslamshahr.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.eslamshahr.R
import com.example.eslamshahr.model.Cast

class PersonsAdapter(

    private var casts: MutableList<Cast>,
    private val onPersonClick: (cast: Cast) -> Unit
) : RecyclerView.Adapter<PersonsAdapter.MovieVieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVieHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieVieHolder(view)
    }

    override fun getItemCount(): Int = casts.size

    fun appendPersons(casts: List<Cast>) {
        val size = casts.size
        this.casts.addAll(casts)
        notifyItemRangeInserted(size, casts.size - 1)
    }

    override fun onBindViewHolder(holder: MovieVieHolder, position: Int) {
        holder.bind(casts[position])
    }

    fun updatePerson(casts: List<Cast>) {
        this.casts = casts as MutableList<Cast>
        notifyDataSetChanged()
    }

    inner class MovieVieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)

        fun bind(cast: Cast) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${cast.profile_path}")
                .transform(CenterCrop())
                .into(poster)
            itemView.setOnClickListener { onPersonClick.invoke(cast) }
        }
    }
}