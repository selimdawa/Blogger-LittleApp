package com.littleapp.blogger.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.littleapp.blogger.Model.Comment
import com.littleapp.blogger.R
import com.littleapp.blogger.Unit.DATA
import com.littleapp.blogger.Unit.VOID
import com.littleapp.blogger.databinding.ItemBloggerCommentBinding
import java.text.SimpleDateFormat

class CommentAdapter(private val context: Context, var comments: ArrayList<Comment>) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBloggerCommentBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = comments[position]
        val id = list.id
        val name = list.name
        val published = list.published
        val comment = list.comment
        val image = list.profileImage
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val dateFormat2 = SimpleDateFormat("dd/MM/yyyy K:mm a")
        var formattedDate = DATA.EMPTY

        try {
            val date = dateFormat.parse(published)
            formattedDate = dateFormat2.format(date)
        } catch (e: Exception) {
            formattedDate = published ?: DATA.EMPTY
            e.printStackTrace()
        }

        holder.binding.name.text = name
        holder.binding.date.text = formattedDate
        holder.binding.comment.text = comment

        try {
            VOID.Glide(context, image, holder.binding.image)
        } catch (e: Exception) {
            holder.binding.image.setImageResource(R.drawable.ic_person)
        }
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    inner class ViewHolder(val binding: ItemBloggerCommentBinding) : RecyclerView.ViewHolder(binding.root)
}