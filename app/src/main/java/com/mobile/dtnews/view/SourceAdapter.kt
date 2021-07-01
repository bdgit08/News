package com.mobile.dtnews.view

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.dtnews.ArticleActivity
import com.mobile.dtnews.R
import com.mobile.dtnews.databinding.ItemSourceBinding
import com.mobile.dtnews.model.Source

class SourceAdapter(var list : MutableList<Source> = mutableListOf()) : RecyclerView.Adapter<SourceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_source,parent,false)
        return SourceViewHolder(ItemSourceBinding.bind(view))
    }

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        val source = list[position]
        holder.onBind(source)
    }

    override fun getItemCount(): Int = list.size
}

class SourceViewHolder(private val binding : ItemSourceBinding) : RecyclerView.ViewHolder(binding.root){
    fun onBind(source : Source?){
        binding.source = source
        itemView.setOnClickListener {
            val intent = Intent(itemView.context,ArticleActivity::class.java)
            intent.putExtra(ArticleActivity.SOURCE,source?.id)
            intent.putExtra(ArticleActivity.NAME,source?.name)
            itemView.context.startActivity(intent)
        }
    }
}