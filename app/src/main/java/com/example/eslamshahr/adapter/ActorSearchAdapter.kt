package com.example.eslamshahr.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.eslamshahr.R
import com.example.eslamshahr.model.Result

class ActorSearchAdapter(

    private var results: MutableList<Result>
) : RecyclerView.Adapter<ActorSearchAdapter.MovieVieHolder>() {
    lateinit var castName: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVieHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_actor_result, parent, false)
        return MovieVieHolder(view)
    }

    override fun getItemCount(): Int = results.size

    fun appendActor(results: List<Result>) {
        this.results.addAll(results)
        notifyItemRangeInserted(this.results.size, results.size - 1)
    }

    override fun onBindViewHolder(holder: MovieVieHolder, position: Int) {
        holder.bind(results[position])
    }

    fun updatePerson(results: List<Result>) {
        this.results = results as MutableList<Result>
        notifyDataSetChanged()
    }

    inner class MovieVieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val suggest: Button = itemView.findViewById(R.id.suggests)
//        private val recyclerView: Button = itemView.findViewById(R.id.actor_search_recyclerView)

        fun bind(result: Result) {
            suggest.text = result.name
            suggest.setOnClickListener{
//                recyclerView.visibility = View.GONE
               castName = suggest.text.toString()
            }
        }
    }
}