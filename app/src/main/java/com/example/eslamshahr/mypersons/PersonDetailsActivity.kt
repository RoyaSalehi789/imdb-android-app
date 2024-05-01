package com.example.eslamshahr.mypersons

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.eslamshahr.R
import com.example.eslamshahr.databinding.ActivityMovieDetailsBinding
import com.example.eslamshahr.databinding.ActivityPersonDetailsBinding
import com.example.eslamshahr.mymovies.*

const val PERSON_PROFILE = "extra_person_profile"
const val PERSON_NAME = "extra_person_name"
const val PERSON_DEPARTMENT = "extra_person_department"
const val PERSON_POPULARITY = "extra_person_popularity"
const val PERSON_KNOWN_FOR = "extra_person_known_for"


class PersonDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityPersonDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }
    }

    private fun populateDetails(extras: Bundle) {
        extras.getString(PERSON_PROFILE)?.let { profilePath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$profilePath")
                .transform(CenterCrop())
                .into(binding.personBackdrop)
        }

        extras.getString(PERSON_PROFILE)?.let { profilePath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$profilePath")
                .transform(CenterCrop())
                .into(binding.personPoster)
        }

        binding.personName.text = extras.getString(PERSON_NAME, "")
        binding.department.text = extras.getString(PERSON_DEPARTMENT, "")
        binding.popularity.text = extras.getDouble(PERSON_POPULARITY, 0.0).toString()
    }
}