package com.example.daggerhiltexample.ui.dashboard

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.daggerhiltexample.R
import com.example.daggerhiltexample.adapter.ResultAdapter
import com.example.daggerhiltexample.base.BaseFragment
import com.example.daggerhiltexample.databinding.FragmentDashboardBinding
import com.example.daggerhiltexample.ui.NetworkStateViewModel
import com.example.daggerhiltexample.utils.NetworkListener
import com.example.daggerhiltexample.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    private val dashboardViewModel: DashboardViewModel by viewModels()
    private val networkStateViewModel: NetworkStateViewModel by viewModels()
    private val mAdapter by lazy { ResultAdapter() }
    private lateinit var networkListener: NetworkListener

    override fun getLayoutId(): Int = R.layout.fragment_dashboard


    override fun prepareView(savedInstanceState: Bundle?) {
        binding.apply {
            recyclerview.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            recyclerview.adapter = mAdapter
            recyclerview.showShimmer()
        }

        networkStateViewModel.readBackOnline.observe(this) {
            networkStateViewModel.backOnline = it
            readDatabase()
        }
        lifecycleScope.launch {
            networkListener = NetworkListener()
            context?.let {
                networkListener.checkNetworkAvailability(it).collect { status ->
                    networkStateViewModel.networkStatus = status
                    networkStateViewModel.showNetworkStatus()
                }
            }
        }
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            requestData()
        }
    }

    private fun requestData() {
        dashboardViewModel.getResult()
        dashboardViewModel.getList.observe(this) { response ->
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

}