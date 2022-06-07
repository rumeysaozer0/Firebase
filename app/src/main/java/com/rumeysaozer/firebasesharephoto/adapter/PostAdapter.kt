package com.rumeysaozer.firebasesharephoto.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rumeysaozer.firebasesharephoto.databinding.ItemRowBinding
import com.rumeysaozer.firebasesharephoto.model.Post
import com.squareup.picasso.Picasso

class PostAdapter (val postList : ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.PostHolder>() {
    class PostHolder(val binding: ItemRowBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.comment.text = postList[position].comment
        holder.binding.email.text = postList[position].eamil
        Picasso.get().load(postList[position].downloadUrl).into(holder.binding.image)

    }

    override fun getItemCount(): Int {
     return postList.size
    }
}