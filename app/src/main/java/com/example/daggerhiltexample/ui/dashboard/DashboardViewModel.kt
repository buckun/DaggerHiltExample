package com.example.daggerhiltexample.ui.dashboard

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.daggerhiltexample.data.repository.Repository
import com.example.daggerhiltexample.model.ProductsItem
import com.example.daggerhiltexample.model.Response
import com.example.daggerhiltexample.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {


    var getList: MutableLiveData<NetworkResult<Response>> = MutableLiveData()
    var getDetails: MutableLiveData<NetworkResult<ProductsItem>> = MutableLiveData()


    fun getResult() = viewModelScope.launch {
        getResultSafeCall()
    }

    private suspend fun getResultSafeCall() {
        getList.value = NetworkResult.Loading()
        if (hasNetworkConnection()) {
            try {
                val response = repository.remote.fetchResult()
                getList.value = handleResponse(response)
            } catch (e: Exception) {
                getList.value = NetworkResult.Error(message = "Not Found!! " + e.message)
            }
        } else {
            getList.value = NetworkResult.Error(message = "No Internet Connection!!")
        }
    }

    private fun handleResponse(response: retrofit2.Response<Response>): NetworkResult<Response>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error(message = "Timeout!!")
            }

            response.isSuccessful -> {
                val result = response.body()
                return NetworkResult.Success(result!!)
            }

            else -> return NetworkResult.Error(message = response.message())
        }
    }


    private fun hasNetworkConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}