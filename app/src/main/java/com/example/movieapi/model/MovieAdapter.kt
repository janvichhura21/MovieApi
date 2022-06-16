package com.example.movieapi.model

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapi.activity.DetailActivity
import com.example.movieapi.databinding.MovieItemsBinding

class MovieAdapter(val context:Context):RecyclerView.Adapter<MovieAdapter.MovieView>() {
    lateinit var items:((MovieList) -> Unit)
    private var movieitems=ArrayList<MovieList>()

    fun getMovieItems( movieItems:List<MovieList>){
        this.movieitems=movieItems as ArrayList<MovieList>
        notifyDataSetChanged()
    }
    class MovieView(val binding: MovieItemsBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieView {
        return MovieView(MovieItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MovieView, position: Int) {
        val data=movieitems[position]
        val imageView="https://image.tmdb.org/t/p/w500"+ data.backdropPath
        holder.binding.mName.text=data.title
        Glide.with(context)
            .load(imageView)
            .into(holder.binding.imageCircle)

        holder.itemView.setOnClickListener {
            items.invoke(data)
        }
        holder.itemView.setOnClickListener {
            val intent=Intent(context,DetailActivity::class.java)
            intent.putExtra("id",data.id)
            intent.putExtra("images",imageView)
            intent.putExtra("names",data.originalTitle)
            intent.putExtra("Overview",data.overview)
            intent.putExtra("ReleaseDate",data.releaseDate)
            intent.putExtra("Genre",data.genreIds.toString())
            intent.putExtra("ratings",data.voteCount)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
       return movieitems.size
    }
}