package com.example.tikaltask.repository.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.v7.util.DiffUtil
import com.google.gson.annotations.SerializedName

data class MoviesResponse(val results : List<Movie>,
                          val total_pages : Int)

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "index")
    var index: Int,

    @ColumnInfo(name = "id")
    @SerializedName(value = "id")
    var id: Int,

    @ColumnInfo(name = "vote_count")
    @SerializedName(value = "vote_count")
    var voteCount: Int? = null,

    @ColumnInfo(name = "vote_average")
    @SerializedName(value = "vote_average")
    var voteAverage: Float? = null,

    @ColumnInfo(name = "title")
    @SerializedName(value = "title")
    var title: String? = null,

    @ColumnInfo(name = "poster_path")
    @SerializedName(value = "poster_path")
    var posterPath: String? = null,

    @ColumnInfo(name = "original_language")
    @SerializedName(value = "original_language")
    var originalLanguage: String? = null,

    @ColumnInfo(name = "backdrop_path")
    @SerializedName(value = "backdrop_path")
    var backdropPath: String? = null,

    @ColumnInfo(name = "overview")
    @SerializedName(value = "overview")
    var overview: String? = null,

    @ColumnInfo(name = "release_date")
    @SerializedName(value = "release_date")
    var releaseDate: String? = null
) {

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Movie> = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.index == newItem.index
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.index == newItem.index
            }
        }
    }

}