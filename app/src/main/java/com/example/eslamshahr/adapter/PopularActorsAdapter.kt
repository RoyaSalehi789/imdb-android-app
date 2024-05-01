package com.example.eslamshahr.adapter

import android.view.LayoutInflater
import com.example.eslamshahr.model.Result
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.eslamshahr.R

class PopularActorsAdapter(

    private var results: MutableList<Result>,
    private val onPersonClick: (result: Result) -> Unit
) : RecyclerView.Adapter<PopularActorsAdapter.MovieVieHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVieHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_actor, parent, false)
        return MovieVieHolder(view)
    }

    override fun getItemCount(): Int = results.size

    fun appendPersons(results: List<Result>) {
        val size = results.size
        this.results.addAll(results)
        notifyItemRangeInserted(size, results.size - 1)
    }

    override fun onBindViewHolder(holder: MovieVieHolder, position: Int) {
        holder.bind(results[position])
    }

    fun updatePerson(results: List<Result>) {
        this.results = results as MutableList<Result>
        notifyDataSetChanged()
    }

    inner class MovieVieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var result1: Result

        private val poster: ImageView = itemView.findViewById(R.id.actor_poster)
        private val nameOfActor: TextView = itemView.findViewById(R.id.name_of_actor)
        private val popularity: TextView = itemView.findViewById(R.id.textView8)

        fun bind(result: Result) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${result.profile_path}")
                .transform(CenterCrop())
                .into(poster)
            nameOfActor.text = result.name
            popularity.text = result.popularity.toString()
            itemView.setOnClickListener { onPersonClick.invoke(result) }
            this.result1 = result
        }
    }
}