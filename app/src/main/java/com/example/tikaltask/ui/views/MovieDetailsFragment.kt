package com.example.tikaltask.ui.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tikaltask.Constants
import com.example.tikaltask.R
import com.example.tikaltask.viewModel.MoviesViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_details_frag.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class MovieDetailsFragment : Fragment(){

    private val vm : MoviesViewModel by sharedViewModel()
    companion object {
        const val TAG = "MovieDetailsFragment"
        fun instance() = MovieDetailsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.movie_details_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.movieDetailsChosen.observe({lifecycle}){movie ->
            movie?.backdropPath?.let {
                Picasso
                    .get()
                    .load(Constants.MOVIE_BIG_IMAGE_URL_PREFIX + it)
                    .into(movieBackground)
            }
            movie?.posterPath?.let {
                Picasso
                    .get()
                    .load(Constants.MOVIE_SMALL_IMAGE_URL_PREFIX + it)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(posterIV)
            }
            movie?.title?.let {
                movieTitle.text = it
            }
            movie?.overview?.let {
                overviewTV.text = it
            }
            movie?.voteAverage?.let {
                voteAverageTV.text = it.toString()
            }
        }
    }
}