package com.example.tikaltask.ui.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tikaltask.Constants
import com.example.tikaltask.R
import com.example.tikaltask.repository.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_cell.view.*

class MoviesPageListAdapter(val itemClickListener : (Movie) -> Unit) :
    PagedListAdapter<Movie, MoviesPageListAdapter.MovieCellViewHolder>(Movie.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCellViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.movie_cell,parent,false)
        return MovieCellViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieCellViewHolder, position: Int) {
        holder.itemView.movieTitle.text = getItem(position)?.title
        holder.itemView.setOnClickListener {
            getItem(position)?.let {
                itemClickListener(it)
            }
        }
        getItem(position)?.posterPath?.let{
            Picasso
                .get()
                .load(Constants.MOVIE_SMALL_IMAGE_URL_PREFIX + it)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.itemView.moviePoster)
        }

    }

    class MovieCellViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

}