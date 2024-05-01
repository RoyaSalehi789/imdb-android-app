package com.example.eslamshahr.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eslamshahr.HebDatabase
import com.example.eslamshahr.R
import com.example.eslamshahr.activities.FolderDetailActivity
import com.example.eslamshahr.activities.MakeWatchListActivity
import com.example.eslamshahr.activities.StartActivity
import com.example.eslamshahr.adapter.MoviesAdapter
import com.example.eslamshahr.adapter.WatchListAdapter
import com.example.eslamshahr.adapter.WatchlistFilesAdapter
import com.example.eslamshahr.databinding.FragmentProfileBinding
import com.example.eslamshahr.entities.FavoriteList
import com.example.eslamshahr.model.Storage
import com.example.eslamshahr.repositories.MovieRepository
import com.example.eslamshahr.viewModel.*

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    private lateinit var getFoldersLayoutMgr: LinearLayoutManager

    private lateinit var getWatchListLayoutMgr: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProfileBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile, container, false
        )

        val application = requireNotNull(this.activity).application

        val dao = HebDatabase.getInstance(application).iDao

        val repository = MovieRepository(dao)

        val factory = ProfileViewModelFactory(repository, application)

        profileViewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)


        binding.lifecycleOwner = this

        binding.usenameText.text = Storage.getInstance().username

        binding.logout.setOnClickListener {
            val intent = Intent(requireContext(), StartActivity::class.java)
            startActivity(intent)
        }

        getFoldersLayoutMgr = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        getWatchListLayoutMgr = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.watchRecyclerView.layoutManager = getFoldersLayoutMgr

        profileViewModel.foldersLiveData.observe(viewLifecycleOwner ) {
            binding.watchRecyclerView.adapter = WatchlistFilesAdapter(it.toMutableList()) { favoriteList -> showFolderDetails(favoriteList) }
        }

        binding.favoriteRecyclerView.layoutManager = getWatchListLayoutMgr

        profileViewModel.watchListLiveData.observe(viewLifecycleOwner) {
            binding.favoriteRecyclerView.adapter = WatchListAdapter(it.toMutableList())
        }

        binding.addWatchlist.setOnClickListener {
            val intent = Intent(requireContext(), MakeWatchListActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun showFolderDetails(favoriteList: FavoriteList) {
        Storage.getInstance().favoriteListId = favoriteList.favoriteList_id
        val intent = Intent(requireContext(), FolderDetailActivity::class.java)
        startActivity(intent)
    }


}
