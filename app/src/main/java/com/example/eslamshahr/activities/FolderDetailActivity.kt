package com.example.eslamshahr.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eslamshahr.HebDatabase
import com.example.eslamshahr.R
import com.example.eslamshahr.adapter.SelectWatchlistAdapter
import com.example.eslamshahr.adapter.WatchlistDetailAdapter
import com.example.eslamshahr.adapter.WatchlistFilesAdapter
import com.example.eslamshahr.databinding.ActivityFolderDetailBinding
import com.example.eslamshahr.databinding.ActivityMakeWatchlistBinding
import com.example.eslamshahr.repositories.MovieRepository
import com.example.eslamshahr.viewModel.FolderDetailViewModelFactory
import com.example.eslamshahr.viewModel.FolderViewModel
import com.example.eslamshahr.viewModel.MakeWatchlistViewModel
import com.example.eslamshahr.viewModel.MakeWatchlistViewModelFactory

class FolderDetailActivity : AppCompatActivity() {

    private lateinit var folderViewModel: FolderViewModel

    private lateinit var getFoldersLayoutMgr: LinearLayoutManager

    lateinit var binding: ActivityFolderDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFolderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val application = requireNotNull(this).application

        val dao = HebDatabase.getInstance(application).iDao

        val repository = MovieRepository(dao)

        val factory = FolderDetailViewModelFactory(repository, application)

        folderViewModel = ViewModelProvider(this, factory).get(FolderViewModel::class.java)

        binding.lifecycleOwner = this

        getFoldersLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        binding.watchListDetail.layoutManager = getFoldersLayoutMgr

        folderViewModel.foldersDetailLiveData.observe(this ) {
            binding.watchListDetail.adapter = WatchlistDetailAdapter(it.toMutableList())
        }
    }
}