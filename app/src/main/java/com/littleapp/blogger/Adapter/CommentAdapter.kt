package com.littleapp.blogger.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.littleapp.blogger.model.Comment
import com.littleapp.blogger.R
import com.littleapp.blogger.unit.DATA
import com.littleapp.blogger.unit.VOID
import com.littleapp.blogger.databinding.ItemBloggerCommentBinding
import java.text.SimpleDateFormat
import java.util.Locale

class CommentAdapter(private val context: Context, var comments: ArrayList<Comment>) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    private val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    private val outputDateFormat = SimpleDateFormat("dd/MM/yyyy K:mm a", Locale.ENGLISH)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBloggerCommentBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentComment = comments[position]

        val formattedDate = try {
            val date = inputDateFormat.parse(currentComment.published ?: DATA.EMPTY)
            if (date != null) outputDateFormat.format(date) else currentComment.published ?: DATA.EMPTY
        } catch (_: Exception) {
            currentComment.published ?: DATA.EMPTY
        }

        with(holder.binding) {
            name.text = currentComment.name ?: DATA.EMPTY
            date.text = formattedDate
            comment.text = currentComment.comment ?: DATA.EMPTY

            try {
                VOID.loadImage(context, currentComment.profileImage, image)
            } catch (_: Exception) {
                image.setImageResource(R.drawable.ic_person)
            }
        }
    }

    override fun getItemCount(): Int = comments.size

    class ViewHolder(val binding: ItemBloggerCommentBinding) : RecyclerView.ViewHolder(binding.root)
}