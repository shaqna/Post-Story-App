package com.ngedev.postcat.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ngedev.postcat.databinding.ActivityHomeBinding
import com.ngedev.postcat.domain.model.Story
import com.ngedev.postcat.external.AppSharedPreference
import com.ngedev.postcat.ui.detail.DetailActivity
import com.ngedev.postcat.ui.maps.MapsActivity
import com.ngedev.postcat.ui.post.PostStoryActivity
import com.ngedev.postcat.ui.settings.SettingsActivity
import com.ngedev.postcat.utils.di.HomeModule.homeModule
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class HomeActivity : AppCompatActivity() {

    private val prefs: AppSharedPreference by inject()

    private val pagingAdapter: StoryAdapter by lazy {
        StoryAdapter().apply {
            onClickListener = { story, optionsCompat ->
                Intent(this@HomeActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.STORY_PARCEL, story)
                    startActivity(it, optionsCompat.toBundle())
                }
            }
        }
    }

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadKoinModules(homeModule)

        prefs.fetchAccessToken()?.let {
            Log.d("MyTOKEN : ", it)
        }

        setButtonMenu()
        observerData()
        setSwipeRefreshLayout()

    }

    private fun setSwipeRefreshLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            observerData()
        }
    }

    private fun observerData() {
        viewModel.getAllStories().observe(this) { listItem ->
            lifecycleScope.launch {
                binding.rvStories.apply {
                    adapter = pagingAdapter
                    layoutManager = LinearLayoutManager(this@HomeActivity)
                }

                pagingAdapter.apply {
                    addLoadStateListener { loadState ->
                        loadState.decideOnState(
                            showLoading = { visible ->
                                showProgressBar(visible)
                            },
                            showEmpty = {

                            },
                            showError = { message ->
                                Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
                            }
                        )

                        binding.swipeRefresh.isRefreshing = false
                    }

                    submitData(listItem)
                    loadStateFlow.distinctUntilChanged()
                    loadStateFlow.collectLatest {
                        if(it.refresh is LoadState.NotLoading) {
                            showProgressBar(false)
                        }
                    }
                }

            }
        }
    }

    private inline fun CombinedLoadStates.decideOnState(
        showLoading: (Boolean) -> Unit,
        showEmpty: (Boolean) -> Unit,
        showError: (String) -> Unit
    ) {
        showLoading(refresh is LoadState.Loading)

        showEmpty(
            source.append is LoadState.NotLoading
                    && source.append.endOfPaginationReached
                    && pagingAdapter.itemCount == 0
        )

        val errorState = source.append as? LoadState.Error
            ?: source.prepend as? LoadState.Error
            ?: source.refresh as? LoadState.Error
            ?: append as? LoadState.Error
            ?: prepend as? LoadState.Error
            ?: refresh as? LoadState.Error

        errorState?.let {
            showError(it.error.toString())
        }
    }


    override fun onResume() {
        super.onResume()
        loadKoinModules(homeModule)
        observerData()
    }

    override fun onPause() {
        super.onPause()
        unloadKoinModules(homeModule)
    }

    private fun setButtonMenu() {
        binding.apply {
            btnSettings.setOnClickListener {
                startActivity(Intent(this@HomeActivity, SettingsActivity::class.java))
            }

            btnPost.setOnClickListener {
                startActivity(Intent(this@HomeActivity, PostStoryActivity::class.java))
            }

            mapsStories.setOnClickListener {
                startActivity(Intent(this@HomeActivity, MapsActivity::class.java))
            }
        }

    }

    private fun showProgressBar(state: Boolean) {
        binding.progressBar.isVisible = state
    }

}