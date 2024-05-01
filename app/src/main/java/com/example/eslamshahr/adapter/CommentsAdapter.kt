package com.example.eslamshahr.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.eslamshahr.R

import com.example.eslamshahr.entities.Comment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CommentsAdapter(private var commentList: MutableList<Comment>,

) : RecyclerView.Adapter<CommentsAdapter.MyViewHolder>() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = commentList.size

    fun appendComment(commentList: List<Comment>) {
        this.commentList.addAll(commentList)
        notifyItemRangeInserted(this.commentList.size, commentList.size - 1)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(commentList[position])
    }

    fun updateComment(commentList: List<Comment>) {
        this.commentList = commentList as MutableList<Comment>
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val context: TextView = itemView.findViewById(R.id.context)
        private val username: TextView = itemView.findViewById(R.id.username)


        fun bind(comment: Comment) {
            username.text = comment.username
            context.text = comment.context
        }
    }


}

