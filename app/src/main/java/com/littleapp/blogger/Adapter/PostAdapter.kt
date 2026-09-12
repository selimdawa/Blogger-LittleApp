package com.littleapp.blogger.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.littleapp.blogger.model.Post
import com.littleapp.blogger.R
import com.littleapp.blogger.unit.CLASS
import com.littleapp.blogger.unit.DATA
import com.littleapp.blogger.unit.VOID
import com.littleapp.blogger.databinding.ItemBloggerBinding
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.Locale

class PostAdapter(private val context: Context, initialPosts: List<Post>) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private var posts: List<Post> = ArrayList(initialPosts)
    private var originalPosts: List<Post> = ArrayList(initialPosts)
    private val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    private val outputDateFormat = SimpleDateFormat("dd/MM/yyyy K:mm a", Locale.ENGLISH)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBloggerBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        val document = Jsoup.parse(post.content ?: DATA.EMPTY)

        try {
            val image = document.select("img").attr("src")
            VOID.loadImage(context, image, holder.binding.image)
        } catch (_: Exception) {
            holder.binding.image.setImageResource(R.color.image_profile)
        }

        val formattedDate = try {
            val date = inputDateFormat.parse(post.published ?: DATA.EMPTY)
            if (date != null) outputDateFormat.format(date) else post.published ?: DATA.EMPTY
        } catch (_: Exception) {
            post.published ?: DATA.EMPTY
        }

        with(holder.binding) {
            title.text = post.title ?: DATA.EMPTY
            description.text = document.text()

            val author = post.authorName ?: DATA.EMPTY
            publishInfo.text = context.getString(R.string.publish_info, author, formattedDate)
        }

        holder.itemView.setOnClickListener {
            VOID.startActivityWithExtra(context, CLASS.BLOGGER_POST_DETAILS, "postId", post.id)
        }
    }

    override fun getItemCount(): Int = posts.size

    fun filter(text: String) {
        val filteredList = if (text.isEmpty()) {
            originalPosts
        } else {
            val result = ArrayList<Post>()
            for (post in originalPosts) {
                if (post.title?.lowercase(Locale.ROOT)?.contains(text.lowercase(Locale.ROOT)) == true) {
                    result.add(post)
                }
            }
            result
        }
        val diffResult = DiffUtil.calculateDiff(PostDiffCallback(posts, filteredList))
        posts = ArrayList(filteredList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun updateList(newList: List<Post>) {
        val diffResult = DiffUtil.calculateDiff(PostDiffCallback(posts, newList))
        posts = ArrayList(newList)
        originalPosts = ArrayList(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    class PostDiffCallback(
        private val oldList: List<Post>,
        private val newList: List<Post>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    class ViewHolder(val binding: ItemBloggerBinding) : RecyclerView.ViewHolder(binding.root)
}