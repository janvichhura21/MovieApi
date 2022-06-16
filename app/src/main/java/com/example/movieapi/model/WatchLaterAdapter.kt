package com.example.movieapi.model

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapi.databinding.WatchListItemsBinding

class WatchLaterAdapter:RecyclerView.Adapter<WatchLaterAdapter.MyView>() {
    var click:Int=0
    private var onItemClickListener:((detail: CheckBox)->Unit)? = null
    fun setOnItemClickListener(onItemClickListener:((detail: CheckBox)->Unit)){
        this.onItemClickListener = onItemClickListener
    }
    class MyView(val binding: WatchListItemsBinding):RecyclerView.ViewHolder(binding.root)
    val diffutil=object : DiffUtil.ItemCallback<MovieList>(){
        override fun areItemsTheSame(oldItem: MovieList, newItem: MovieList): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieList, newItem: MovieList): Boolean {
            return oldItem==newItem
        }

    }
    val differ = AsyncListDiffer(this,diffutil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        return MyView(WatchListItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        val data=differ.currentList[position]
        val imageView="https://image.tmdb.org/t/p/w500"+ data.backdropPath
        Glide.with(holder.itemView)
            .load(imageView)
            .into(holder.binding.watchlistImage)
        holder.binding.title.text=data.originalTitle
        holder.binding.saveBox.setOnClickListener {
            val position=holder.adapterPosition
            click=position
            onItemClickListener?.invoke(holder.binding.saveBox)

        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}