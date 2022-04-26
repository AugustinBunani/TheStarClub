package com.augustin.thestarclub.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.augustin.thestarclub.model.UserData
import com.augustin.thestarclub.repository.UserDataRepository
import com.augustin.thestarclub.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDataViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
): ViewModel() {
    var isLoading = mutableStateOf(false)
    private var _getUserData: MutableLiveData<UserData> = MutableLiveData<UserData>()
    var getUserData: LiveData<UserData> = _getUserData

    fun getUserData(context: Context): Resource<UserData> {

        val result = userDataRepository.getUserData(context = context)

        MediatorLiveData<Resource<UserData>>().apply {
            addSource(result){
                isLoading.value = true
                _getUserData.value = it.data!!
            }
            observeForever {
                Log.d("TAG", "getUserData: ")
            }
        }
        return Resource.Error("Please wait")
    }
}