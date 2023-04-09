package com.example.recyclerviewtest

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewtest.databinding.ItemModelBinding

class PostAdapter: RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var postList = emptyList<PostModel>()
    class PostViewHolder(val binding: ItemModelBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemModelBinding.inflate(inflater, parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]
        with(holder.binding) {
            rvPostId.text = post.post_id.toString()
            rvPostText.text = post.text
            rvPostTitle.text = post.title
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: DataPosts) {
        postList = list.list
        notifyDataSetChanged()
    }


}