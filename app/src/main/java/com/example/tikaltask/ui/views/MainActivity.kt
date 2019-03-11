package com.example.tikaltask.ui.views

import android.arch.lifecycle.Observer
import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.tikaltask.R
import com.example.tikaltask.viewModel.MoviesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val viewModel : MoviesViewModel by viewModel()

    companion object {
        const val TAG = "MainActivity"
        const val MovieDetailsFragmentTAG = "MovieDetailsFragmentTAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_main)

        val frag = supportFragmentManager.findFragmentByTag(MovieDetailsFragmentTAG)
        if(frag != null) {
            supportFragmentManager.beginTransaction().remove(frag).commit()
        }

        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, MovieDetailsFragment.instance() ,MovieDetailsFragmentTAG)
                .commit()
        }

        viewModel.getNetworkLiveData().observe(this, Observer {
            Snackbar
                .make(mainView, it!!, Snackbar.LENGTH_LONG)
                .show()
        })

    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentByTag(MovieDetailsFragmentTAG)
        if(frag != null && resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            supportFragmentManager
                .beginTransaction()
                .remove(frag)
                .commit()
        } else {
            super.onBackPressed()
        }
    }

}
