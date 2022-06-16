package com.example.movieapi.model

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapi.databinding.FavMovieBinding

class FavMovieAdapter:RecyclerView.Adapter<FavMovieAdapter.FavView>() {
    var click:Int=0
    private var onItemClickListener:((detail: CheckBox)->Unit)? = null
    fun setOnItemClickListener(onItemClickListener:((detail: CheckBox)->Unit)){
        this.onItemClickListener = onItemClickListener
    }
    private var onItemAddClickListener:((detail: MovieList)->Unit)? = null
    fun setOnItemAddClickListener(onItemAddClickListener:((detail: MovieList)->Unit)){
        this.onItemAddClickListener = onItemAddClickListener
    }
    class FavView(val binding: FavMovieBinding) :RecyclerView.ViewHolder(binding.root)
    val diffutil=object : DiffUtil.ItemCallback<MovieList>(){
        override fun areItemsTheSame(oldItem: MovieList, newItem: MovieList): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieList, newItem: MovieList): Boolean {
            return oldItem==newItem
        }

    }

    val differ = AsyncListDiffer(this,diffutil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavView {
        return FavView(FavMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: FavView, position: Int) {
        val data=differ.currentList[position]
        val imageView="https://image.tmdb.org/t/p/w500"+ data.backdropPath
        Glide.with(holder.itemView)
            .load(imageView)
            .into(holder.binding.favImages)
        holder.binding.favTxt.text=data.originalTitle
        holder.binding.favCheck.setOnClickListener {
            val position=holder.adapterPosition
            click=position
            onItemClickListener?.invoke(holder.binding.favCheck)

        }
        holder.itemView.setOnClickListener {
            onItemAddClickListener?.invoke(data)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}