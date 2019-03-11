package com.example.tikaltask.ui.views

import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.example.tikaltask.R
import com.example.tikaltask.repository.model.Movie
import com.example.tikaltask.ui.adapter.MoviesPageListAdapter
import com.example.tikaltask.viewModel.MoviesViewModel
import kotlinx.android.synthetic.main.movies_list_fragment.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class MoviesListFragment : Fragment(){

    private val vm : MoviesViewModel by sharedViewModel()
    companion object {
        val TAG = "MoviesListFragment"
        val CURRENT_POSITION =  "CURRENT_POSITION"
        fun instance() = MoviesListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.movies_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MoviesPageListAdapter {
            if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                activity?.supportFragmentManager?.beginTransaction()?.
                    replace(R.id.container, MovieDetailsFragment.instance(), MainActivity.MovieDetailsFragmentTAG)?.commit()
            }
            vm.updateMovieWasChosen(it)
        }

        vm.movieListLiveDate.observe(this, Observer<PagedList<Movie>> { pagedList ->
            adapter.submitList(pagedList)
        })
        rv.adapter = adapter

        rv.afterMeasured {
            val cellHeight = resources.getDimension(R.dimen.movie_cell_height)
            val columnCount = ( (rv.measuredWidth + (cellHeight/2) )/ cellHeight).toInt()
            rv.layoutManager = GridLayoutManager(context, columnCount)
            val position  = savedInstanceState?.getInt(CURRENT_POSITION,0) ?: 0
            rv.scrollToPosition(position)
        }
        vm.connectivityLiveData.observe(this, Observer {active ->
            if(active == true && !vm.lastKnownNetworkStatus) {
                vm.lastKnownNetworkStatus = true
                vm.start()
                vm.movieListLiveDate.observe(this, Observer<PagedList<Movie>> { pagedList ->
                    adapter.submitList(pagedList)
                })
            } else {
                vm.lastKnownNetworkStatus = active!!
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val positionOfChild = rv.getChildAdapterPosition(rv.getChildAt(0))
        outState.putInt(CURRENT_POSITION,positionOfChild)
    }


    private inline fun View.afterMeasured(crossinline f: () -> Unit){
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (measuredWidth > 0 && measuredHeight > 0) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    f()
                }
            }
        })
    }

}