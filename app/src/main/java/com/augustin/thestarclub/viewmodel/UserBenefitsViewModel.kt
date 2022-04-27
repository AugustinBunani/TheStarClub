package com.augustin.thestarclub.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.augustin.thestarclub.model.Benefit
import com.augustin.thestarclub.model.BenefitX
import com.augustin.thestarclub.repository.UserBenefitsRepository
import com.augustin.thestarclub.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserBenefitsViewModel @Inject constructor(
    private val userBenefitsRepository: UserBenefitsRepository
): ViewModel() {
    var isLoading = mutableStateOf(false)
    private var _getBenefitsData: MutableLiveData<Benefit> = MutableLiveData<Benefit>()
//    private var _getBenefitsData: MutableLiveData<Benefit> = MutableLiveData<Benefit>()
    var getBenefitsData: LiveData<Benefit> = _getBenefitsData

    fun getBenefitsData(context: Context): Resource<Benefit> {

        val result = userBenefitsRepository.getBenefitsData(context = context)

        MediatorLiveData<Resource<Benefit>>().apply {
            addSource(result){
                isLoading.value = true
                _getBenefitsData.value = it.data!!
            }
            observeForever {
                Log.d("TAG", "getUserData: ")
            }
        }
        return Resource.Error("Please wait")
    }
}