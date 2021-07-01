package com.mobile.dtnews.view

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.dtnews.R
import com.mobile.dtnews.WebActivity
import com.mobile.dtnews.databinding.ItemArticleBinding
import com.mobile.dtnews.model.Article
import com.mobile.dtnews.paging.PageListAdapter

class ArticleAdapter : PageListAdapter<Article>(POST_COMPARATOR) {
    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areContentsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean =
                oldItem.title == newItem.title
        }
    }

    override fun getLayoutItem(viewType: Int): Int = R.layout.item_article

    override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder = ArticleViewHolder(
        ItemArticleBinding.bind(view))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val article = getItem(position)
        (holder as ArticleViewHolder).onBind(article)
    }

    inner class ArticleViewHolder(private val binding : ItemArticleBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(article : Article?){
            binding.article = article
            Glide.with(itemView.context).load(article?.image).into(binding.ivArticle);
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, WebActivity::class.java)
                intent.putExtra(WebActivity.URL,article?.url)
                itemView.context.startActivity(intent)
            }

        }
    }
}