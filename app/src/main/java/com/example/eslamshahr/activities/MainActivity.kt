package com.example.eslamshahr.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.eslamshahr.HebDatabase
import com.example.eslamshahr.IDao
import com.example.eslamshahr.R
import com.example.eslamshahr.databinding.ActivityMainBinding
import com.example.eslamshahr.fragments.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao: IDao = HebDatabase.getInstance(this).iDao

        binding.bottomNavigationView.setOnItemSelectedListener {

            if (it.itemId == R.id.icon_home) {
                val homeFragment = Homefragment()
                replaceFragment(homeFragment)
            } else if (it.itemId == R.id.icon_group) {
                val artistsFragment = ArtistsFragment()
                replaceFragment(artistsFragment)

            } else if (it.itemId == R.id.icon_movies) {
                val moviesFragment = MoviesFragment()
                replaceFragment(moviesFragment)
            } else if (it.itemId == R.id.icon_filter) {
                val filterFragment = FilterFragment()
                replaceFragment(filterFragment)
            } else if (it.itemId == R.id.icon_profile) {
                val profileFragment = ProfileFragment()
                replaceFragment(profileFragment)
            }
            true
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView2, fragment)
        transaction.commit()
    }
}
