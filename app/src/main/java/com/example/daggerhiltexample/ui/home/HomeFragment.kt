package com.example.daggerhiltexample.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.daggerhiltexample.MyApplication
import com.example.daggerhiltexample.R
import com.example.daggerhiltexample.adapter.ResultAdapter
import com.example.daggerhiltexample.base.BaseFragment
import com.example.daggerhiltexample.data.datasource.RemoteDataSource
import com.example.daggerhiltexample.data.network.ServiceApi
import com.example.daggerhiltexample.data.repository.Repository
import com.example.daggerhiltexample.databinding.FragmentHomeBinding
import com.example.daggerhiltexample.utils.Constants
import com.example.daggerhiltexample.utils.NetworkResult
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var homeViewModel: HomeViewModel
    private val mAdapter by lazy { ResultAdapter() }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun prepareView(savedInstanceState: Bundle?) {
        homeViewModel = createViewModel()!!
        binding.apply {
            recyclerview.layoutManager =
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            recyclerview.adapter = mAdapter
            recyclerview.showShimmer()
        }
        readDatabase()
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            requestData()
        }
    }

    private fun requestData() {
        homeViewModel?.getResult()
        homeViewModel?.getList?.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.recyclerview.hideShimmer()
                    response.data.let { mAdapter.setData(it!!) }
                }
                is NetworkResult.Error -> {
                    binding.recyclerview.hideShimmer()
                    Toast.makeText(context, response.message.toString(), Toast.LENGTH_SHORT).show()

                }
                is NetworkResult.Loading -> {
                    binding.recyclerview.showShimmer()
                }
            }
        }
    }


    private fun createViewModel(): HomeViewModel? {

        val appContainer = (activity?.application as MyApplication).appContainer
        //val remoteData = RemoteDataSource(appContainer.retrofit)
        //val repository = Repository(remoteData)
        //return activity?.let { HomeViewModel(repository, application = it.application) }
        return appContainer.fa.create()
    }

}