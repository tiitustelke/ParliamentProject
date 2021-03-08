package com.example.oopproject1.fragments.member

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oopproject1.R
import com.example.oopproject1.data.Comment
import kotlinx.android.synthetic.main.comment_row.view.*

/**
 * @author Tiitus Telke
 * @version 8.3.2021
 * RecycleView adapter for showing comments.
 */
class CommentAdapter: RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    private var commentList = emptyList<Comment>()

    inner class CommentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.comment_row, parent, false))
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val selectedItem = commentList[position]
        holder.itemView.commentText.text = selectedItem.comment
        holder.itemView.nameView.text = selectedItem.userName
        holder.itemView.timeView.text = selectedItem.time
    }

    fun setCommentData(comments: List<Comment>) {
        this.commentList = comments
        notifyDataSetChanged()
    }

}